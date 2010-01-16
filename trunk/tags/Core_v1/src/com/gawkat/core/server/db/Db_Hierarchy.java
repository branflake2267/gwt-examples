package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thing.hierarchy.HierarchyData;
import com.gawkat.core.client.account.thing.hierarchy.HierarchyFilterData;
import com.gawkat.core.client.account.thingset.ThingSetOfData;
import com.gawkat.core.client.account.thingset.ThingSetOfFilterData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;

public class Db_Hierarchy {

  private ServerPersistence sp = null;
  
  public Db_Hierarchy(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public HierarchyData getHierarchy(OAuthTokenData accessToken, HierarchyFilterData filter) {
    
    ThingSetOfFilterData thingSetOfFilterData = new ThingSetOfFilterData();
    
    Db_ThingSet dbTs = new Db_ThingSet(sp);
    ThingSetOfData[] tsd = dbTs.getThingSet(thingSetOfFilterData);
    
    HierarchyData h = new HierarchyData();
    h.thingSetData = tsd;
    
    return h;
  }
  
}
