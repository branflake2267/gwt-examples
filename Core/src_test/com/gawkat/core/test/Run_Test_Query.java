package com.gawkat.core.test;

import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.admin.thingtype.ThingTypeFilterData;
import com.gawkat.core.server.db.ThingType;

public class Run_Test_Query {

  public static void main(String[] args) {
    
    ThingType t = new ThingType();
    ThingTypeData[] r = t.getThingTypes(new ThingTypeFilterData());
    
  }
  
}
