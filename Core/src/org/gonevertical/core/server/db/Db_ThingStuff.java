package org.gonevertical.core.server.db;

import java.util.logging.Logger;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeDataFilter;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypesData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.ThingJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

public class Db_ThingStuff {

	private static final Logger log = Logger.getLogger(Db_ThingStuff.class.getName());
	
  private ServerPersistence sp = null;
  
  private ThingJdo dbThing;
	private ThingStuffJdo dbThingStuffJdo;

	/**
	 * construtor
	 * 
	 * @param sp - session server persitence 
	 */
  public Db_ThingStuff(ServerPersistence sp) {
    this.sp = sp;
    dbThing = new ThingJdo(sp);
    dbThingStuffJdo = new ThingStuffJdo(sp);
  }
  
  public ThingStuffsData getThingStuffsData(OAuthTokenData accessToken, ThingStuffDataFilter filter) {
    
  	// TODO authorization
  	
  	// get stuff - set filter up
  	ThingStuffJdo tsj = new ThingStuffJdo(sp);
    ThingStuffData[] thingStuffData = tsj.query(filter);
    
    // get stuff children
    if (thingStuffData != null) {
      
    	for (int i=0; i < thingStuffData.length; i++) {
      	
    		// parent id
      	long parentStuffId = thingStuffData[i].getStuffId();
      	
      	// filter by parent stuff id
      	ThingStuffDataFilter filterByParent = new ThingStuffDataFilter();
      	filterByParent.setParentThingId(thingStuffData[i].getParentThingId());
      	filterByParent.setParentStuffId(parentStuffId);
      	
      	// get the child stuff (by parentId StuffId)
      	ThingStuffData[] stuffData_child = dbThingStuffJdo.query(filterByParent);
      	
      	// setup thing childs and set it in stuff
      	if (stuffData_child != null && stuffData_child.length > 0) {
        	ThingStuffsData stuffsData_childs = new ThingStuffsData();
        	stuffsData_childs.setTotal(stuffData_child.length);
        	stuffsData_childs.setThingStuffData(stuffData_child);
        
        	thingStuffData[i].setThingStuffChilds(stuffsData_childs);
      	}
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
  
  private boolean save(ThingStuffData stuffData) {
  
  	if (stuffData == null) {
  		return true;
  	}
  	
  	
    long parentStuffId = dbThingStuffJdo.save(stuffData);
    
    
    // save child the thingStuffs
    ThingStuffsData childStuffsData = stuffData.getChildStuffs();

    if (childStuffsData == null || childStuffsData.getThingStuffData() == null) {
    	return true;
    }
    
    ThingStuffData[] stuffData_Childs = childStuffsData.getThingStuffData();
    
    // save thingstuff child
    for (int i=0; i < stuffData_Childs.length; i++) {
    	
    	// set the parent id
    	stuffData_Childs[i].setParentStuffId(parentStuffId);
    	
    	// save
    	dbThingStuffJdo.save(stuffData_Childs[i]);
    	
    }
    
    return true;
  }

  private long getThingTypeId(ThingStuffData thingStuffData) {
	  
  	if (thingStuffData == null) {
  		return 0;
  	}
  	
  	ThingData thingData = dbThing.query(thingStuffData.getParentThingId());
  	
  	long thingTypeId = 0; 
  	if (thingData != null) {
  		thingTypeId = thingData.getThingTypeId();
  	}
  	
	  return thingTypeId;
  }

	/**
   * delete stuff
   * 
   * @param accessToken
   * @param stuffId
   * @return
   */
  public boolean deleteThingStuffData(OAuthTokenData accessToken, long stuffId) {
  	
  	// TODO authorization
  	
  	// delete stuff
    boolean b = dbThingStuffJdo.delete(stuffId);
    
    // delete the stuffs children
    long parentStuffId = stuffId;
    
    // delete stuff children
    // TODO - not sure if there is any parents, but could use some more error checking on return?
    dbThingStuffJdo.deleteByParentStuffId(parentStuffId);
    
    return b;
  }
  	
	public void createThingStuff_Unique(long thingId, long thingTypeId, int stuffTypeId, long value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setParentThingId(thingId);
		ts2.setStuffTypeId(stuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

	public void createThingStuff_Unique(long thingId, long thingTypeId, int stuffTypeId, String value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setParentThingId(thingId);
		ts2.setStuffTypeId(stuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

	public void createThingStuff_Unique(long thingId, long thingTypeId, int stuffTypeId, boolean value) {

		ThingStuffData ts2 = new ThingStuffData();
		ts2.setParentThingId(thingId);
		ts2.setStuffTypeId(stuffTypeId);
		ts2.setValue(value);

		ThingStuffJdo tsj2 = new ThingStuffJdo(sp);
		tsj2.saveUnique(ts2);

	}

}
