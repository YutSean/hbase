/**
 * Autogenerated by Thrift Compiler (0.14.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hbase.thrift2.generated;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * Represents a single cell and the amount to increment it by
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.14.1)", date = "2021-06-29")
public class TColumnIncrement implements org.apache.thrift.TBase<TColumnIncrement, TColumnIncrement._Fields>, java.io.Serializable, Cloneable, Comparable<TColumnIncrement> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TColumnIncrement");

  private static final org.apache.thrift.protocol.TField FAMILY_FIELD_DESC = new org.apache.thrift.protocol.TField("family", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField QUALIFIER_FIELD_DESC = new org.apache.thrift.protocol.TField("qualifier", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("amount", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TColumnIncrementStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TColumnIncrementTupleSchemeFactory();

  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer family; // required
  public @org.apache.thrift.annotation.Nullable java.nio.ByteBuffer qualifier; // required
  public long amount; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FAMILY((short)1, "family"),
    QUALIFIER((short)2, "qualifier"),
    AMOUNT((short)3, "amount");

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
        case 1: // FAMILY
          return FAMILY;
        case 2: // QUALIFIER
          return QUALIFIER;
        case 3: // AMOUNT
          return AMOUNT;
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
  private static final int __AMOUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.AMOUNT};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FAMILY, new org.apache.thrift.meta_data.FieldMetaData("family", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.QUALIFIER, new org.apache.thrift.meta_data.FieldMetaData("qualifier", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    tmpMap.put(_Fields.AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("amount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TColumnIncrement.class, metaDataMap);
  }

  public TColumnIncrement() {
    this.amount = 1L;

  }

  public TColumnIncrement(
    java.nio.ByteBuffer family,
    java.nio.ByteBuffer qualifier)
  {
    this();
    this.family = org.apache.thrift.TBaseHelper.copyBinary(family);
    this.qualifier = org.apache.thrift.TBaseHelper.copyBinary(qualifier);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TColumnIncrement(TColumnIncrement other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetFamily()) {
      this.family = org.apache.thrift.TBaseHelper.copyBinary(other.family);
    }
    if (other.isSetQualifier()) {
      this.qualifier = org.apache.thrift.TBaseHelper.copyBinary(other.qualifier);
    }
    this.amount = other.amount;
  }

  public TColumnIncrement deepCopy() {
    return new TColumnIncrement(this);
  }

  @Override
  public void clear() {
    this.family = null;
    this.qualifier = null;
    this.amount = 1L;

  }

  public byte[] getFamily() {
    setFamily(org.apache.thrift.TBaseHelper.rightSize(family));
    return family == null ? null : family.array();
  }

  public java.nio.ByteBuffer bufferForFamily() {
    return org.apache.thrift.TBaseHelper.copyBinary(family);
  }

  public TColumnIncrement setFamily(byte[] family) {
    this.family = family == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(family.clone());
    return this;
  }

  public TColumnIncrement setFamily(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer family) {
    this.family = org.apache.thrift.TBaseHelper.copyBinary(family);
    return this;
  }

  public void unsetFamily() {
    this.family = null;
  }

  /** Returns true if field family is set (has been assigned a value) and false otherwise */
  public boolean isSetFamily() {
    return this.family != null;
  }

  public void setFamilyIsSet(boolean value) {
    if (!value) {
      this.family = null;
    }
  }

  public byte[] getQualifier() {
    setQualifier(org.apache.thrift.TBaseHelper.rightSize(qualifier));
    return qualifier == null ? null : qualifier.array();
  }

  public java.nio.ByteBuffer bufferForQualifier() {
    return org.apache.thrift.TBaseHelper.copyBinary(qualifier);
  }

  public TColumnIncrement setQualifier(byte[] qualifier) {
    this.qualifier = qualifier == null ? (java.nio.ByteBuffer)null   : java.nio.ByteBuffer.wrap(qualifier.clone());
    return this;
  }

  public TColumnIncrement setQualifier(@org.apache.thrift.annotation.Nullable java.nio.ByteBuffer qualifier) {
    this.qualifier = org.apache.thrift.TBaseHelper.copyBinary(qualifier);
    return this;
  }

  public void unsetQualifier() {
    this.qualifier = null;
  }

  /** Returns true if field qualifier is set (has been assigned a value) and false otherwise */
  public boolean isSetQualifier() {
    return this.qualifier != null;
  }

  public void setQualifierIsSet(boolean value) {
    if (!value) {
      this.qualifier = null;
    }
  }

  public long getAmount() {
    return this.amount;
  }

  public TColumnIncrement setAmount(long amount) {
    this.amount = amount;
    setAmountIsSet(true);
    return this;
  }

  public void unsetAmount() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __AMOUNT_ISSET_ID);
  }

  /** Returns true if field amount is set (has been assigned a value) and false otherwise */
  public boolean isSetAmount() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __AMOUNT_ISSET_ID);
  }

  public void setAmountIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __AMOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case FAMILY:
      if (value == null) {
        unsetFamily();
      } else {
        if (value instanceof byte[]) {
          setFamily((byte[])value);
        } else {
          setFamily((java.nio.ByteBuffer)value);
        }
      }
      break;

    case QUALIFIER:
      if (value == null) {
        unsetQualifier();
      } else {
        if (value instanceof byte[]) {
          setQualifier((byte[])value);
        } else {
          setQualifier((java.nio.ByteBuffer)value);
        }
      }
      break;

    case AMOUNT:
      if (value == null) {
        unsetAmount();
      } else {
        setAmount((java.lang.Long)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case FAMILY:
      return getFamily();

    case QUALIFIER:
      return getQualifier();

    case AMOUNT:
      return getAmount();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case FAMILY:
      return isSetFamily();
    case QUALIFIER:
      return isSetQualifier();
    case AMOUNT:
      return isSetAmount();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof TColumnIncrement)
      return this.equals((TColumnIncrement)that);
    return false;
  }

  public boolean equals(TColumnIncrement that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_family = true && this.isSetFamily();
    boolean that_present_family = true && that.isSetFamily();
    if (this_present_family || that_present_family) {
      if (!(this_present_family && that_present_family))
        return false;
      if (!this.family.equals(that.family))
        return false;
    }

    boolean this_present_qualifier = true && this.isSetQualifier();
    boolean that_present_qualifier = true && that.isSetQualifier();
    if (this_present_qualifier || that_present_qualifier) {
      if (!(this_present_qualifier && that_present_qualifier))
        return false;
      if (!this.qualifier.equals(that.qualifier))
        return false;
    }

    boolean this_present_amount = true && this.isSetAmount();
    boolean that_present_amount = true && that.isSetAmount();
    if (this_present_amount || that_present_amount) {
      if (!(this_present_amount && that_present_amount))
        return false;
      if (this.amount != that.amount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetFamily()) ? 131071 : 524287);
    if (isSetFamily())
      hashCode = hashCode * 8191 + family.hashCode();

    hashCode = hashCode * 8191 + ((isSetQualifier()) ? 131071 : 524287);
    if (isSetQualifier())
      hashCode = hashCode * 8191 + qualifier.hashCode();

    hashCode = hashCode * 8191 + ((isSetAmount()) ? 131071 : 524287);
    if (isSetAmount())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(amount);

    return hashCode;
  }

  @Override
  public int compareTo(TColumnIncrement other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetFamily(), other.isSetFamily());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFamily()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.family, other.family);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetQualifier(), other.isSetQualifier());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQualifier()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.qualifier, other.qualifier);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetAmount(), other.isSetAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.amount, other.amount);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TColumnIncrement(");
    boolean first = true;

    sb.append("family:");
    if (this.family == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.family, sb);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("qualifier:");
    if (this.qualifier == null) {
      sb.append("null");
    } else {
      org.apache.thrift.TBaseHelper.toString(this.qualifier, sb);
    }
    first = false;
    if (isSetAmount()) {
      if (!first) sb.append(", ");
      sb.append("amount:");
      sb.append(this.amount);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (family == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'family' was not present! Struct: " + toString());
    }
    if (qualifier == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'qualifier' was not present! Struct: " + toString());
    }
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

  private static class TColumnIncrementStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TColumnIncrementStandardScheme getScheme() {
      return new TColumnIncrementStandardScheme();
    }
  }

  private static class TColumnIncrementStandardScheme extends org.apache.thrift.scheme.StandardScheme<TColumnIncrement> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TColumnIncrement struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FAMILY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.family = iprot.readBinary();
              struct.setFamilyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // QUALIFIER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.qualifier = iprot.readBinary();
              struct.setQualifierIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.amount = iprot.readI64();
              struct.setAmountIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TColumnIncrement struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.family != null) {
        oprot.writeFieldBegin(FAMILY_FIELD_DESC);
        oprot.writeBinary(struct.family);
        oprot.writeFieldEnd();
      }
      if (struct.qualifier != null) {
        oprot.writeFieldBegin(QUALIFIER_FIELD_DESC);
        oprot.writeBinary(struct.qualifier);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAmount()) {
        oprot.writeFieldBegin(AMOUNT_FIELD_DESC);
        oprot.writeI64(struct.amount);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TColumnIncrementTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TColumnIncrementTupleScheme getScheme() {
      return new TColumnIncrementTupleScheme();
    }
  }

  private static class TColumnIncrementTupleScheme extends org.apache.thrift.scheme.TupleScheme<TColumnIncrement> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TColumnIncrement struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeBinary(struct.family);
      oprot.writeBinary(struct.qualifier);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetAmount()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetAmount()) {
        oprot.writeI64(struct.amount);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TColumnIncrement struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.family = iprot.readBinary();
      struct.setFamilyIsSet(true);
      struct.qualifier = iprot.readBinary();
      struct.setQualifierIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.amount = iprot.readI64();
        struct.setAmountIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

