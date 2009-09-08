package com.gawkat.core.client.account.thingset;

import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * thing data stuff
 * 
 * @author branflake2267
 *
 */
public class ThingSetOfData implements IsSerializable {
  
  // the thing
  public ThingData thingData = null;
  
  // and its stuff
  public ThingStuffData[] thingStuffData = null;

}
