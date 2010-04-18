package org.gonevertical.core.client.account.thing.ownership;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingLinkFilterData implements IsSerializable {

  public int start = 0;
  
  public int limit = 0;
  
  // get downline of this - the starting point
  public int thingIdStarting = 0;
  
}
