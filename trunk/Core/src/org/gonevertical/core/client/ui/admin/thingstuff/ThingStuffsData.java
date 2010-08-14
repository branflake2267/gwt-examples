package org.gonevertical.core.client.ui.admin.thingstuff;

import java.util.ArrayList;

import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypesData;

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
  
  public void setThingStuffTypesData(ThingStuffTypesData thingStuffTypesData) {
  	this.thingStuffTypesData = thingStuffTypesData;
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
  
  /**
   * get one stuff, the first stuff that comes up
   * 	like first name
   * 
   * @param thingStufftypeId
   * @return
   */
  public ThingStuffData getThingStuffData(long thingStufftypeId) {
  	if (thingStuffData == null) {
  		return null;
  	}
  	
  	ThingStuffData tsd = null;
  	for (int i=0; i < thingStuffData.length; i++) {
  		long tsid = thingStuffData[i].getStuffTypeId();
  		if (thingStufftypeId == tsid) {
  			tsd = thingStuffData[i];
  			break;
  		}
  	}
  	
  	return tsd;
  }
  
  public ThingStuffData[] getThingStuffsData(long thingStufftypeId) {
  	if (thingStuffData == null) {
  		return null;
  	}
  	
  	ArrayList<ThingStuffData> atsd = new ArrayList<ThingStuffData>();
  	for (int i=0; i < thingStuffData.length; i++) {
  		long tsid = thingStuffData[i].getStuffTypeId();
  		if (thingStufftypeId == tsid) {
  			atsd.add(thingStuffData[i]);
  		}
  	}
  	
  	if (atsd.size() == 0) {
  		return null;
  	}
  	
  	ThingStuffData[] tsd = new ThingStuffData[atsd.size()];
  	atsd.toArray(tsd);
  	
  	return tsd;
  }

}
