/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hbase.replication.regionserver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.Server;
import org.apache.hadoop.hbase.Stoppable;
import org.apache.hadoop.hbase.Waiter;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.WALProtos.BulkLoadDescriptor;
import org.apache.hadoop.hbase.regionserver.MultiVersionConcurrencyControl;
import org.apache.hadoop.hbase.regionserver.wal.WALActionsListener;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.replication.ReplicationEndpoint;
import org.apache.hadoop.hbase.replication.ReplicationFactory;
import org.apache.hadoop.hbase.replication.ReplicationPeerConfig;
import org.apache.hadoop.hbase.replication.ReplicationPeers;
import org.apache.hadoop.hbase.replication.ReplicationQueueInfo;
import org.apache.hadoop.hbase.replication.ReplicationQueues;
import org.apache.hadoop.hbase.replication.ReplicationQueuesClient;
import org.apache.hadoop.hbase.replication.ReplicationSourceDummy;
import org.apache.hadoop.hbase.replication.regionserver.ReplicationSourceManager.NodeFailoverWorker;
import org.apache.hadoop.hbase.replication.regionserver.helper.DummyServer;
import org.apache.hadoop.hbase.testclassification.MediumTests;
import org.apache.hadoop.hbase.util.ByteStringer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;
import org.apache.hadoop.hbase.wal.WAL;
import org.apache.hadoop.hbase.wal.WALFactory;
import org.apache.hadoop.hbase.wal.WALKey;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(MediumTests.class)
public class TestReplicationSourceManagerManager extends TestReplicationSourceManagerBase {
  private static final Log LOG =
    LogFactory.getLog(TestReplicationSourceManagerManager.class);
  private static List<String> files = new ArrayList<>();
  private static CountDownLatch latch;

  @Test
  public void testLogRoll() throws Exception {
    long baseline = 1000;
    long time = baseline;
    MultiVersionConcurrencyControl mvcc = new MultiVersionConcurrencyControl();
    KeyValue kv = new KeyValue(r1, f1, r1);
    WALEdit edit = new WALEdit();
    edit.add(kv);

    List<WALActionsListener> listeners = new ArrayList<WALActionsListener>();
    listeners.add(replication);
    final WALFactory wals = new WALFactory(utility.getConfiguration(), listeners,
        URLEncoder.encode("regionserver:60020", "UTF8"));
    final WAL wal = wals.getWAL(hri.getEncodedNameAsBytes(), hri.getTable().getNamespace());
    manager.init();
    HTableDescriptor htd = new HTableDescriptor();
    htd.addFamily(new HColumnDescriptor(f1));
    // Testing normal log rolling every 20
    for(long i = 1; i < 101; i++) {
      if(i > 1 && i % 20 == 0) {
        wal.rollWriter();
      }
      LOG.info(i);
      final long txid = wal.append(htd,
          hri,
          new WALKey(hri.getEncodedNameAsBytes(), test, System.currentTimeMillis(), mvcc),
          edit,
          true);
      wal.sync(txid);
    }

    // Simulate a rapid insert that's followed
    // by a report that's still not totally complete (missing last one)
    LOG.info(baseline + " and " + time);
    baseline += 101;
    time = baseline;
    LOG.info(baseline + " and " + time);

    for (int i = 0; i < 3; i++) {
      wal.append(htd, hri,
          new WALKey(hri.getEncodedNameAsBytes(), test, System.currentTimeMillis(), mvcc),
          edit,
          true);
    }
    wal.sync();

    int logNumber = 0;
    for (Map.Entry<String, SortedSet<String>> entry : manager.getWALs().get(slaveId).entrySet()) {
      logNumber += entry.getValue().size();
    }
    assertEquals(6, logNumber);

    wal.rollWriter();

    manager.logPositionAndCleanOldLogs(manager.getSources().get(0).getCurrentPath(),
        "1", 0, false, false);

    wal.append(htd, hri,
        new WALKey(hri.getEncodedNameAsBytes(), test, System.currentTimeMillis(), mvcc),
        edit,
        true);
    wal.sync();

    assertEquals(1, manager.getWALs().size());


    // TODO Need a case with only 2 WALs and we only want to delete the first one
  }

