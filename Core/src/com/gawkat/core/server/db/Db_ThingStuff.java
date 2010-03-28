package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstuff.ThingStuffFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffsData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingStuffAboutJdo;
import com.gawkat.core.server.jdo.data.ThingStuffJdo;

public class Db_ThingStuff {

  private ServerPersistence sp = null;
  
	private ThingStuffJdo dbThingStuffJdo;

	private ThingStuffAboutJdo dbThingStuffAboutJdo;
  
  public Db_ThingStuff(ServerPersistence sp) {
    this.sp = sp;
    dbThingStuffJdo = new ThingStuffJdo(sp);
    dbThingStuffAboutJdo = new ThingStuffAboutJdo(sp);
  }
  
  public ThingStuffsData getThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter) {
    
  	// get stuff
  	ThingStuffJdo tsj = new ThingStuffJdo(sp);
    ThingStuffData[] thingStuffData = tsj.query(filter);
    
    // get stuff about
    if (thingStuffData != null) {
      
    	for (int i=0; i < thingStuffData.length; i++) {
      	
    		// parent id
      	long stuffId = thingStuffData[i].getStuffId();
      	
      	// setup filter (of parent id)
      	ThingStuffFilterData filterByParent = new ThingStuffFilterData();
      	filterByParent.thingId = thingStuffData[i].getThingId();
      	filterByParent.thingStuffId = stuffId;
      	
      	// get the about stuff (by parentId StuffId)
      	ThingStuffData[] thingStuffDataAbout = dbThingStuffAboutJdo.query(filterByParent);
      	
      	// setup thing About Stuffs and set it in stuff
      	ThingStuffsData thingStuffsDataAbout = new ThingStuffsData();
      	thingStuffsDataAbout.total = thingStuffDataAbout.length;
      	thingStuffsDataAbout.thingStuffData = thingStuffDataAbout;
      
      	thingStuffData[i].setThingStuffsAbout(thingStuffsDataAbout);
      }
    	
    }
    
    // get the thing types to choose from
    Db_ThingStuffType t = new Db_ThingStuffType(sp);
    ThingStuffTypesData thingStuffTypesData = t.getThingStuffTypes(accessToken, null); // TODO filter ?
    
    ThingStuffsData r = new ThingStuffsData();
    r.total = 0; // TODO later
    r.thingStuffData = thingStuffData;
    r.thingStuffTypesData = thingStuffTypesData;
    
    return r;
  }
  
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter, ThingStuffData[] thingStuffData) {
    
  	if (thingStuffData == null) {
  		return null;
  	}
  	
    for (int i=0; i < thingStuffData.length; i++) {
      save(thingStuffData[i]);
    }
    
    // load stuff and return it agian
    ThingStuffsData r = getThingStuffData(accessToken, filter);
    
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
    boolean b = dbThingStuffJdo.delete(thingStuffId);
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
  	ThingStuffAboutJdo tsaj = new ThingStuffAboutJdo(sp);
  	boolean b = tsaj.delete(thingStuffAboutId);
    return b;
  }
  

}
