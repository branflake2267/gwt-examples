package org.gonevertical.core.server.db;

import java.util.logging.Logger;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.oauth.Sha1;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thing.ThingDataFilter;
import org.gonevertical.core.client.ui.admin.thing.ThingsData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypesData;
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
    r.setThingData(td);
    r.setThingTypesData(tdt);
    r.setTotal(tj.queryTotal());
    
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
    	f.setParentThingId(td[i].getThingId());
    	f.setStuffTypeId(1); // only get name
    	
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
  
  public ThingData getThing(OAuthTokenData accessToken, ThingDataFilter filter) {
  	
  	// get thing
  	ThingJdo tj = new ThingJdo(sp);
  	ThingData td = tj.query(filter.getThingId());
  	
  	// get thing stuffs
  	ThingStuffDataFilter f = new ThingStuffDataFilter();
  	f.setParentThingId(filter.getThingId());
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
				tsd.getThingStuffData()[i].setParentThingId(thingId);
			}

		}
		
		// filter by thingId
		ThingStuffDataFilter f = new ThingStuffDataFilter();
		f.setParentThingId(thingId);
		
		// get thing stuff
		if (tsd != null && tsd.getThingStuffData() != null) {
			Db_ThingStuff dbTs = new Db_ThingStuff(sp);
			dbTs.saveThingStuffData(accessToken, f, tsd.getThingStuffData());
		}
		
		// get thing
		//filter.setThingId(thingId); // TODO should I need this
		System.out.println("Db_Thing.save() thingId=" + thingId);
		if (thingId <= 0) {
			System.out.println("Db_Thing.save() ERROROROROROROROROROROR*****************");
		}
		ThingData r = getThing(accessToken, filter);
	  return r;
  }
  
	/**
	 * moved to dataJoinJdo
	 * 
	 * @param accessToken
	 * @param thingIdLink
	 * @return
	 */
	@Deprecated
	public String getThingIds(OAuthTokenData accessToken, long thingIdLink) {
		
		ThingStuffDataFilter filter = new ThingStuffDataFilter();
		filter.setStuffTypeId(ThingStuffTypeData.THINGSTUFFTYPE_LINK);
		filter.setValueLong(thingIdLink);
		
		ThingStuffData[] tsd = dbTs.getThingStuffData(accessToken, filter);
		
		// TODO not sure this should happen
		if (tsd == null || tsd.length == 0) {
			return null;
		}
		
		long[] links = new long[tsd.length];
		for (int i=0; i < tsd.length; i++) {
			links[i] = tsd[i].getValueLong();
		}
		
		String s = null;
		if (links.length > 0) {
			if (s == null) {
				s = "";
			}
			for (int i=0; i < links.length; i++) {
				s += links[i];
				if (i < links.length -1) {
					s += ",";
				}
			}
		
		}
		
		return s;
	}
	
	/**
	 * create a thing from set defaults
	 * 
	 * @param id
	 * @param thingTypeId
	 * @param key
	 * @param password
	 */
	public void createThing(int id, int thingTypeId, String key, String password) {
		Sha1 sha = new Sha1();

		String secret = null;
		if (password != null) {
			secret = sha.hex_hmac_sha1(ClientPersistence.PASSWORD_SALT, password);
		}
		
		//debug
		System.out.println("SetDefaults.createThing(): key=" + key + " password=" + password);

		ThingJdo a = new ThingJdo(sp);
		a.setThingId(id);
		a.insertUnique(thingTypeId, key, secret);
	}
  
}
