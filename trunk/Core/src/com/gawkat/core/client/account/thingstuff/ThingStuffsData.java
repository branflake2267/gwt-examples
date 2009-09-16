package com.gawkat.core.client.account.thingstuff;

import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffsData implements IsSerializable {
  
  public long total = 0;

  // type choices
  public ThingStuffTypesData thingStuffTypesData = null;
  
  public ThingStuffData[] thingStuffData = null;

}
