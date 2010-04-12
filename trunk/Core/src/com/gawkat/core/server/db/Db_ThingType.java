package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypeFilterData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

public class Db_ThingType {
  
  private ServerPersistence sp = null;
  
  /**
   * constructor
   */
  public Db_ThingType(ServerPersistence sp) {
    this.sp = sp;
  }
  
  /**
   * get thing types - application, group, user, ...
   * 
   * @param filter
   * @return
   */
  public ThingTypesData getThingTypes(ThingTypeFilterData filter) {
  	
  	ThingTypeJdo ttj = new ThingTypeJdo(sp);
  	
    ThingTypeJdo[] thingTypeJdo = ttj.query(filter);
    ThingTypeData[] t = ThingTypeJdo.convert(thingTypeJdo);
    
    ThingTypesData r = new ThingTypesData();
    r.thingTypeData = t;
    r.total = ttj.queryTotal();
    
    return r;
  }
  
  /**
   * save thing types
   * 
   * @param filter
   * @param thingTypeData
   * @return
   */
  public ThingTypesData saveThingTypes(ThingTypeFilterData filter, ThingTypeData[] thingTypeData) {
    for (int i=0; i < thingTypeData.length; i++) {
      save(thingTypeData[i]);
    }
    
    ThingTypesData r = getThingTypes(filter); 
    return r;
  }

  private void save(ThingTypeData thingTypeData) {

    ThingTypeJdo j = new ThingTypeJdo(sp);
    j.setData(thingTypeData);
    j.insertUnique();
    
  }

  public boolean delete(OAuthTokenData accessToken, ThingTypeData thingTypeData) {
  	
  	ThingTypeJdo ttj = new ThingTypeJdo(sp);
  	
    boolean b = ttj.deleteThingTypeDataJdo(thingTypeData);
    
    return b;
  }
  
  
  
  
}
