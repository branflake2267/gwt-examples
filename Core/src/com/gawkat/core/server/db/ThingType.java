package com.gawkat.core.server.db;

import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.admin.thingtype.ThingTypeFilterData;
import com.gawkat.core.server.jdo.Thing_TypeJdo;

public class ThingType {
  
  public ThingType() {
  }
  
  public ThingTypeData[] getThingTypes(ThingTypeFilterData filter) {
    
    ThingTypeData[] t = Thing_TypeJdo.query(filter);
    
    return t;
  }

}
