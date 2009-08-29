package com.gawkat.core.client.account.thing;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingsData implements IsSerializable {

  public int total = 0;
  
  public ThingData[] thingData = null;
  
  public ThingsData() {
  }
  
}
