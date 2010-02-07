package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thing.ownership.OwnershipData;
import com.gawkat.core.client.account.thing.ownership.OwnershipFilterData;
import com.gawkat.core.client.account.thingset.ThingSetOfData;
import com.gawkat.core.client.account.thingset.ThingSetOfFilterData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;

public class Db_Hierarchy {

  private ServerPersistence sp = null;
  
  public Db_Hierarchy(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public OwnershipData getHierarchy(OAuthTokenData accessToken, OwnershipFilterData filter) {
    
    ThingSetOfFilterData thingSetOfFilterData = new ThingSetOfFilterData();
    
    Db_ThingSet dbTs = new Db_ThingSet(sp);
    ThingSetOfData[] tsd = dbTs.getThingSet(thingSetOfFilterData);
    
    OwnershipData h = new OwnershipData();
    h.thingSetData = tsd;
    
    return h;
  }
  
}
