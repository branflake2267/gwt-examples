package com.gawkat.core.client.account.thing.ownership;

import com.gawkat.core.client.account.thingset.ThingSetOfData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingLinksData implements IsSerializable {

  public int total = 0;
  
  public ThingSetOfData[] thingSetData = null;
  
}
