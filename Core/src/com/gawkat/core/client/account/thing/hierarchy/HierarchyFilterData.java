package com.gawkat.core.client.account.thing.hierarchy;

import com.gawkat.core.client.account.thingset.ThingSetOfFilterData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class HierarchyFilterData implements IsSerializable {

  public int start = 0;
  
  public int limit = 0;
  
  // get downline of this - the starting point
  public int thingIdStarting = 0;
  
}
