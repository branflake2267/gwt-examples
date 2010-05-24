package org.gonevertical.core.server.db;

import java.util.logging.Logger;

import org.gonevertical.core.client.account.thingtype.ThingTypeData;
import org.gonevertical.core.client.account.thingtype.ThingTypeDataFilter;
import org.gonevertical.core.client.account.thingtype.ThingTypesData;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.ThingTypeJdo;

public class Db_ThingType {
  
	private static final Logger log = Logger.getLogger(Db_ThingType.class.getName());
	
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
  public ThingTypesData getThingTypes(ThingTypeDataFilter filter) {
  	
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
  public ThingTypesData saveThingTypes(ThingTypeDataFilter filter, ThingTypeData[] thingTypeData) {
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
