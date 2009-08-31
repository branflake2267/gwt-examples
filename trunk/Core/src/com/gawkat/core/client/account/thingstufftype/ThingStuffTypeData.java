package com.gawkat.core.client.account.thingstufftype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypeData implements IsSerializable {

  public static final int VT_STRING = 1;
  public static final int VT_BOOLEAN = 2;
  public static final int VT_DOUBLE = 3;
  public static final int VT_INT = 4;
  public static final int VT_STRING_LARGE = 5; // text area
  public static final int VT_STRING_CASE = 6; // text box case sensitive
  public static final int VT_STRING_LARGE_CASE = 7; // text area case sensitive
  public static final int VT_HTML = 8;
  public static final int VT_URL = 9;
  public static final int VT_EMAIL = 10;
  public static final int VT_PHONE = 11;
  
  private long stuffTypeId;
  
  private int valueTypeId = VT_STRING;
  
  private String name;
  
  public ThingStuffTypeData() {
  }
  
  public void setData(long id, String name, int valueTypeId) {
    this.stuffTypeId = id;
    this.name = name;
    this.valueTypeId = valueTypeId;
  }
  
  public long getStuffTypeId() {
    return this.stuffTypeId;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setValueTypeId(int valueTypeId) {
    this.valueTypeId = valueTypeId;
  }
  
  public int getValueTypeId() {
    return valueTypeId;
  }
  
}
