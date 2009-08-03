package com.gawkat.core.server.db;

import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.admin.thingtype.ThingTypeFilterData;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

public class ThingType {
  
  /**
   * constructor
   */
  public ThingType() {
  }
  
  /**
   * get thing types - application, group, user, ...
   * 
   * @param filter
   * @return
   */
  public ThingTypeData[] getThingTypes(ThingTypeFilterData filter) {
    ThingTypeJdo[] thingTypeJdo = ThingTypeJdo.query(filter);
    ThingTypeData[] r = ThingTypeJdo.convert(thingTypeJdo);
    return r;
  }

}
