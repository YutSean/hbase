/**
 * Autogenerated by Thrift Compiler (0.14.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hbase.thrift.generated;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * For increments that are not incrementColumnValue
 * equivalents.
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.14.1)", date = "2021-06-29")
public class TIncrement implements org.apache.thrift.TBase<TIncrement, TIncrement._Fields>, java.io.Serializable, Cloneable, Comparable<TIncrement> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TIncrement");

  private static final org.apache.thrift.protocol.TField TABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("table", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ROW_FIELD_DESC = new org.apache.thrift.protocol.TField("row", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField COLUMN_FIELD_DESC = new org.apache.thrift.protocol.TField("column", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField AMMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("ammount", org.apache.thrift.protocol.TType.I64, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TIncrementStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TIncrementTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer table; // required
  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer row; // required
  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer column; // required
  public long ammount; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TABLE((short)1, "table"),
    ROW((short)2, "row"),
    COLUMN((short)3, "column"),
    AMMOUNT((short)4, "ammount");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TABLE
          return TABLE;
        case 2: // ROW
          return ROW;
        case 3: // COLUMN
          return COLUMN;
        case 4: // AMMOUNT
          return AMMOUNT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __AMMOUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TABLE, new org.apache.thrift.meta_data.FieldMetaData("table", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Text")));
    tmpMap.put(_Fields.ROW, new org.apache.thrift.meta_data.FieldMetaData("row", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Text")));
    tmpMap.put(_Fields.COLUMN, new org.apache.thrift.meta_data.FieldMetaData("column", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "Text")));
    tmpMap.put(_Fields.AMMOUNT, new org.apache.thrift.meta_data.FieldMetaData("ammount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TIncrement.class, metaDataMap);
  }

  public TIncrement() {
  }

  public TIncrement(
    java.nio.ByteBuffer table,
    java.nio.ByteBuffer row,
    java.nio.ByteBuffer column,
    long ammount)
  {
    this();
    this.table = org.apache.thrift.TBaseHelper.copyBinary(table);
    this.row = org.apache.thrift.TBaseHelper.copyBinary(row);
    this.column = org.apache.thrift.TBaseHelper.copyBinary(column);
    this.ammount = ammount;
    setAmmountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TIncrement(TIncrement other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTable()) {
      this.table = org.apache.thrift.TBaseHelper.copyBinary(other.table);
    }
    if (other.isSetRow()) {
      this.row = org.apache.thrift.TBaseHelper.copyBinary(other.row);
    }
    if (other.isSetColumn()) {
      this.column = org.apache.thrift.TBaseHelper.copyBinary(other.column);
    }
    this.ammount = other.ammount;
  }

  public TIncrement deepCopy() {
    return new TIncrement(this);
  }

  @Override
  public void clear() {
    this.table = null;
    this.row = null;
    this.column = null;
    setAmmountIsSet(false);
    this.ammount = 0;
  }

  public byte[] getTable() {
    setTable(org.apache.thrift.TBaseHelper.rightSize(table));
    return table == null ? null : table.array();
  }

  public java.nio.ByteBuffer bufferForTable() {
    return org.apache.thrift.TBaseHelper.copyBinary(table);
  }

  public TIncrement setTable(byte[] table) {
    this.table = table == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(table.clone());
    return this;
  }

  public TIncrement setTable(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer table) {
    this.table = org.apache.thrift.TBaseHelper.copyBinary(table);
    return this;
  }

  public void unsetTable() {
    this.table = null;
  }

  /** Returns true if field table is set (has been assigned a value) and false otherwise */
  public boolean isSetTable() {
    return this.table != null;
  }

  public void setTableIsSet(boolean value) {
    if (!value) {
      this.table = null;
    }
  }

  public byte[] getRow() {
    setRow(org.apache.thrift.TBaseHelper.rightSize(row));
    return row == null ? null : row.array();
  }

  public java.nio.ByteBuffer bufferForRow() {
    return org.apache.thrift.TBaseHelper.copyBinary(row);
  }

  public TIncrement setRow(byte[] row) {
    this.row = row == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(row.clone());
    return this;
  }

  public TIncrement setRow(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer row) {
    this.row = org.apache.thrift.TBaseHelper.copyBinary(row);
    return this;
  }

  public void unsetRow() {
    this.row = null;
  }

  /** Returns true if field row is set (has been assigned a value) and false otherwise */
  public boolean isSetRow() {
    return this.row != null;
  }

  public void setRowIsSet(boolean value) {
    if (!value) {
      this.row = null;
    }
  }

  public byte[] getColumn() {
    setColumn(org.apache.thrift.TBaseHelper.rightSize(column));
    return column == null ? null : column.array();
  }

  public java.nio.ByteBuffer bufferForColumn() {
    return org.apache.thrift.TBaseHelper.copyBinary(column);
  }

  public TIncrement setColumn(byte[] column) {
    this.column = column == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(column.clone());
    return this;
  }

  public TIncrement setColumn(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer column) {
    this.column = org.apache.thrift.TBaseHelper.copyBinary(column);
    return this;
  }

  public void unsetColumn() {
    this.column = null;
  }

  /** Returns true if field column is set (has been assigned a value) and false otherwise */
  public boolean isSetColumn() {
    return this.column != null;
  }

  public void setColumnIsSet(boolean value) {
    if (!value) {
      this.column = null;
    }
  }

  public long getAmmount() {
    return this.ammount;
  }

  public TIncrement setAmmount(long ammount) {
    this.ammount = ammount;
    setAmmountIsSet(true);
    return this;
  }

  public void unsetAmmount() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __AMMOUNT_ISSET_ID);
  }

  /** Returns true if field ammount is set (has been assigned a value) and false otherwise */
  public boolean isSetAmmount() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __AMMOUNT_ISSET_ID);
  }

  public void setAmmountIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __AMMOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case TABLE:
      if (value == null) {
        unsetTable();
      } else {
        if (value instanceof byte[]) {
          setTable((byte[])value);
        } else {
          setTable((java.nio.ByteBuffer)value);
        }
      }
      break;

    case ROW:
      if (value == null) {
        unsetRow();
      } else {
        if (value instanceof byte[]) {
          setRow((byte[])value);
        } else {
          setRow((java.nio.ByteBuffer)value);
        }
      }
      break;

    case COLUMN:
      if (value == null) {
        unsetColumn();
      } else {
        if (value instanceof byte[]) {
          setColumn((byte[])value);
        } else {
          setColumn((java.nio.ByteBuffer)value);
        }
      }
      break;

    case AMMOUNT:
      if (value == null) {
        unsetAmmount();
      } else {
        setAmmount((java.lang.Long)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case TABLE:
      return getTable();

    case ROW:
      return getRow();

    case COLUMN:
      return getColumn();

    case AMMOUNT:
      return getAmmount();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case TABLE:
      return isSetTable();
    case ROW:
      return isSetRow();
    case COLUMN:
      return isSetColumn();
    case AMMOUNT:
      return isSetAmmount();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof TIncrement)
      return this.equals((TIncrement)that);
    return false;
  }

  public boolean equals(TIncrement that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_table = true && this.isSetTable();
    boolean that_present_table = true && that.isSetTable();
    if (this_present_table || that_present_table) {
      if (!(this_present_table && that_present_table))
        return false;
      if (!this.table.equals(that.table))
        return false;
    }

    boolean this_present_row = true && this.isSetRow();
    boolean that_present_row = true && that.isSetRow();
    if (this_present_row || that_present_row) {
      if (!(this_present_row && that_present_row))
        return false;
      if (!this.row.equals(that.row))
        return false;
    }

    boolean this_present_column = true && this.isSetColumn();
    boolean that_present_column = true && that.isSetColumn();
    if (this_present_column || that_present_column) {
      if (!(this_present_column && that_present_column))
        return false;
      if (!this.column.equals(that.column))
        return false;
    }

    boolean this_present_ammount = true;
    boolean that_present_ammount = true;
    if (this_present_ammount || that_present_ammount) {
      if (!(this_present_ammount && that_present_ammount))
        return false;
      if (this.ammount != that.ammount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetTable()) ? 131071 : 524287);
    if (isSetTable())
      hashCode = hashCode * 8191 + table.hashCode();

    hashCode = hashCode * 8191 + ((isSetRow()) ? 131071 : 524287);
    if (isSetRow())
      hashCode = hashCode * 8191 + row.hashCode();

    hashCode = hashCode * 8191 + ((isSetColumn()) ? 131071 : 524287);
    if (isSetColumn())
      hashCode = hashCode * 8191 + column.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(ammount);

    return hashCode;
  }

  @Override
  public int compareTo(TIncrement other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetTable(), other.isSetTable());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTable()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.table, other.table);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetRow(), other.isSetRow());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRow()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.row, other.row);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetColumn(), other.isSetColumn());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetColumn()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.column, other.column);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetAmmount(), other.isSetAmmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAmmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ammount, other.ammount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TIncrement(");
    boolean first = true;

    sb.append("table:");
    if (this.table == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.table, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("row:");
    if (this.row == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.row, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("column:");
    if (this.column == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.column, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ammount:");
    sb.append(this.ammount);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TIncrementStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TIncrementStandardScheme getScheme() {
      return new TIncrementStandardScheme();
    }
  }

  private static class TIncrementStandardScheme extends org.apache.thrift.scheme.StandardScheme<TIncrement> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TIncrement struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TABLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.table = iprot.readBinary();
              struct.setTableIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ROW
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.row = iprot.readBinary();
              struct.setRowIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // COLUMN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.column = iprot.readBinary();
              struct.setColumnIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // AMMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.ammount = iprot.readI64();
              struct.setAmmountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TIncrement struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.table != null) {
        oprot.writeFieldBegin(TABLE_FIELD_DESC);
        oprot.writeBinary(struct.table);
        oprot.writeFieldEnd();
      }
      if (struct.row != null) {
        oprot.writeFieldBegin(ROW_FIELD_DESC);
        oprot.writeBinary(struct.row);
        oprot.writeFieldEnd();
      }
      if (struct.column != null) {
        oprot.writeFieldBegin(COLUMN_FIELD_DESC);
        oprot.writeBinary(struct.column);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(AMMOUNT_FIELD_DESC);
      oprot.writeI64(struct.ammount);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TIncrementTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TIncrementTupleScheme getScheme() {
      return new TIncrementTupleScheme();
    }
  }

  private static class TIncrementTupleScheme extends org.apache.thrift.scheme.TupleScheme<TIncrement> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TIncrement struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetTable()) {
        optionals.set(0);
      }
      if (struct.isSetRow()) {
        optionals.set(1);
      }
      if (struct.isSetColumn()) {
        optionals.set(2);
      }
      if (struct.isSetAmmount()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTable()) {
        oprot.writeBinary(struct.table);
      }
      if (struct.isSetRow()) {
        oprot.writeBinary(struct.row);
      }
      if (struct.isSetColumn()) {
        oprot.writeBinary(struct.column);
      }
      if (struct.isSetAmmount()) {
        oprot.writeI64(struct.ammount);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TIncrement struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.table = iprot.readBinary();
        struct.setTableIsSet(true);
      }
      if (incoming.get(1)) {
        struct.row = iprot.readBinary();
        struct.setRowIsSet(true);
      }
      if (incoming.get(2)) {
        struct.column = iprot.readBinary();
        struct.setColumnIsSet(true);
      }
      if (incoming.get(3)) {
        struct.ammount = iprot.readI64();
        struct.setAmmountIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

