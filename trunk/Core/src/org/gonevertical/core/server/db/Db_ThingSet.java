package org.gonevertical.core.server.db;

import org.gonevertical.core.client.account.thing.ThingData;
import org.gonevertical.core.client.account.thing.ThingFilterData;
import org.gonevertical.core.client.account.thingset.ThingSetOfData;
import org.gonevertical.core.client.account.thingset.ThingSetOfFilterData;
import org.gonevertical.core.client.account.thingstuff.ThingStuffFilterData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.jdo.data.ThingJdo;
import org.gonevertical.core.server.jdo.data.ThingStuffJdo;

public class Db_ThingSet {

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
    
    ThingFilterData filter = null;
    
    ThingJdo tj = new ThingJdo(sp);
    ThingData[] things = tj.query(filter);
    
    ThingSetOfData[] sets = new ThingSetOfData[things.length];
    for (int i=0; i < things.length; i++) {
      sets[i] = new ThingSetOfData();
      
      sets[i].thingData = things[i];
      
      ThingStuffFilterData thingStuffFilter = new ThingStuffFilterData();
      thingStuffFilter.thingId = things[i].getThingId();
      
      ThingStuffJdo tsj = new ThingStuffJdo(sp);
      sets[i].thingStuffData = tsj.query(thingStuffFilter);
    }
    
    return sets;
  }
  
  
}
