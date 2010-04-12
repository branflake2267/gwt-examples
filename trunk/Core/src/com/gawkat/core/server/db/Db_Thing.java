package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thing.ThingFilterData;
import com.gawkat.core.client.account.thing.ThingsData;
import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstuff.ThingStuffFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffsData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingJdo;
import com.gawkat.core.server.jdo.data.ThingStuffJdo;

public class Db_Thing {

  private ServerPersistence sp = null;

  public Db_Thing(ServerPersistence sp) {
    this.sp  = sp;
  }
  
  /**
   * get things
   * 
   * @param accessToken
   * @param filter
   * @return
   */
  public ThingsData getThings(OAuthTokenData accessToken, ThingFilterData filter) {
    
  	ThingJdo tj = new ThingJdo(sp);
    ThingData[] td = tj.query(filter);
    
    Db_ThingType dbt = new Db_ThingType(sp);
    ThingTypesData tdt = dbt.getThingTypes(null); // TODO filter later
    
    ThingsData r = new ThingsData();
    r.thingData = td;
    r.thingTypesData = tdt;
    r.total = tj.queryTotal();
    
    return r;
  }
  
  /**
   * save things
   * 
   * @param accessToken
   * @param filter
   * @param thingData
   * @return
   */
  public ThingsData saveThings(OAuthTokenData accessToken, ThingFilterData filter, ThingData[] thingData) {
    
  	if (thingData == null) {
  		return null;
  	}
  	
  	ThingJdo tj = new ThingJdo(sp);
  	tj.save(thingData);
  	
  	ThingsData tsd = getThings(accessToken, filter);
  	
    return tsd;
  }
  
  /**
   * delete thing
   * 
   * @param accessToken
   * @param thingData
   * @return
   */
  public boolean deleteThing(OAuthTokenData accessToken, ThingData thingData) {
  	
  	if (thingData == null) {
  		return true;
  	}
  	
  	ThingJdo tj = new ThingJdo(sp);
    boolean b = tj.delete(thingData);
    
    return b;
  }
  
  public ThingData getThing(OAuthTokenData accessToken, ThingFilterData filter, long thingId) {
  	
  	// get thing
  	ThingJdo tj = new ThingJdo(sp);
  	ThingData td = tj.query(thingId);
  	
  	// get thing stuffs
  	Db_ThingStuff dbTs = new Db_ThingStuff(sp);
  	ThingStuffFilterData f = new ThingStuffFilterData();
  	f.thingId = thingId;
  	ThingStuffsData tsd = dbTs.getThingStuffData(accessToken, f);
  	td.setThingStuffsData(tsd);
  	
  	return td;
  }

	public ThingData saveThing(OAuthTokenData accessToken, ThingFilterData filter, ThingData thingData) {
		
		if (thingData == null) {
			return null;
		}
		
		ThingJdo tj = new ThingJdo(sp);
		
		// save thing
		long thingId = tj.save(thingData);
		
		
		// save thing stuff
		ThingStuffsData tsd = thingData.getThingStuffsData();
		
		if (tsd != null &&  tsd.thingStuffData != null) {
			
			for (int i=0; i < tsd.thingStuffData.length; i++) {
				tsd.thingStuffData[i].setThingId(thingId);
			}

		}
		
		// filter by thingId
		ThingStuffFilterData f = new ThingStuffFilterData();
		f.thingId = thingId;
		
		// get thing stuff
		Db_ThingStuff dbTs = new Db_ThingStuff(sp);
		dbTs.saveThingStuffData(accessToken, f, tsd.thingStuffData);
		
		// get thing
		ThingData r = getThing(accessToken, filter, thingId);
	  return r;
  }
  

  
}
