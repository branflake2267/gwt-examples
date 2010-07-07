package org.gonevertical.core.client.ui.admin.thingstufftype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffTypesData implements IsSerializable {
  
  private long total = 0;
  
  private ThingStuffTypeData[] thingStuffTypeData = null;
  
  public void setTotal(long total) {
  	this.total = total;
  }
  
  public long getTotal() {
  	return total;
  }
  
  public void setThingStuffTypeData(ThingStuffTypeData[] thingStuffTypeData) {
  	this.thingStuffTypeData = thingStuffTypeData;
  }
  
  public ThingStuffTypeData[] getThingStuffTypeData() {
  	return thingStuffTypeData;
  }
  
  public ThingStuffTypeData getStuffTypeData(long id) {
    if (thingStuffTypeData == null) {
    	return null;
    }
  	ThingStuffTypeData r = null;
    for (int i=0; i < thingStuffTypeData.length; i++) {
      if (id == thingStuffTypeData[i].getStuffTypeId()) {
        r = thingStuffTypeData[i];
        break;
      }
    }
    return r;
  }
}
