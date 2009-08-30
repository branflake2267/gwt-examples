package com.gawkat.core.client.account.thing;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingData implements IsSerializable {
  
  private long thingTypeId = 0;
  
  private long thingId = 0;
  
  private String key = null;
  
  public ThingData() {
  }
  
  public ThingData(long thingTypeId, long thingId) {
    this.thingTypeId = thingTypeId;
    this.thingId = thingId;
  }
  
  public void setData(long thingTypeId, long thingId) {
    this.thingTypeId = thingTypeId;
    this.thingId = thingId;
  }
  
  public void setData(long thingTypeId, long thingId, String key) {
    this.thingTypeId = thingTypeId;
    this.thingId = thingId;
    this.key = key;
  }
  
  public long getThingTypeId() {
    return thingTypeId;
  }
  
  public long getThingId() {
    return thingId;
  }
  
  public String getKey() {
    return key;
  }
  


}
