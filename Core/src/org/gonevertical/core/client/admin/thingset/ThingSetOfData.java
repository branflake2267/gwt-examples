package org.gonevertical.core.client.admin.thingset;

import org.gonevertical.core.client.admin.thing.ThingData;
import org.gonevertical.core.client.admin.thingstuff.ThingStuffData;

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
