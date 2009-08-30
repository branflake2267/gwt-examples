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
      //ThingJdo rr = this; // TODO make this instance = tt
      //rr = tt[0];
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
  
  /**
   * get identity
   * 
   * @return
   */
  public Long getThingId() {
    return this.thingId;
  }
  
  /**
   * get key (usernameKey, applicationKey,...)
   * @return
   */
  public String getKey() {
    return this.key;
  }
  
  /**
   * get secret
   * 
   * @return
   */
  public String getSecret() {
    return this.secret;
  }
  
  /**
   * query a thing by its id
   * 
   * @param thingId
   * @return
   */
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


  
}
