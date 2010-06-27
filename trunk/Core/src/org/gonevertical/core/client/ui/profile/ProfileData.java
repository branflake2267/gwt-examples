package org.gonevertical.core.client.ui.profile;

import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProfileData implements IsSerializable {
	
	private ThingData thingData;
	
	public void setThingData(ThingData thingData) {
		this.thingData = thingData;
	}
	
	public ThingData getThingData() {
		return thingData;
	}
	
	public String getAlias() {
		int thingStuffTypeId = ThingStuffTypeData.THINGSTUFFTYPE_ALIAS;
		return getValue(thingStuffTypeId);
	}
	
	public String getFirstName() {
		int thingStuffTypeId = ThingStuffTypeData.THINGSTUFFTYPE_FIRSTNAME;
		return getValue(thingStuffTypeId);
	}
	
	public String getLastName() {
		int thingStuffTypeId = ThingStuffTypeData.THINGSTUFFTYPE_LASTNAME;
		return getValue(thingStuffTypeId);
	}
	
	public String getValue(int thingStuffTypeId) {
		
		ThingStuffsData tsds = thingData.getThingStuffsData();
		ThingStuffData tsd = tsds.getThingStuffData(thingStuffTypeId);
		
		String s = "";
		if (tsd != null) {
			s = tsd.getValue();
		}

		return s;
	}

}
