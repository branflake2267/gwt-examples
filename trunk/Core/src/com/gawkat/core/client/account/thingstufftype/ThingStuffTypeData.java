package com.gawkat.core.client.account.thingstufftype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypeData implements IsSerializable {

  public static final int VT_STRING = 0;
  public static final int VT_BOOLEAN = 1;
  public static final int VT_DOUBLE = 2;
  
  private long id;
  
  private int valueTypeId = VT_STRING;
  
  private String name;
  
  public ThingStuffTypeData() {
  }
  
  public void setData(long id, String name, int valueTypeId) {
    this.id = id;
    this.name = name;
    this.valueTypeId = valueTypeId;
  }
  
  public long getId() {
    return this.id;
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
