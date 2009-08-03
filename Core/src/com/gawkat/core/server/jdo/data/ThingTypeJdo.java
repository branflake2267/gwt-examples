package com.gawkat.core.server.jdo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.admin.thingtype.ThingTypeFilterData;
import com.gawkat.core.server.jdo.PMF;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingTypeJdo {

  // required thing types
  public static final int TYPE_APPLICATION = 1;
  public static final int TYPE_USER = 2;
  public static final int TYPE_GROUP = 3;
  
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long thingTypeId;
  
  @Persistent
  private String name;
  
  @Persistent
  private Date dateCreated;
  
  @Persistent
  private Date dateUpdated;
  
  /**
   * get Identity
   * 
   * @return
   */
  public Long getId() {
    return this.thingTypeId;
  }
  
  /**
   * get thingType name
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * can only insert unique names
   * 
   * @param name
   */
  public void insert(String name) {
    this.name = name;
    this.dateCreated = new Date();
    
    // don't insert if name already exists
    ThingTypeJdo[] tt = query(name);
    if (tt != null && tt.length > 0) {
      return;
    }
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      pm.makePersistent(this);
      tx.commit();
    } finally {
      if (tx.isActive()) {
          tx.rollback();
      }
      pm.close();
    }
    
    System.out.println("saved: thingTypeId:" + getId());
  }
  
  /**
   * query thingType
   * 
   * @param name
   * @return
   */
  public static ThingTypeJdo[] query(String name) {
    
    ArrayList<ThingTypeJdo> aT = new ArrayList<ThingTypeJdo>();
    
    String qfilter = "name==\"" + name + "\"";
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<ThingTypeJdo> e = pm.getExtent(ThingTypeJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();
      
      Collection<ThingTypeJdo> c = (Collection<ThingTypeJdo>) q.execute();
      Iterator<ThingTypeJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingTypeJdo t = (ThingTypeJdo) iter.next();
        aT.add(t);
      }

      tx.commit();
      q.closeAll();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    
    ThingTypeJdo[] r = new ThingTypeJdo[aT.size()];
    aT.toArray(r);
    return r;
  }
  
  /**
   * query thingType by filter
   * 
   * @param filter
   * @return
   */
  public static ThingTypeJdo[] query(ThingTypeFilterData filter) {

    // TODO configure drill to setup filters

    ArrayList<ThingTypeJdo> aT = new ArrayList<ThingTypeJdo>();

    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      // TODO build filter
      Extent<ThingTypeJdo> e = pm.getExtent(ThingTypeJdo.class, true);
      Query q = pm.newQuery(e);
      q.setRange(0, 10); // TODO - finish range
      q.execute();
      
      Collection<ThingTypeJdo> c = (Collection<ThingTypeJdo>) q.execute();
      Iterator<ThingTypeJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingTypeJdo t = (ThingTypeJdo) iter.next();
        aT.add(t);
      }

      tx.commit();
      q.closeAll();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }

    ThingTypeJdo[] r = new ThingTypeJdo[aT.size()];
    aT.toArray(r);
    return r;
  }
  
  /**
   * convert from jdo to data object type for rpc transit
   * 
   * @param thingTypeJdo
   * @return
   */
  public static ThingTypeData[] convert(ThingTypeJdo[] thingTypeJdo) {
    ThingTypeData[] r = new ThingTypeData[thingTypeJdo.length];
    for (int i=0; i < thingTypeJdo.length; i++) {
      r[i] = new ThingTypeData();
      r[i].set(thingTypeJdo[i].getId(), thingTypeJdo[i].getName());
    }
    return r;
  }
  
  
}