  @Test
  public void testClaimQueues() throws Exception {
    conf.setBoolean(HConstants.ZOOKEEPER_USEMULTI, true);
    final Server server = new DummyServer(conf, "hostname0.example.org", zkw);
    ReplicationQueues rq =
        ReplicationFactory.getReplicationQueues(server.getZooKeeper(), server.getConfiguration(),
          server);
    rq.init(server.getServerName().toString());
    // populate some znodes in the peer znode
    files.add("log1");
    files.add("log2");
    for (String file : files) {
      rq.addLog("1", file);
    }
    // create 3 DummyServers
    Server s1 = new DummyServer(conf, "dummyserver1.example.org", zkw);
    Server s2 = new DummyServer(conf, "dummyserver2.example.org", zkw);
    Server s3 = new DummyServer(conf, "dummyserver3.example.org", zkw);

    // create 3 DummyNodeFailoverWorkers
    DummyNodeFailoverWorker w1 = new DummyNodeFailoverWorker(
        server.getServerName().getServerName(), s1);
    DummyNodeFailoverWorker w2 = new DummyNodeFailoverWorker(
        server.getServerName().getServerName(), s2);
    DummyNodeFailoverWorker w3 = new DummyNodeFailoverWorker(
        server.getServerName().getServerName(), s3);

    latch = new CountDownLatch(3);
    // start the threads
    w1.start();
    w2.start();
    w3.start();
    // make sure only one is successful
    int populatedMap = 0;
    // wait for result now... till all the workers are done.
    latch.await();
    populatedMap += w1.isLogZnodesMapPopulated() + w2.isLogZnodesMapPopulated()
        + w3.isLogZnodesMapPopulated();
    assertEquals(1, populatedMap);
    server.abort("", null);
  }

  @Test
  public void testCleanupFailoverQueues() throws Exception {
    final Server server = new DummyServer(conf, "hostname1.example.org", zkw);
    ReplicationQueues rq =
        ReplicationFactory.getReplicationQueues(server.getZooKeeper(), server.getConfiguration(),
          server);
    rq.init(server.getServerName().toString());
    // populate some znodes in the peer znode
    SortedSet<String> files = new TreeSet<String>();
    String group = "testgroup";
    String file1 = group + ".log1";
    String file2 = group + ".log2";
    files.add(file1);
    files.add(file2);
    for (String file : files) {
      rq.addLog("1", file);
    }
    Server s1 = new DummyServer(conf, "dummyserver1.example.org", zkw);
    ReplicationQueues rq1 =
        ReplicationFactory.getReplicationQueues(s1.getZooKeeper(), s1.getConfiguration(), s1);
    rq1.init(s1.getServerName().toString());
    ReplicationPeers rp1 =
        ReplicationFactory.getReplicationPeers(s1.getZooKeeper(), s1.getConfiguration(), s1);
    rp1.init();
    NodeFailoverWorker w1 =
        manager.new NodeFailoverWorker(server.getServerName().getServerName(), rq1, rp1, new UUID(
            new Long(1), new Long(2)));
    w1.start();
    w1.join(10000);
    assertEquals(1, manager.getWalsByIdRecoveredQueues().size());
    String id = "1-" + server.getServerName().getServerName();
    assertEquals(files, manager.getWalsByIdRecoveredQueues().get(id).get(group));
    manager.cleanOldLogs(file2, id, true);
    // log1 should be deleted
    assertEquals(Sets.newHashSet(file2), manager.getWalsByIdRecoveredQueues().get(id).get(group));
  }

