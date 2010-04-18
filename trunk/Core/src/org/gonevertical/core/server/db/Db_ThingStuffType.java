package org.gonevertical.core.server.db;

import org.gonevertical.core.client.account.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.account.thingstufftype.ThingStuffTypeFilterData;
import org.gonevertical.core.client.account.thingstufftype.ThingStuffTypesData;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.ThingStuffTypeJdo;

public class Db_ThingStuffType {

  private ServerPersistence sp = null;
  
  public Db_ThingStuffType(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public ThingStuffTypesData getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeFilterData filter) {
  	ThingStuffTypeJdo tstj = new ThingStuffTypeJdo(sp);
    ThingStuffTypeJdo[] thingStuffTypeJdo = tstj.query(filter);
    ThingStuffTypeData[] t = ThingStuffTypeJdo.convert(thingStuffTypeJdo);
    
    ThingStuffTypesData r = new ThingStuffTypesData();
    r.thingStuffTypeData = t;
    r.total = tstj.queryTotal();
    
    return r;
  }

  public ThingStuffTypesData saveThingStuffTypes(OAuthTokenData accessToken,ThingStuffTypeFilterData filter, ThingStuffTypeData[] thingStuffTypeData) {
    for (int i=0; i < thingStuffTypeData.length; i++) {
      save(thingStuffTypeData[i]);
    }
    
    ThingStuffTypesData r = getThingStuffTypes(accessToken, filter); 
    return r;
  }

  private void save(ThingStuffTypeData thingStuffTypeData) {
    ThingStuffTypeJdo j = new ThingStuffTypeJdo(sp);
    j.save(thingStuffTypeData);
  }

  public boolean delete(OAuthTokenData accessToken, ThingStuffTypeData thingStuffTypeData) {
  	ThingStuffTypeJdo tstj = new ThingStuffTypeJdo(sp);
    boolean b = tstj.delete(thingStuffTypeData);
    return b;
  }
  
}
