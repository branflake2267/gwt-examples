package org.gonevertical.core.server.db;

import java.util.logging.Logger;

import org.gonevertical.core.client.admin.thing.ThingData;
import org.gonevertical.core.client.admin.thing.ThingDataFilter;
import org.gonevertical.core.client.admin.thing.ThingsData;
import org.gonevertical.core.client.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.admin.thingtype.ThingTypesData;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.ThingJdo;

public class Db_Thing {
	
	private static final Logger log = Logger.getLogger(Db_Thing.class.getName());

  private ServerPersistence sp = null;
	private Db_ThingStuff dbTs;

  public Db_Thing(ServerPersistence sp) {
    this.sp  = sp;
    dbTs = new Db_ThingStuff(sp);
  }
  
  /**
   * get things
   * 
   * @param accessToken
   * @param filter
   * @return
   */
  public ThingsData getThings(OAuthTokenData accessToken, ThingDataFilter filter) {
    
  	ThingJdo tj = new ThingJdo(sp);
    ThingData[] td = tj.query(filter);
    
    Db_ThingType dbt = new Db_ThingType(sp);
    ThingTypesData tdt = dbt.getThingTypes(null); // TODO filter later
    
    ThingsData r = new ThingsData();
    r.thingData = td;
    r.thingTypesData = tdt;
    r.total = tj.queryTotal();
    
    // get names for the things
    td = getNamesForThings(accessToken, td);
    
    return r;
  }
  
  private ThingData[] getNamesForThings(OAuthTokenData accessToken, ThingData[] td) {
	  
  	if (td == null || td.length == 0) {
  		return td;
  	}
  	
  	// get name for things
  	for (int i=0; i < td.length; i++) {
  		
  		ThingStuffDataFilter f = new ThingStuffDataFilter();
    	f.setThingId(td[i].getThingId());
    	f.setThingStuffTypeId(1); // only get name
    	
    	ThingStuffData[] tsd = dbTs.getThingStuffData(accessToken, f);
    	if (tsd != null && tsd.length > 0) {
      	ThingStuffsData tsds= new ThingStuffsData();
      	tsds.setThingStuffData(tsd);
      	td[i].setThingStuffsData(tsds);
    	}
    	
  	}
  
  	
	  return td;
  }

	/**
   * save things
   * 
   * @param accessToken
   * @param filter
   * @param thingData
   * @return
   */
  public ThingsData saveThings(OAuthTokenData accessToken, ThingDataFilter filter, ThingData[] thingData) {
    
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
  
  public ThingData getThing(OAuthTokenData accessToken, ThingDataFilter filter, long thingId) {
  	
  	// get thing
  	ThingJdo tj = new ThingJdo(sp);
  	ThingData td = tj.query(thingId);
  	
  	// get thing stuffs
  	
  	ThingStuffDataFilter f = new ThingStuffDataFilter();
  	f.setThingId(thingId);
  	ThingStuffsData tsd = dbTs.getThingStuffsData(accessToken, f);
  	td.setThingStuffsData(tsd);
  	
  	return td;
  }

	public ThingData saveThing(OAuthTokenData accessToken, ThingDataFilter filter, ThingData thingData) {
		
		if (thingData == null) {
			return null;
		}
		
		ThingJdo tj = new ThingJdo(sp);
		
		// save thing
		long thingId = tj.save(thingData);
		
		
		// save thing stuff
		ThingStuffsData tsd = thingData.getThingStuffsData();
		
		if (tsd != null &&  tsd.getThingStuffData() != null) {
			
			for (int i=0; i < tsd.getThingStuffData().length; i++) {
				tsd.getThingStuffData()[i].setThingId(thingId);
			}

		}
		
		// filter by thingId
		ThingStuffDataFilter f = new ThingStuffDataFilter();
		f.setThingId(thingId);
		
		// get thing stuff
		Db_ThingStuff dbTs = new Db_ThingStuff(sp);
		dbTs.saveThingStuffData(accessToken, f, tsd.getThingStuffData());
		
		// get thing
		ThingData r = getThing(accessToken, filter, thingId);
	  return r;
  }
  

  
}