  @Test
  public void testNodeFailoverDeadServerParsing() throws Exception {
    LOG.debug("testNodeFailoverDeadServerParsing");
    conf.setBoolean(HConstants.ZOOKEEPER_USEMULTI, true);
    final Server server = new DummyServer(conf, "ec2-54-234-230-108.compute-1.amazonaws.com", zkw);
    ReplicationQueues repQueues =
        ReplicationFactory.getReplicationQueues(server.getZooKeeper(), conf, server);
    repQueues.init(server.getServerName().toString());
    // populate some znodes in the peer znode
    files.add("log1");
    files.add("log2");
    for (String file : files) {
      repQueues.addLog("1", file);
    }

    // create 3 DummyServers
    Server s1 = new DummyServer(conf, "ip-10-8-101-114.ec2.internal", zkw);
    Server s2 = new DummyServer(conf, "ec2-107-20-52-47.compute-1.amazonaws.com", zkw);
    Server s3 = new DummyServer(conf, "ec2-23-20-187-167.compute-1.amazonaws.com", zkw);

    // simulate three servers fail sequentially
    ReplicationQueues rq1 =
        ReplicationFactory.getReplicationQueues(s1.getZooKeeper(), s1.getConfiguration(), s1);
    rq1.init(s1.getServerName().toString());
    String serverName = server.getServerName().getServerName();
    List<String> unclaimed = rq1.getUnClaimedQueueIds(serverName);
    rq1.claimQueue(serverName, unclaimed.get(0)).getSecond();
    rq1.removeReplicatorIfQueueIsEmpty(unclaimed.get(0));

    ReplicationQueues rq2 =
        ReplicationFactory.getReplicationQueues(s2.getZooKeeper(), s2.getConfiguration(), s2);
    rq2.init(s2.getServerName().toString());
    serverName = s1.getServerName().getServerName();
    unclaimed = rq2.getUnClaimedQueueIds(serverName);
    rq2.claimQueue(serverName, unclaimed.get(0)).getSecond();
    rq2.removeReplicatorIfQueueIsEmpty(unclaimed.get(0));
    ReplicationQueues rq3 =
        ReplicationFactory.getReplicationQueues(s3.getZooKeeper(), s3.getConfiguration(), s3);
    rq3.init(s3.getServerName().toString());
    serverName = s2.getServerName().getServerName();
    unclaimed = rq3.getUnClaimedQueueIds(serverName);
    String queue3 = rq3.claimQueue(serverName, unclaimed.get(0)).getFirst();
    rq3.removeReplicatorIfQueueIsEmpty(unclaimed.get(0));
    ReplicationQueueInfo replicationQueueInfo = new ReplicationQueueInfo(queue3);
    List<String> result = replicationQueueInfo.getDeadRegionServers();

    // verify
    assertTrue(result.contains(server.getServerName().getServerName()));
    assertTrue(result.contains(s1.getServerName().getServerName()));
    assertTrue(result.contains(s2.getServerName().getServerName()));

    server.abort("", null);
  }

  @Test
  public void testFailoverDeadServerCversionChange() throws Exception {
    LOG.debug("testFailoverDeadServerCversionChange");

    conf.setBoolean(HConstants.ZOOKEEPER_USEMULTI, true);
    final Server s0 = new DummyServer(conf, "cversion-change0.example.org", zkw);
    ReplicationQueues repQueues =
        ReplicationFactory.getReplicationQueues(s0.getZooKeeper(), conf, s0);
    repQueues.init(s0.getServerName().toString());
    // populate some znodes in the peer znode
    files.add("log1");
    files.add("log2");
    for (String file : files) {
      repQueues.addLog("1", file);
    }
    // simulate queue transfer
    Server s1 = new DummyServer(conf, "cversion-change1.example.org", zkw);
    ReplicationQueues rq1 =
        ReplicationFactory.getReplicationQueues(s1.getZooKeeper(), s1.getConfiguration(), s1);
    rq1.init(s1.getServerName().toString());

    ReplicationQueuesClient client =
        ReplicationFactory.getReplicationQueuesClient(s1.getZooKeeper(), s1.getConfiguration(), s1);

    int v0 = client.getQueuesZNodeCversion();
    List<String> queues = rq1.getUnClaimedQueueIds(s0.getServerName().getServerName());
    for(String queue : queues) {
      rq1.claimQueue(s0.getServerName().getServerName(), queue);
    }
    rq1.removeReplicatorIfQueueIsEmpty(s0.getServerName().getServerName());
    int v1 = client.getQueuesZNodeCversion();
    // cversion should increased by 1 since a child node is deleted
    assertEquals(v0 + 1, v1);

    s0.abort("", null);
  }

