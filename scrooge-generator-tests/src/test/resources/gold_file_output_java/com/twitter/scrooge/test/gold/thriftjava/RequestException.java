/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.twitter.scrooge.test.gold.thriftjava;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apache.thrift.*;
import org.apache.thrift.async.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

import com.twitter.finagle.AbstractFailureFlags;
import com.twitter.finagle.JavaFailureFlags;
import com.twitter.scrooge.ThriftStructIface;
import com.twitter.scrooge.UtilValidator;
import com.twitter.scrooge.thrift_validation.BaseValidator;
import com.twitter.scrooge.thrift_validation.ThriftValidationViolation;
import com.twitter.scrooge.TFieldBlob;
import com.twitter.scrooge.internal.TProtocols;

// No additional import required for struct/union.

public class RequestException extends AbstractFailureFlags<RequestException> implements TBase<RequestException, RequestException._Fields>, java.io.Serializable, Cloneable, ThriftStructIface {
  private static final TStruct STRUCT_DESC = new TStruct("RequestException");

  private static final TField MESSAGE_FIELD_DESC = new TField("message", TType.STRING, (short)1);


  public String message;
  private Map<Short, TFieldBlob> passThroughFields;

  private long _flags;

  /** The set of fields this object contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    MESSAGE((short)1, "message");
  
    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();
  
    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }
  
    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // MESSAGE
          return MESSAGE;
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
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }
  
    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }
  
    private final short _thriftId;
    private final String _fieldName;
  
    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }
  
    public short getThriftFieldId() {
      return _thriftId;
    }
  
    public String getFieldName() {
      return _fieldName;
    }
  }


  // isset id assignments

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  
  /**
   * FieldValueMetaData.type returns TType.STRING for both string and binary field values.
   * This set can be used to determine if a FieldValueMetaData with type TType.STRING is actually
   * declared as binary in the idl file.
   */
  public static final Set<FieldValueMetaData> binaryFieldValueMetaDatas;
  
