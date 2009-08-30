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

import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeFilterData;
import com.gawkat.core.server.jdo.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingStuffTypeJdo {

  public static final int VT_STRING = 0;
  public static final int VT_BOOLEAN = 1;
  public static final int VT_DOUBLE = 2;
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private long stuffTypeId;
  
  @Persistent
  private String name = null;
  
  @Persistent
  private int valueTypeId = VT_STRING;
  
  @Persistent
  private Date dateCreated;
  
  @Persistent
  private Date dateUpdated;
  
  
  public ThingStuffTypeJdo() {
  }
  
  public ThingStuffTypeJdo(ThingStuffTypeData thingStuffTypeData) {
    this.stuffTypeId = thingStuffTypeData.getId();
    this.name = thingStuffTypeData.getName();
    this.valueTypeId = thingStuffTypeData.getValueTypeId();
  }
  
  public long getId() {
    return stuffTypeId;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public int getValueTypeId() {
    return valueTypeId;
  }

  /**
   * can only insert unique names
   * 
   * @param name
   */
  public void insert() {
    this.dateCreated = new Date();
    
    // don't insert if name already exists
    ThingStuffTypeJdo[] tt = query(name);
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
    
    System.out.println("saved: Id:" + getId());
  }
  
  public static ThingStuffTypeJdo[] query(String name) {
    
    ArrayList<ThingStuffTypeJdo> aT = new ArrayList<ThingStuffTypeJdo>();
    
    String qfilter = "name==\"" + name + "\"";
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<ThingStuffTypeJdo> e = pm.getExtent(ThingStuffTypeJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();

      Collection<ThingStuffTypeJdo> c = (Collection<ThingStuffTypeJdo>) q.execute();
      Iterator<ThingStuffTypeJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingStuffTypeJdo t = (ThingStuffTypeJdo) iter.next();
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
    

    ThingStuffTypeJdo[] r = null;
    if (aT.size() > 0) {
      r = new ThingStuffTypeJdo[aT.size()];
      aT.toArray(r);
    }
    
    return r;
  }
  
  public static ThingStuffTypeJdo[] query(ThingStuffTypeFilterData filter) {

    // TODO configure drill to setup filters

    ArrayList<ThingStuffTypeJdo> aT = new ArrayList<ThingStuffTypeJdo>();

    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      // TODO build filter
      Extent<ThingStuffTypeJdo> e = pm.getExtent(ThingStuffTypeJdo.class, true);
      Query q = pm.newQuery(e);
      q.setRange(0, 10); // TODO - finish range
      q.execute();
      
      Collection<ThingStuffTypeJdo> c = (Collection<ThingStuffTypeJdo>) q.execute();
      Iterator<ThingStuffTypeJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingStuffTypeJdo t = (ThingStuffTypeJdo) iter.next();
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

    ThingStuffTypeJdo[] r = null;
    if (aT.size() > 0) {
      r = new ThingStuffTypeJdo[aT.size()];
      aT.toArray(r);
    }
    return r;
  }
  
  public static boolean delete(ThingStuffTypeData thingStuffTypeData) {
    
    ThingStuffTypeJdo ttj = new ThingStuffTypeJdo(thingStuffTypeData);
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    boolean b = false;
    try {
      tx.begin();

      ThingStuffTypeJdo ttj2 = (ThingStuffTypeJdo) pm.getObjectById(ThingStuffTypeJdo.class, ttj.getId());
      pm.deletePersistent(ttj2);
      
      tx.commit();
      b = true;
    } catch (Exception e) {
      e.printStackTrace();
      b = false;
    } finally {
      if (tx.isActive()) {
        tx.rollback();
        b = false;
      }
      pm.close();
    }
    
    return b;
  }
  
  /**
   * convert from jdo to data object type for rpc transit
   * 
   * @param thingStuffTypeJdo
   * @return
   */
  public static ThingStuffTypeData[] convert(ThingStuffTypeJdo[] thingStuffTypeJdo) {
    if (thingStuffTypeJdo == null) {
      return null;
    }
    ThingStuffTypeData[] r = new ThingStuffTypeData[thingStuffTypeJdo.length];
    for (int i=0; i < thingStuffTypeJdo.length; i++) {
      r[i] = new ThingStuffTypeData();
      r[i].setData(thingStuffTypeJdo[i].getId(), thingStuffTypeJdo[i].getName(), thingStuffTypeJdo[i].getValueTypeId());
    }
    return r;
  }
  
  
}
