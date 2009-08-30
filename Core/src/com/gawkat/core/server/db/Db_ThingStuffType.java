package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeFilterData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypeFilterData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingStuffTypeJdo;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

public class Db_ThingStuffType {

  private ServerPersistence sp = null;
  
  public Db_ThingStuffType(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public ThingStuffTypesData getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeFilterData filter) {
    ThingStuffTypeJdo[] thingStuffTypeJdo = ThingStuffTypeJdo.query(filter);
    ThingStuffTypeData[] t = ThingStuffTypeJdo.convert(thingStuffTypeJdo);
    
    ThingStuffTypesData r = new ThingStuffTypesData();
    r.thingStuffTypeData = t;
    r.total = 0; //TODO - how to ???
    
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
    ThingStuffTypeJdo j = new ThingStuffTypeJdo(thingStuffTypeData);
    j.insert();
  }

  public boolean delete(OAuthTokenData accessToken, ThingStuffTypeData thingStuffTypeData) {
    
    boolean b = ThingStuffTypeJdo.delete(thingStuffTypeData);
    
    return b;
  }
  
}