  @Test
  @edu.umd.cs.findbugs.annotations.SuppressWarnings(value="RU_INVOKE_RUN",
    justification="Intended")
  public void testCleanupUnknownPeerZNode() throws Exception {
    final Server server = new DummyServer(conf, "hostname2.example.org", zkw);
    ReplicationQueues rq =
        ReplicationFactory.getReplicationQueues(server.getZooKeeper(), server.getConfiguration(),
          server);
    rq.init(server.getServerName().toString());
    // populate some znodes in the peer znode
    // add log to an unknown peer
    String group = "testgroup";
    rq.addLog("2", group + ".log1");
    rq.addLog("2", group + ".log2");

    NodeFailoverWorker w1 = manager.new NodeFailoverWorker(server.getServerName().getServerName());
    w1.run();

    // The log of the unknown peer should be removed from zk
    for (String peer : manager.getAllQueues()) {
      assertTrue(peer.startsWith("1"));
    }
  }

  @Test
  public void testBulkLoadWALEditsWithoutBulkLoadReplicationEnabled() throws Exception {
    // 1. Create wal key
    WALKey logKey = new WALKey();
    // 2. Get the bulk load wal edit event
    WALEdit logEdit = getBulkLoadWALEdit();

    // 3. Get the scopes for the key
    Replication.scopeWALEdits(htd, logKey, logEdit, conf, manager);

    // 4. Assert that no bulk load entry scopes are added if bulk load hfile replication is disabled
    assertNull("No bulk load entries scope should be added if bulk load replication is diabled.",
      logKey.getScopes());
  }

  @Test
  public void testBulkLoadWALEdits() throws Exception {
    // 1. Create wal key
    WALKey logKey = new WALKey();
    // 2. Get the bulk load wal edit event
    WALEdit logEdit = getBulkLoadWALEdit();
    // 3. Enable bulk load hfile replication
    Configuration bulkLoadConf = HBaseConfiguration.create(conf);
    bulkLoadConf.setBoolean(HConstants.REPLICATION_BULKLOAD_ENABLE_KEY, true);

    // 4. Get the scopes for the key
    Replication.scopeWALEdits(htd, logKey, logEdit, bulkLoadConf, manager);

    NavigableMap<byte[], Integer> scopes = logKey.getScopes();
    // Assert family with replication scope global is present in the key scopes
    assertTrue("This family scope is set to global, should be part of replication key scopes.",
      scopes.containsKey(f1));
    // Assert family with replication scope local is not present in the key scopes
    assertFalse("This family scope is set to local, should not be part of replication key scopes",
      scopes.containsKey(f2));
  }

  /**
   * Test whether calling removePeer() on a ReplicationSourceManager that failed on initializing the
   * corresponding ReplicationSourceInterface correctly cleans up the corresponding
   * replication queue and ReplicationPeer.
   * See HBASE-16096.
   * @throws Exception exception
   */
  @Test
  public void testPeerRemovalCleanup() throws Exception {
    String replicationSourceImplName = conf.get("replication.replicationsource.implementation");
    final String peerId = "FakePeer";
    final ReplicationPeerConfig peerConfig =
        new ReplicationPeerConfig().setClusterKey("localhost:1:/hbase");
    try {
      final ReplicationQueues rq =
          ReplicationFactory.getReplicationQueues(server.getZooKeeper(), server.getConfiguration(),
              server);
      rq.init(server.getServerName().toString());
      // Purposely fail ReplicationSourceManager.addSource() by causing ReplicationSourceInterface
      // initialization to throw an exception.
      conf.set("replication.replicationsource.implementation",
          FailInitializeDummyReplicationSource.class.getName());
      final ReplicationPeers rp = manager.getReplicationPeers();
      // Set up the znode and ReplicationPeer for the fake peer
      // Don't wait for replication source to initialize, we know it won't.
      addPeerAndWait(peerId, peerConfig, false);

      // Sanity check
      assertNull(manager.getSource(peerId));


      // Create a replication queue for the fake peer
      rq.addLog(peerId, "FakeFile");
      // Unregister peer, this should remove the peer and clear all queues associated with it
      // Need to wait for the ReplicationTracker to pick up the changes and notify listeners.
      removePeerAndWait(peerId);
      assertFalse(rq.getAllQueues().contains(peerId));
    } finally {
      conf.set("replication.replicationsource.implementation", replicationSourceImplName);
      removePeerAndWait(peerId);
    }
  }

