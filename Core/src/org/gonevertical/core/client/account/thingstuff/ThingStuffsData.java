package org.gonevertical.core.client.account.thingstuff;

import org.gonevertical.core.client.account.thingstufftype.ThingStuffTypesData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffsData implements IsSerializable {
  
  private long total = 0;

  // thing stuff type choices
  private ThingStuffTypesData thingStuffTypesData = null;
  
  private ThingStuffData[] thingStuffData = null;
  
  public void setTotal(long total) {
  	this.total = total;
  }
  
  public long getTotal() {
  	return total;
  }
  
  public void setThingStuffTypesData(ThingStuffTypesData thingSTuffTypesData) {
  	this.thingStuffTypesData = thingSTuffTypesData;
  }
  
  public ThingStuffTypesData getThingStuffTypesData() {
  	return thingStuffTypesData;
  }
  
  public void setThingStuffData(ThingStuffData[] thingStuffData) {
  	this.thingStuffData = thingStuffData;
  }
  
  public ThingStuffData[] getThingStuffData() {
  	if (thingStuffData == null) {
  		thingStuffData = new ThingStuffData[0];
  	}
  	return thingStuffData;
  }

}
