/**
 * Autogenerated by Scrooge
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.twitter.scrooge.test.gold.thriftandroid;

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
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

// No additional import required for struct/union.

public class RequestException extends Exception implements TBase<RequestException, RequestException._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("RequestException");

  private static final TField MESSAGE_FIELD_DESC = new TField("message", TType.STRING, (short)1);


  private String message;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
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
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MESSAGE, new FieldMetaData("message", TFieldRequirementType.DEFAULT,
      new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(RequestException.class, metaDataMap);
  }


  public RequestException() {
  }

  public RequestException(
      String message
  ) {
    this();
    if(message != null) {
      this.message = message;
    }
  }


  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RequestException(RequestException other) {
    if (other.isSet(_Fields.MESSAGE)) {
      this.message = other.message;
    }
  }

  public static List<String> validateNewInstance(RequestException item) {
    final List<String> buf = new ArrayList<String>();
    return buf;
  }

  public RequestException deepCopy() {
    return new RequestException(this);
  }

  @java.lang.Override
  public void clear() {
    this.message = null;
  }



  @SuppressWarnings("unchecked")
  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MESSAGE:
      if (value == null) {
        this.message = null;
      } else {
        this.message = (String) value;
      }
      break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MESSAGE:
      return this.message;
    }
    throw new IllegalStateException();
  }

  @SuppressWarnings("unchecked")
  public <Any> Any get(_Fields field) {
    switch(field) {
      case MESSAGE:
          Any rval_message = (Any)((String) getFieldValue(field));
          return rval_message;
      default:
        throw new IllegalStateException("Invalid field type");
    }
  }

  /** Returns true if field corresponding to field is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    switch (field) {
    case MESSAGE:
        return message != null;
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
    if (that == null)
      return false;
    if (this == that)
      return true;
    boolean this_present_message = true && this.isSet(_Fields.MESSAGE);
    boolean that_present_message = true && that.isSet(_Fields.MESSAGE);
    if (this_present_message || that_present_message) {
      if (!(this_present_message && that_present_message))
        return false;
      if (!this.message.equals(that.message))
        return false;
    }

    return true;
  }

  @java.lang.Override
  @SuppressWarnings("unchecked")
  public int hashCode() {
    int hashCode = 1;
    if (true && (isSet(_Fields.MESSAGE))) {
        hashCode = 31 * hashCode + message.hashCode();
    }
    return hashCode;
  }

  public int compareTo(RequestException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    RequestException typedOther = (RequestException)other;

    lastComparison = Boolean.valueOf(isSet(_Fields.MESSAGE)).compareTo(typedOther.isSet(_Fields.MESSAGE));
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSet(_Fields.MESSAGE)) {
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
          if (field.type == TType.STRING) {
            this.message = iprot.readString();
          } else {
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
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

  public static final _Fields MESSAGE = _Fields.MESSAGE;

  public static class Builder {
    private String message;
  @SuppressWarnings("unchecked")
  public Builder set (_Fields field, Object value) {
    switch(field) {
      case MESSAGE: {
        if (value != null) {
          this.message = (String) value;
        }
        break;
      }
      default: {
        break;
      }
    }
    return this;
  }
  public RequestException build() {
    // check for required fields
    return new RequestException(message);
    }
  }
}

