package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thing.ThingFilterData;
import com.gawkat.core.client.account.thing.ThingsData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingJdo;

public class Db_Thing {

  private ServerPersistence sp = null;

  public Db_Thing(ServerPersistence sp) {
    this.sp  = sp;
  }
  
  public ThingsData getThings(OAuthTokenData accessToken, ThingFilterData filter) {
    
    ThingData[] td = ThingJdo.query(filter);
    
    Db_ThingType dbt = new Db_ThingType(sp);
    ThingTypesData tdt = dbt.getThingTypes(null); // TODO filter later
    
    ThingsData r = new ThingsData();
    r.thingData = td;
    r.thingTypesData = tdt;
    
    return r;
  }
  
  public ThingsData saveThings(OAuthTokenData accessToken, ThingFilterData filter, ThingData[] thingData) {
    
    return null;
  }
  
  public boolean deleteThing(OAuthTokenData accessToken, ThingData thingData) {
    
    return false;
  }
  
}
