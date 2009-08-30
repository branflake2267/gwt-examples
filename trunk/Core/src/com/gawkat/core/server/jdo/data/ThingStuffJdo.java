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
import com.gawkat.core.client.account.thingstuff.ThingStuffFilterData;
import com.gawkat.core.server.jdo.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingStuffJdo {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private long thingStuffId;
  
  @Persistent
  private long thingStuffTypeId;
  
  // values that can be stored
  @Persistent
  private String value = null;
  
  @Persistent
  private boolean valueBol = false;
  
  @Persistent
  private double valueDouble = 0.0;
  
  @Persistent
  private long valueInt = 0;
  
  @Persistent
  private Date dateCreated;
  
  @Persistent
  private Date dateUpdated;
  
  /**
   * constructor
   */
  public ThingStuffJdo() {
  }
  
  public ThingStuffJdo(ThingStuffData thingStuffData) {
    this.thingStuffId = thingStuffData.getId();
    this.thingStuffTypeId = thingStuffData.getThingStuffTypeId();
    this.value = thingStuffData.getValue();
    this.valueBol = thingStuffData.getValueBol();
    this.valueDouble = thingStuffData.getValueDouble();
    this.valueInt = thingStuffData.getValueInt();
    
    if (thingStuffId > 0) {
      this.dateUpdated = new Date();
    } else {
      this.dateCreated = new Date();
    }
  }
  
  public long getStuffId() {
    return thingStuffId;
  }
  
  public long getStuffTypeId() {
    return thingStuffTypeId;
  }
  
  public long getId() {
    return thingStuffId;
  }
  
  public long getThingStuffTypeId() {
    return thingStuffTypeId;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public void setValue(boolean value) {
    this.valueBol = value;
  }
  
  public void setValue(double value) {
    this.valueDouble = value;
  }
  
  public String getValue() {
    return value;
  }
  
  public boolean getValueBol() {
    return valueBol;
  }
  
  public double getValueDouble() {
    return valueDouble;
  }
  
  public void save() {
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
   * insert thing
   * 
   * @param thingTypeId
   * @param key
   * @param secret
   */
  @Deprecated
  public void save(long thingStuffId, long thingStuffTypeId, String value, boolean valueBol, double valueDouble) {
    this.thingStuffId = thingStuffId;
    this.thingStuffTypeId = thingStuffTypeId;
    this.value = value;
    this.valueBol = valueBol;
    this.valueDouble = valueDouble;
    this.dateCreated = new Date();
        
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      
      if (thingStuffId > 0) { // update
        ThingStuffJdo thingStuff = pm.getObjectById(ThingStuffJdo.class, thingStuffId);
        thingStuff = this;
        pm.makePersistent(thingStuff);
      } else { // insert
        pm.makePersistent(this);
      }
     
      tx.commit();
    } finally {
      if (tx.isActive()) {
          tx.rollback();
      }
      pm.close();
    }
  }
  
  /**
   * query a thing by its id
   * 
   * @param thingStuffId
   * @return
   */
  public static ThingStuffJdo query(long thingStuffId) {
    ThingStuffJdo thingStuff = null;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      thingStuff = pm.getObjectById(ThingStuffJdo.class, thingStuffId);
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    return thingStuff;
  }
  
  public static ThingStuffData[] query(ThingStuffFilterData filter) {
    
    long thingStuffId = filter.thingStuffId;
    
    ArrayList<ThingStuffJdo> aT = new ArrayList<ThingStuffJdo>();

    String qfilter = null;
    if (filter.thingStuffId > 0) {
      qfilter = "thingStuffId==" + thingStuffId + "";
    }
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<ThingStuffJdo> e = pm.getExtent(ThingStuffJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();
      
      Collection<ThingStuffJdo> c = (Collection<ThingStuffJdo>) q.execute();
      Iterator<ThingStuffJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingStuffJdo t = (ThingStuffJdo) iter.next();
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

    ThingStuffJdo[] tj = new ThingStuffJdo[aT.size()];
    if (aT.size() > 0) {
      tj = new ThingStuffJdo[aT.size()];
      aT.toArray(tj);
    }
    
    ThingStuffData[] td = convert(tj);
    
    return td;
  }
  
  public static ThingStuffData[] convert(ThingStuffJdo[] thingJdo) {
    ThingStuffData[] r = new ThingStuffData[thingJdo.length];
    for (int i=0; i < thingJdo.length; i++) {
      r[i] = new ThingStuffData(
          thingJdo[i].thingStuffId, 
          thingJdo[i].thingStuffTypeId, 
          thingJdo[i].value, 
          thingJdo[i].valueBol, 
          thingJdo[i].valueDouble,
          thingJdo[i].valueInt);
    }
    return r;
  }

}
