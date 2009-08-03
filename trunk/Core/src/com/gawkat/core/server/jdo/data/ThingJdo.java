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
  public Long getId() {
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
    aT.toArray(r);
    return r;
  }





  
}
