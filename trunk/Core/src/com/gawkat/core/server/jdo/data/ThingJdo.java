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

import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thing.ThingFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.server.jdo.PMF;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingJdo {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long thingId;
  
  // what type of thing is this, user, group, object?
  @Persistent
  private Long thingTypeId;
  
  @Persistent
  private String key;
  
  @Persistent
  private String secret;
  
  @Persistent
  private Date dateCreated;
  
  @Persistent
  private Date dateUpdated;
  
  /**
   * constructor
   */
  public ThingJdo() {
  }
  
  public void setData(ThingData thingData) {
    this.thingId = thingData.getThingId();
    this.thingTypeId = thingData.getThingTypeId();
    this.key = thingData.getKey();
    //this.secret = thingData.getSecret(); // TODO?
    if (thingId > 0) {
      dateUpdated = new Date();
    } else {
      dateCreated = new Date();
    }
  }
  
  public void setData(ThingJdo thingData) {
    this.thingId = thingData.getThingId();
    this.thingTypeId = thingData.getThingTypeId();
    this.key = thingData.getKey();
    //this.secret = thingData.getSecret(); // TODO?
    if (thingId > 0) {
      dateUpdated = new Date();
    } else {
      dateCreated = new Date();
    }
  }
  
  /**
   * insert thing
   * 
   * @param thingTypeId
   * @param key
   * @param secret
   */
  public void insert(long thingTypeId, String key, String secret) {
    this.thingTypeId = thingTypeId;
    this.key = key;
    this.secret = secret;
    this.dateCreated = new Date();
    
    // do not insert duplicate
    ThingJdo[] tt = ThingJdo.query(thingTypeId, key);
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
  }
  
  public boolean save(ThingData thingData) {
    setData(thingData);

    boolean b = false;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      if (thingId > 0) { // update
        ThingJdo update = pm.getObjectById(ThingJdo.class, thingId);
        update.setData(thingData);
      } else { // insert    
        pm.makePersistent(this);
      }
      tx.commit();
      b = true;
    } finally {
      if (tx.isActive()) {
        tx.rollback();
        b = false;
      }
      pm.close();
    }

    return b;
  }
  
  public long getThingId() {
    return this.thingId;
  }
  
  public long getThingTypeId() {
    return thingTypeId;
  }
  
  public String getKey() {
    return this.key;
  }
  
  public String getSecret() {
    return this.secret;
  }
  
  public static ThingJdo query(long thingId) {
    ThingJdo thing = null;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      thing = pm.getObjectById(ThingJdo.class, thingId);
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    return thing;
  }
  
  /**
   * query thing by key(usernameKey,ApplicationKey,GroupKey,...)
   * 
   * @param key
   * @return
   */
  public static ThingJdo[] query(long thingTypeId, String key) {
    
    ArrayList<ThingJdo> aT = new ArrayList<ThingJdo>();

    String qfilter = "thingTypeId==" + thingTypeId + " && key==\"" + key + "\" ";
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<ThingJdo> e = pm.getExtent(ThingJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();
      
      Collection<ThingJdo> c = (Collection<ThingJdo>) q.execute();
      Iterator<ThingJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingJdo t = (ThingJdo) iter.next();
        aT.add(t);
      }

      tx.commit();
      q.closeAll();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }

    ThingJdo[] r = new ThingJdo[aT.size()];
    if (aT.size() > 0) {
      r = new ThingJdo[aT.size()];
      aT.toArray(r);
    }
    return r;
  }

  public static ThingData[] query(ThingFilterData filter) {
    
    long thingTypeId = filter.thingTypeId;
    
    ArrayList<ThingJdo> aT = new ArrayList<ThingJdo>();

    String qfilter = null;
    if (filter.thingTypeId > 0) {
      qfilter = "thingTypeId==" + thingTypeId + "";
    }
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<ThingJdo> e = pm.getExtent(ThingJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();
      
      Collection<ThingJdo> c = (Collection<ThingJdo>) q.execute();
      Iterator<ThingJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingJdo t = (ThingJdo) iter.next();
        aT.add(t);
      }

      tx.commit();
      q.closeAll();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }

    ThingJdo[] tj = new ThingJdo[aT.size()];
    if (aT.size() > 0) {
      tj = new ThingJdo[aT.size()];
      aT.toArray(tj);
    }
    
    ThingData[] td = convert(tj);
    
    return td;
  }
  
  public static ThingData[] convert(ThingJdo[] thingJdo) {
    ThingData[] r = new ThingData[thingJdo.length];
    for (int i=0; i < thingJdo.length; i++) {
      r[i] = new ThingData();
      r[i].setData(thingJdo[i].thingTypeId, thingJdo[i].thingId, thingJdo[i].key);
    }
    return r;
  }

  // TODO - delete child objects
  public static boolean delete(ThingData thingData) {
    
    if (thingData == null) {
      return false;
    }
    
    if (thingData.getThingId() == 0) {
      return false;
    }
    
    deleteSub(thingData);
    
    ThingJdo ttj = new ThingJdo();
    ttj.setData(thingData);
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    boolean b = false;
    try {
      tx.begin();

      ThingJdo ttj2 = (ThingJdo) pm.getObjectById(ThingJdo.class, ttj.getThingId());
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
   * delete other data too
   * 
   * @param thingData
   */
  private static void deleteSub(ThingData thingData) {
    // stuff data
    ThingStuffJdo.delete(thingData);
  }
  
}