  private static FieldValueMetaData registerBinaryFieldValueMetaData(FieldValueMetaData f, Set<FieldValueMetaData> binaryFieldValues) {
    binaryFieldValues.add(f);
    return f;
  }
  
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    Set<FieldValueMetaData> tmpSet = new HashSet<FieldValueMetaData>();
    tmpMap.put(_Fields.MESSAGE, new FieldMetaData("message", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    binaryFieldValueMetaDatas = Collections.unmodifiableSet(tmpSet);
    FieldMetaData.addStructMetaDataMap(RequestException.class, metaDataMap);
  }

  /**
   * Returns a map of the annotations and their values for this struct declaration.
   * See fieldAnnotations or valueAnnotations for the annotations attached to struct fields
   * or enum values.
   */
  public static final Map<String, String> structAnnotations;
  static {
    structAnnotations = Collections.emptyMap();
  }

  /**
   * Returns a map of the annotations for each of this struct's fields, keyed by the field.
   * See structAnnotations for the annotations attached to this struct's declaration.
   */
  public static final Map<_Fields, Map<String, String>> fieldAnnotations;
  static {
    Map<_Fields, Map<String, String>> tmpMap = new EnumMap<_Fields, Map<String, String>>(_Fields.class);
      {
        Map<String, String> tmpFieldMap = new HashMap<String, String>();
        tmpFieldMap.put("validation.notEmpty", "");
        tmpMap.put(_Fields.MESSAGE, Collections.unmodifiableMap(tmpFieldMap));
      }
    fieldAnnotations = Collections.unmodifiableMap(tmpMap);
  }

  /**
   * Returns the set of fields that have a configured default value.
   * The default values for these fields can be obtained by
   * instantiating this class with the default constructor.
   */
  public static final Set<_Fields> hasDefaultValue;
  static {
    Set<_Fields> tmp = EnumSet.noneOf(_Fields.class);
    hasDefaultValue = Collections.unmodifiableSet(tmp);
  }


  public RequestException() {
    this._flags = JavaFailureFlags.EMPTY;
  }

  public RequestException(
    String message)
  {
    this();
    this.message = message;
    this._flags = JavaFailureFlags.EMPTY;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RequestException(RequestException other) {
    if (other.isSetMessage()) {
      this.message = other.message;
    }
    this.passThroughFields = other.passThroughFields;
    this._flags = other._flags;
  }

  public long flags() {
    return _flags;
  }

  public RequestException copyWithFlags(long flags) {
    RequestException copied = deepCopy();
    copied._flags = flags;
    return copied;
  }

  public static List<String> validateNewInstance(RequestException item) {
    final List<String> buf = new ArrayList<String>();

    return buf;
  }

  public static Set<ThriftValidationViolation> validateInstanceValue(RequestException item) {
    final Set<ThriftValidationViolation> violations = new HashSet<ThriftValidationViolation>();
    final BaseValidator validator = new UtilValidator();

    violations.addAll(validator.validateField("message", item.message, fieldAnnotations.get(_Fields.MESSAGE)));

    return violations;
  }

  public RequestException deepCopy() {
    return new RequestException(this);
  }

  @java.lang.Override
  public void clear() {
    this.message = null;
    this.passThroughFields = null;
    this._flags = JavaFailureFlags.EMPTY;
  }

  public String getMessage() {
    return this.message;
  }

  public RequestException setMessage(String message) {
    this.message = message;
    
    return this;
  }

  public void unsetMessage() {
    this.message = null;
  }

  /** Returns true if field message is set (has been assigned a value) and false otherwise */
  public boolean isSetMessage() {
    return this.message != null;
  }

  public void setMessageIsSet(boolean value) {
    if (!value) {
      this.message = null;
    }
  }

  @SuppressWarnings("unchecked")
  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MESSAGE:
      if (value == null) {
        unsetMessage();
      } else {
        setMessage((String)value);
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MESSAGE:
      return getMessage();
    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MESSAGE:
      return isSetMessage();
    }
    throw new IllegalStateException();
  }

  @java.lang.Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RequestException)
      return this.equals((RequestException)that);
    return false;
  }

  public boolean equals(RequestException that) {
    return this == that || (equalsWithoutPassthrough(that) && passthroughFieldsAreEqual(that));
  }

  private boolean equalsWithoutPassthrough(RequestException that) {
    if (that == null)
      return false;
    boolean this_present_message = true && this.isSetMessage();
    boolean that_present_message = true && that.isSetMessage();
    if (this_present_message || that_present_message) {
      if (!(this_present_message && that_present_message))
        return false;
      if (!this.message.equals(that.message))
        return false;
    }
    if (this._flags != that._flags)
      return false;
    return true;
  }

  private boolean passthroughFieldsAreEqual(RequestException that) {
    if (that == null)
      return false;
    if (this.passThroughFields == null && that.passThroughFields != null)
      return false;
    if (this.passThroughFields == that.passThroughFields
        || this.passThroughFields.equals(that.passThroughFields))
      return true;
    return false;
  }

  @java.lang.Override
  public int hashCode() {
    int hashCode = 1;
    if (isSetMessage()) {
      hashCode = 31 * hashCode + message.hashCode();
    }
    {
      hashCode = 31 * hashCode + Long.hashCode(this._flags);
    }
    return hashCode;
  }

  public int compareTo(RequestException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    RequestException typedOther = (RequestException)other;

    lastComparison = Boolean.valueOf(isSetMessage()).compareTo(typedOther.isSetMessage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessage()) {
      lastComparison = TBaseHelper.compareTo(this.message, typedOther.message);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }


  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) {
        break;
      }
      switch (field.id) {
        case 1: // MESSAGE
          TProtocols.validateFieldType(TType.STRING, field.type, "message");
          this.message = iprot.readString();
          break;
        default:
          if (this.passThroughFields == null) {
            this.passThroughFields = new HashMap<Short, TFieldBlob>();
          }
          this.passThroughFields.put(field.id, TFieldBlob.extractBlob(field, iprot));
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();
    
    oprot.writeStructBegin(STRUCT_DESC);
    if (this.message != null) {
      oprot.writeFieldBegin(MESSAGE_FIELD_DESC);
      oprot.writeString(this.message);
      oprot.writeFieldEnd();
    }
    if (this.passThroughFields != null) {
      for (TFieldBlob field : this.passThroughFields.values()) {
        field.write(oprot);
      }
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @java.lang.Override
  public String toString() {
    StringBuilder sb = new StringBuilder("RequestException(");
    boolean first = true;
    sb.append("message:");
    if (this.message == null) {
      sb.append("null");
    } else {
      sb.append(this.message);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }
}

