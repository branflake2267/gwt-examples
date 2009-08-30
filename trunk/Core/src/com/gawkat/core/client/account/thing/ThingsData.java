package com.gawkat.core.client.account.thing;

import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingsData implements IsSerializable {

  public int total = 0;
  
  public ThingData[] thingData = null;
  
  // list of thing types that the thing data will need to show in the list
  public ThingTypesData thingTypesData = null;
  
  public ThingsData() {
  }
  
}
