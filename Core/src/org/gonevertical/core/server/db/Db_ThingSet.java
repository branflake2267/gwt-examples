package org.gonevertical.core.server.db;

import java.util.logging.Logger;

import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thing.ThingDataFilter;
import org.gonevertical.core.client.ui.admin.thingset.ThingSetOfData;
import org.gonevertical.core.client.ui.admin.thingset.ThingSetOfFilterData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.DataJoinJdo;
import org.gonevertical.core.server.jdo.data.ThingJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

public class Db_ThingSet {
	
	private static final Logger log = Logger.getLogger(Db_ThingSet.class.getName());

  private ServerPersistence sp = null;
  
  public Db_ThingSet(ServerPersistence sp) {
    this.sp = sp;
  }
  
  /**
   * get things and there stuff
   * 
   *   Moved the data into one group
   * 
   * @param thingSetOfFilterData
   * @return
   */
  @Deprecated
  public ThingSetOfData[] getThingSet(ThingSetOfFilterData thingSetOfFilterData) {
    
    ThingDataFilter filter = null;
    
    ThingJdo tj = new ThingJdo(sp);
    ThingData[] things = tj.query(filter);
    
    ThingSetOfData[] sets = new ThingSetOfData[things.length];
    for (int i=0; i < things.length; i++) {
      sets[i] = new ThingSetOfData();
      
      sets[i].thingData = things[i];
      
      ThingStuffDataFilter thingStuffFilter = new ThingStuffDataFilter();
      thingStuffFilter.setParentThingId(things[i].getThingId());
      
      ThingStuffJdo tsj = new ThingStuffJdo(sp);
      sets[i].thingStuffData = tsj.query(thingStuffFilter);
    }
    
    return sets;
  }
  
  
}
