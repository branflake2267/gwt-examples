package com.gawkat.core.client.account.thingstuff;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffData implements IsSerializable {
  
  private long thingStuffId = 0;
  
  private long thingStuffTypeId = 0;
    
  // values that can be stored
  private String value = null;
  
  private boolean valueBol = false;
  
  private double valueDouble = 0.0;

  /**
   * constructor
   */
  public ThingStuffData() {
  }
  
  public long getId() {
    return thingStuffId;
  }
  
  public long getThingStuffTypeId() {
    return thingStuffTypeId;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public void setValue(boolean value) {
    this.valueBol = value;
  }
  
  public void setValue(double value) {
    this.valueDouble = value;
  }
  
  public String getValue() {
    return value;
  }
  
  public boolean getValueBol() {
    return valueBol;
  }
  
  public double getValueDouble() {
    return valueDouble;
  }
  
}