  @Test
  public void testRemovePeerMetricsCleanup() throws Exception {
    final String peerId = "DummyPeer";
    final ReplicationPeerConfig peerConfig =
        new ReplicationPeerConfig().setClusterKey("localhost:1:/hbase");
    try {
      addPeerAndWait(peerId, peerConfig, true);

      ReplicationSourceInterface source = manager.getSource(peerId);
      // Sanity check
      assertNotNull(source);
      // Retrieve the global replication metrics source
      Field f = MetricsSource.class.getDeclaredField("globalSourceSource");
      f.setAccessible(true);
      MetricsReplicationSourceSource globalSource =
          (MetricsReplicationSourceSource)f.get(source.getSourceMetrics());
      int globalLogQueueSizeInitial = globalSource.getSizeOfLogQueue();

      // Enqueue log and check if metrics updated
      source.enqueueLog(new Path("abc"));
      assertEquals(1, source.getSourceMetrics().getSizeOfLogQueue());
      assertEquals(1 + globalLogQueueSizeInitial, globalSource.getSizeOfLogQueue());

      // Removing the peer should reset the global metrics
      removePeerAndWait(peerId);
      assertEquals(globalLogQueueSizeInitial, globalSource.getSizeOfLogQueue());

      // Adding the same peer back again should reset the single source metrics
      addPeerAndWait(peerId, peerConfig, true);
      source = manager.getSource(peerId);
      assertNotNull(source);
      assertEquals(0, source.getSourceMetrics().getSizeOfLogQueue());
      assertEquals(globalLogQueueSizeInitial, globalSource.getSizeOfLogQueue());
    } finally {
      removePeerAndWait(peerId);
    }
  }

  /**
   * Add a peer and wait for it to initialize
   * @param peerId the replication peer Id
   * @param peerConfig the replication peer config
   * @param waitForSource Whether to wait for replication source to initialize
   * @throws Exception exception
   */
  private void addPeerAndWait(final String peerId, final ReplicationPeerConfig peerConfig,
      final boolean waitForSource) throws Exception {
    final ReplicationPeers rp = manager.getReplicationPeers();
    rp.addPeer(peerId, peerConfig);
    Waiter.waitFor(conf, 20000, new Waiter.Predicate<Exception>() {
      @Override public boolean evaluate() throws Exception {
        if (waitForSource) {
          return (manager.getSource(peerId) != null);
        } else {
          return (rp.getPeer(peerId) != null);
        }
      }
    });
  }

  /**
   * Remove a peer and wait for it to get cleaned up
   * @param peerId the replication peer Id
   * @throws Exception exception
   */
  private void removePeerAndWait(final String peerId) throws Exception {
    final ReplicationPeers rp = manager.getReplicationPeers();
    if (rp.getAllPeerIds().contains(peerId)) {
      rp.removePeer(peerId);
    }
    Waiter.waitFor(conf, 20000, new Waiter.Predicate<Exception>() {
      @Override public boolean evaluate() throws Exception {
        List<String> peers = rp.getAllPeerIds();
        return (!manager.getAllQueues().contains(peerId)) && (rp.getPeer(peerId) == null)
            && (!peers.contains(peerId));
      }
    });
  }

  @Test
  public void testSameWALPrefix() throws IOException {
    Set<Path> latestWalsBefore = manager.getLastestPath();
    Path walName1 = new Path("localhost,8080,12345-45678-Peer.34567");
    Path walName2 = new Path("localhost,8080,12345.56789");
    manager.preLogRoll(walName1);
    manager.preLogRoll(walName2);
    Set<Path> latestWals = manager.getLastestPath();
    latestWals.removeAll(latestWalsBefore);
    assertEquals(2, latestWals.size());
    assertTrue(latestWals.contains(walName1));
    assertTrue(latestWals.contains(walName2));
  }

