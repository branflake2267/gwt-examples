package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstuff.ThingStuffFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffsData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingStuffJdo;

public class Db_ThingStuff {

  private ServerPersistence sp = null;
  
  public Db_ThingStuff(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public ThingStuffsData getThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter) {
    
    ThingStuffData[] thingStuffData = ThingStuffJdo.query(filter);
    
    Db_ThingStuffType t = new Db_ThingStuffType(sp);
    ThingStuffTypesData thingStuffTypesData = t.getThingStuffTypes(accessToken, null); // TODO filter ?
    
    ThingStuffsData r = new ThingStuffsData();
    r.total = 0;
    r.thingStuffData = thingStuffData;
    r.thingStuffTypesData = thingStuffTypesData;
    
    return r;
  }
  
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter, ThingStuffData[] thingStuffData) {
    
    for (int i=0; i < thingStuffData.length; i++) {
      save(thingStuffData[i]);
    }
    
    ThingStuffsData r = getThingStuffData(accessToken, filter);
    return r;
  }
  
  private void save(ThingStuffData thingStuffData) {
    ThingStuffJdo db = new ThingStuffJdo();
    db.save(thingStuffData);
  }

  public boolean deleteThingStuffData(OAuthTokenData accessToken, long thingStuffId) {
    
    return false;
  }
  
}
