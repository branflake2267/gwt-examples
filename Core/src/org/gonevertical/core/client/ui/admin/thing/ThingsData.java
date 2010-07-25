package org.gonevertical.core.client.ui.admin.thing;

import org.gonevertical.core.client.ui.admin.thingtype.ThingTypesData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingsData implements IsSerializable {

  private long total = 0;
  
  private ThingData[] thingData = null;
  
  // list of thing types that the thing data will need to show in the list
  private ThingTypesData thingTypesData = null;
  
  public ThingsData() {
  }
  
  public void setThingTypesData(ThingTypesData thingTypesData) {
  	this.thingTypesData = thingTypesData;
  }
  
  public ThingTypesData getThingTypesData() {
  	return thingTypesData;
  }
  
  public void setThingData(ThingData[] thingData) {
  	this.thingData = thingData;
  }
  
  public ThingData[] getThingData() {
  	return thingData;
  }
  
  public void setTotal(long total) {
  	this.total = total;
  }
  
  public long getTotal() {
  	return total;
  }
  
}
