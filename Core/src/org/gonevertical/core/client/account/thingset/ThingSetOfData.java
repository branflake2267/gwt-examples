package org.gonevertical.core.client.account.thingset;

import org.gonevertical.core.client.account.thing.ThingData;
import org.gonevertical.core.client.account.thingstuff.ThingStuffData;

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
