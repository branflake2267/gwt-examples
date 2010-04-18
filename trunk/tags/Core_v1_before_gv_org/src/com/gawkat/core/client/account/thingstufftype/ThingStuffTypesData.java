package com.gawkat.core.client.account.thingstufftype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypesData implements IsSerializable {
  
  public long total = 0;
  
  public ThingStuffTypeData[] thingStuffTypeData = null;
  
  public ThingStuffTypeData getStuffTypeData(int id) {
    ThingStuffTypeData r = null;
    for (int i=0; i < thingStuffTypeData.length; i++) {
      if (id == thingStuffTypeData[i].getStuffTypeId()) {
        r = thingStuffTypeData[i];
        break;
      }
    }
    return r;
  }
}
