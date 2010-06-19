package org.gonevertical.core.client.admin.thing;

import org.gonevertical.core.client.admin.thingtype.ThingTypesData;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingsData implements IsSerializable {

  public long total = 0;
  
  public ThingData[] thingData = null;
  
  // list of thing types that the thing data will need to show in the list
  public ThingTypesData thingTypesData = null;
  
  public ThingsData() {
  }
  
}
