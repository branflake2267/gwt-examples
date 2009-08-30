package com.gawkat.core.server.jdo.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingStuffJdo {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private long thingStuffId;
  
  @Persistent
  private long thingStuffTypeId;
  
  // values that can be stored
  @Persistent
  private String value = null;
  
  @Persistent
  private boolean valueBol = false;
  
  @Persistent
  private double valueDouble = 0.0;
  
  /**
   * constructor
   */
  public ThingStuffJdo() {
  }
  
  public long getStuffId() {
    return thingStuffId;
  }
  
  public long getStuffTypeId() {
    return thingStuffTypeId;
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