  private WALEdit getBulkLoadWALEdit() {
    // 1. Create store files for the families
    Map<byte[], List<Path>> storeFiles = new HashMap<>(1);
    Map<String, Long> storeFilesSize = new HashMap<>(1);
    List<Path> p = new ArrayList<>(1);
    Path hfilePath1 = new Path(Bytes.toString(f1));
    p.add(hfilePath1);
    try {
      storeFilesSize.put(hfilePath1.getName(), fs.getFileStatus(hfilePath1).getLen());
    } catch (IOException e) {
      LOG.debug("Failed to calculate the size of hfile " + hfilePath1);
      storeFilesSize.put(hfilePath1.getName(), 0L);
    }
    storeFiles.put(f1, p);

    p = new ArrayList<>(1);
    Path hfilePath2 = new Path(Bytes.toString(f2));
    p.add(hfilePath2);
    try {
      storeFilesSize.put(hfilePath2.getName(), fs.getFileStatus(hfilePath2).getLen());
    } catch (IOException e) {
      LOG.debug("Failed to calculate the size of hfile " + hfilePath2);
      storeFilesSize.put(hfilePath2.getName(), 0L);
    }
    storeFiles.put(f2, p);

    // 2. Create bulk load descriptor
    BulkLoadDescriptor desc =
        ProtobufUtil.toBulkLoadDescriptor(hri.getTable(),
          ByteStringer.wrap(hri.getEncodedNameAsBytes()), storeFiles, storeFilesSize, 1);

    // 3. create bulk load wal edit event
    WALEdit logEdit = WALEdit.createBulkLoadEvent(hri, desc);
    return logEdit;
  }

  static class DummyNodeFailoverWorker extends Thread {
    private SortedMap<String, SortedSet<String>> logZnodesMap;
    Server server;
    private String deadRsZnode;
    ReplicationQueues rq;

    public DummyNodeFailoverWorker(String znode, Server s) throws Exception {
      this.deadRsZnode = znode;
      this.server = s;
      this.rq =
          ReplicationFactory.getReplicationQueues(server.getZooKeeper(), server.getConfiguration(),
            server);
      this.rq.init(this.server.getServerName().toString());
    }

    @Override
    public void run() {
      try {
        logZnodesMap = new TreeMap<>();
        List<String> queues = rq.getUnClaimedQueueIds(deadRsZnode);
        for(String queue:queues){
          Pair<String, SortedSet<String>> pair = rq.claimQueue(deadRsZnode, queue);
          if (pair != null) {
            logZnodesMap.put(pair.getFirst(), pair.getSecond());
          }
        }
        server.abort("Done with testing", null);
      } catch (Exception e) {
        LOG.error("Got exception while running NodeFailoverWorker", e);
      } finally {
        latch.countDown();
      }
    }

    /**
     * @return 1 when the map is not empty.
     */
    private int isLogZnodesMapPopulated() {
      Collection<SortedSet<String>> sets = logZnodesMap.values();
      if (sets.size() > 1) {
        throw new RuntimeException("unexpected size of logZnodesMap: " + sets.size());
      }
      if (sets.size() == 1) {
        SortedSet<String> s = sets.iterator().next();
        for (String file : files) {
          // at least one file was missing
          if (!s.contains(file)) {
            return 0;
          }
        }
        return 1; // we found all the files
      }
      return 0;
    }
  }

  static class FailInitializeDummyReplicationSource extends ReplicationSourceDummy {

    @Override
    public void init(Configuration conf, FileSystem fs, ReplicationSourceManager manager,
        ReplicationQueues rq, ReplicationPeers rp, Stoppable stopper, String peerClusterId,
        UUID clusterId, ReplicationEndpoint replicationEndpoint, MetricsSource metrics)
        throws IOException {
      throw new IOException("Failing deliberately");
    }
  }
}
