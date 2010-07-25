package org.gonevertical.core.client.ui.account;

import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AccountData implements IsSerializable {
	
	private ThingData thingData;
	
	public void setThingData(ThingData thingData) {
		this.thingData = thingData;
	}
	
	public ThingData getThingData() {
		return thingData;
	}
	
	public ThingStuffData getAlias() {
		int thingStuffTypeId = ThingStuffTypeData.THINGSTUFFTYPE_ALIAS;
		return getValue(thingStuffTypeId);
	}
	
	public ThingStuffData getFirstName() {
		int thingStuffTypeId = ThingStuffTypeData.THINGSTUFFTYPE_FIRSTNAME;
		return getValue(thingStuffTypeId);
	}
	
	public ThingStuffData getLastName() {
		int thingStuffTypeId = ThingStuffTypeData.THINGSTUFFTYPE_LASTNAME;
		return getValue(thingStuffTypeId);
	}
	
	public ThingStuffData getName() {
		int thingStuffTypeId = ThingStuffTypeData.THINGSTUFFTYPE_NAME;
		return getValue(thingStuffTypeId);
	}
	
	public ThingStuffData getValue(int thingStuffTypeId) {
		
		ThingStuffsData tsds = thingData.getThingStuffsData();
		ThingStuffData tsd = tsds.getThingStuffData(thingStuffTypeId);
		
		return tsd;
	}
	
	public ThingStuffData[] getEmails() {
		
		ThingStuffsData tsds = thingData.getThingStuffsData();
		ThingStuffData[] tsd = tsds.getThingStuffsData((long) ThingStuffTypeData.THINGSTUFFTYPE_EMAIL);
			
		return tsd;
	}

	public ThingStuffData[] getNames() {
	 
		ThingStuffData[] tsd = new ThingStuffData[4];
		tsd[0] = getAlias();
		tsd[1] = getFirstName();
		tsd[2] = getLastName();
		tsd[3] = getName();
		
	  return tsd;
  }

}
