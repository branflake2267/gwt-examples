package com.gawkat.core.client.account.thingstuff;

import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffsData implements IsSerializable {
  
	// TODO - used for later with offset,limit...
  public long total = 0;

  // thing stuff type choices
  public ThingStuffTypesData thingStuffTypesData = null;
  
  public ThingStuffData[] thingStuffData = null;
  
  public ThingStuffData[] getThingStuffData() {
  	if (thingStuffData == null) {
  		thingStuffData = new ThingStuffData[0];
  	}
  	return thingStuffData;
  }

}
