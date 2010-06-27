package org.gonevertical.core.server.db;

import java.util.logging.Logger;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeDataFilter;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypesData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.ThingStuffAboutJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

public class Db_ThingStuff {

	private static final Logger log = Logger.getLogger(Db_ThingStuff.class.getName());
	
  private ServerPersistence sp = null;
  
	private ThingStuffJdo dbThingStuffJdo;

	private ThingStuffAboutJdo dbThingStuffAboutJdo;
  
	/**
	 * construtor
	 * 
	 * @param sp - session server persitence 
	 */
  public Db_ThingStuff(ServerPersistence sp) {
    this.sp = sp;
    dbThingStuffJdo = new ThingStuffJdo(sp);
    dbThingStuffAboutJdo = new ThingStuffAboutJdo(sp);
  }
  
  public ThingStuffsData getThingStuffsData(OAuthTokenData accessToken, ThingStuffDataFilter filter) {
    
  	// TODO authorization
  	
  	// get stuff - set filter up
  	ThingStuffJdo tsj = new ThingStuffJdo(sp);
    ThingStuffData[] thingStuffData = tsj.query(filter);
    
    // get stuff about
    if (thingStuffData != null) {
      
    	for (int i=0; i < thingStuffData.length; i++) {
      	
    		// parent id
      	long stuffId = thingStuffData[i].getStuffId();
      	
      	// setup filter (of parent id)
      	ThingStuffDataFilter filterByParent = new ThingStuffDataFilter();
      	filterByParent.setThingId(thingStuffData[i].getThingId());
      	filterByParent.setThingStuffId(stuffId);
      	
      	// get the about stuff (by parentId StuffId)
      	ThingStuffData[] thingStuffDataAbout = dbThingStuffAboutJdo.query(filterByParent);
      	
      	// setup thing About Stuffs and set it in stuff
      	ThingStuffsData thingStuffsDataAbout = new ThingStuffsData();
      	thingStuffsDataAbout.setTotal(thingStuffDataAbout.length);
      	thingStuffsDataAbout.setThingStuffData(thingStuffDataAbout);
      
      	thingStuffData[i].setThingStuffsAbout(thingStuffsDataAbout);
      }
    	
    }
    
    
    // TODO move filter upline, not sure what I am aiming for yet
    // TODO This will get up to a 100 items for the drop down choice  
    ThingStuffTypeDataFilter stuffTypeDataFilter = new ThingStuffTypeDataFilter();
    
    // get the thing types to choose from
    Db_ThingStuffType t = new Db_ThingStuffType(sp);
    ThingStuffTypesData thingStuffTypesData = t.getThingStuffTypes(accessToken, stuffTypeDataFilter);
    
    ThingStuffsData r = new ThingStuffsData();
    r.setTotal(tsj.queryTotal());
    r.setThingStuffData(thingStuffData);
    r.setThingStuffTypesData(thingStuffTypesData);
    
    return r;
  }
  
  public ThingStuffData[] getThingStuffData(OAuthTokenData accessToken, ThingStuffDataFilter filter) {
    
  	// TODO authorization
  	
  	// get stuff - set filter up
  	ThingStuffJdo tsj = new ThingStuffJdo(sp);
    ThingStuffData[] thingStuffData = tsj.query(filter);
    
    return thingStuffData;
  }
  
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffDataFilter filter, ThingStuffData[] thingStuffData) {
    
  	// TODO authorization
  	
  	if (thingStuffData == null) {
  		return null;
  	}
  	
    for (int i=0; i < thingStuffData.length; i++) {
      save(thingStuffData[i]);
    }
    
    // load stuff and return it agian
    ThingStuffsData r = getThingStuffsData(accessToken, filter);
    
    return r;
  }
  
  private boolean save(ThingStuffData thingStuffData) {
  
  	if (thingStuffData == null) {
  		return true;
  	}
  	
    long stuffId = dbThingStuffJdo.save(thingStuffData);
    
    
    // ***** below this will save the multi dem About
    
    // save about the thingStuffs
    ThingStuffsData thingStuffsAboutData = thingStuffData.getThingStuffsAbout();

    if (thingStuffsAboutData == null || thingStuffsAboutData.getThingStuffData() == null) {
    	return true;
    }
    
    ThingStuffData[] thingStuffDataAbout = thingStuffsAboutData.getThingStuffData();
    
    // save thingstuff About
    for (int i=0; i < thingStuffDataAbout.length; i++) {
    	
    	// set the parent id
    	thingStuffDataAbout[i].setStuffId(stuffId);
    	
    	// save
    	dbThingStuffAboutJdo.save(thingStuffDataAbout[i]);
    	
    }
    
    return true;
  }

  /**
   * delete stuff
   * 
   * @param accessToken
   * @param thingStuffId
   * @return
   */
  public boolean deleteThingStuffData(OAuthTokenData accessToken, long thingStuffId) {
  	
  	// TODO authorization
  	
  	// delete stuff
    boolean b = dbThingStuffJdo.delete(thingStuffId);
    
    // delete stuff children
    b = dbThingStuffAboutJdo.deleteByParent(thingStuffId);
    
    return b;
  }
  
  /**
   * delete stuff about
   * 
   * @param accessToken
   * @param thingStuffAboutId
   * @return
   */
  public boolean deleteThingStuffAboutData(OAuthTokenData accessToken, long thingStuffAboutId) {
  	
  	// TODO authorization
  	
  	ThingStuffAboutJdo tsaj = new ThingStuffAboutJdo(sp);
  	boolean b = tsaj.delete(thingStuffAboutId);
    return b;
  }
  	
	public void createThingStuff_Unique(long thingId, int thingStuffTypeId, long value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setThingId(thingId);
		ts2.setThingStuffTypeId(thingStuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

	public void createThingStuff_Unique(long thingId, int thingStuffTypeId, String value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setThingId(thingId);
		ts2.setThingStuffTypeId(thingStuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

	public void createThingStuff_Unique(long thingId, int thingStuffTypeId, boolean value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setThingId(thingId);
		ts2.setThingStuffTypeId(thingStuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

}
