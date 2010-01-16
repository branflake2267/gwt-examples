package com.gawkat.core.client.account.thingtype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingTypesData implements IsSerializable {

  public int total = 0;
  
  public ThingTypeData[] thingTypeData = null;
  
  /**
   * get the thing type by id
   * 
   * @param thingTypeId
   * @return
   */
  public ThingTypeData getThingType(long thingTypeId) {
    ThingTypeData r = null;
    for (int i=0; i < thingTypeData.length; i++) {
      long id = thingTypeData[i].getId();
      if (id == thingTypeId) {
        r = thingTypeData[i];
        break;
      }
    }
    return r;
  }
  
}
