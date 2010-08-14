package org.gonevertical.core.server.db;

import java.util.Date;
import java.util.logging.Logger;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeDataFilter;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypesData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.ThingStuffTypeJdo;

public class Db_ThingStuffType {
	
	private static final Logger log = Logger.getLogger(Db_ThingStuffType.class.getName());

  private ServerPersistence sp = null;
  
  public Db_ThingStuffType(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public ThingStuffTypesData getThingStuffTypes(OAuthTokenData accessToken, ThingStuffTypeDataFilter filter) {
  	ThingStuffTypeJdo tstj = new ThingStuffTypeJdo(sp);
    ThingStuffTypeJdo[] thingStuffTypeJdo = tstj.query(filter);
    ThingStuffTypeData[] t = ThingStuffTypeJdo.convert(thingStuffTypeJdo);
    
    ThingStuffTypesData r = new ThingStuffTypesData();
    r.setThingStuffTypeData(t);
    r.setTotal(tstj.queryTotal());
    
    return r;
  }

  public ThingStuffTypesData saveThingStuffTypes(OAuthTokenData accessToken,ThingStuffTypeDataFilter filter, ThingStuffTypeData[] thingStuffTypeData) {
    for (int i=0; i < thingStuffTypeData.length; i++) {
      save(thingStuffTypeData[i]);
    }
    
    ThingStuffTypesData r = getThingStuffTypes(accessToken, filter); 
    return r;
  }

  private void save(ThingStuffTypeData thingStuffTypeData) {
    ThingStuffTypeJdo j = new ThingStuffTypeJdo(sp);
    j.save(thingStuffTypeData);
  }

  public boolean delete(OAuthTokenData accessToken, ThingStuffTypeData thingStuffTypeData) {
  	ThingStuffTypeJdo tstj = new ThingStuffTypeJdo(sp);
    boolean b = tstj.delete(thingStuffTypeData);
    return b;
  }
  
  /**
	 * create stuff type for set defaults
	 * 
	 * @param id
	 * @param name
	 * @param valueTypeId
	 */
	public void createStuffType(int id, String name, int valueTypeId) {
		
		Date startOf = null;
		Date endOf = null;
		Double rank = null;
		long createdBy = 0;
		Date dateCreated = new Date();
		long updatedBy = 0;
		Date dateUpdated = null;
		long[] ownerThingIds = null;
		
		ThingStuffTypeData tstd = new ThingStuffTypeData();
		tstd.setData(
				id, 
				name, 
				valueTypeId, 
				startOf, 
				endOf, 
				rank, 
				createdBy, 
				dateCreated, 
				updatedBy, 
				dateUpdated, 
				ownerThingIds);
		
		ThingStuffTypeJdo tstj = new ThingStuffTypeJdo(sp);
		tstj.setData(tstd);
		tstj.insertUnique();
	}
  
}
