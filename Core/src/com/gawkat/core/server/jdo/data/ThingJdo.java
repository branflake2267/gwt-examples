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
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thing.ThingFilterData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingJdo {

	@NotPersistent
	private ServerPersistence sp = null;
	
	// identity
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key thingIdKey;
  
  // what type of thing is this, user, group, object?
  @Persistent
  private long thingTypeId;
  
  // username is represented here
  @Persistent
  private String key;
  
  // password sha1 hash is represented here
  @Persistent
  private String secret;
  
  // when did this start in time
  @Persistent
  private Date startOf;
  
  // when did this end in time
  @Persistent
  private Date endOf;
  
  // when this object was created
  @Persistent
  private Date dateCreated;
  
  // when this object was updated last
  @Persistent
  private Date dateUpdated;
  
  // who created this object
  @Persistent
  private long createdByThingId;
  
  // who last updated this object
  @Persistent
  private long updatedByThingId;
  
  /**
   * constructor
   */
  public ThingJdo(ServerPersistence sp) {
  	this.sp = sp;
  }
  
  /**
   * set data
   * 
   * @param thingData
   */
  public void setData(ThingData thingData) {
  	if (thingData == null) {
  		return;
  	}
  	setThingId(thingData.getThingId());
  	
    thingTypeId = thingData.getThingTypeId();
    key = thingData.getKey();
    
    // TODO be able to update secret (password hash)
    //this.secret = thingData.getSecret(); // TODO?
    
    this.startOf = thingData.getStartOf();
    this.endOf = thingData.getEndOf();
    
    if (thingIdKey != null && thingIdKey.getId() > 0) {
      dateUpdated = new Date();
    } else {
      dateCreated = new Date();
    }
  }
  
	public void setData(ThingJdo thingData) {
		if (thingData == null) {
  		return;
  	}
		setThingId(thingData.getThingId());
		
    this.thingTypeId = thingData.getThingTypeId();
    this.key = thingData.getKey();
    
    //this.secret = thingData.getSecret(); // TODO? do I do someting here, can't remember
    
    this.startOf = thingData.getStartOf();
    this.endOf = thingData.getEndOf();
    
    if (thingIdKey != null && thingIdKey.getId() > 0) {
      dateUpdated = new Date();
    } else {
      dateCreated = new Date();
    }
  }
  
	public ThingData getThingData() {
    ThingData thingData = new ThingData();
    thingData.setData(thingTypeId, thingIdKey.getId(), key, startOf, endOf, dateCreated, dateUpdated);
    return thingData;
  }
  
  /**
   * insert thing, only if its unique
   * 
   * @param thingTypeId
   * @param key
   * @param secret
   */
  public void insertUnique(long thingTypeId, String key, String secret) {
    this.thingTypeId = thingTypeId;
    this.key = key;
    this.secret = secret;
    this.dateCreated = new Date();
    
    // do not insert duplicate
    ThingJdo[] tt = query(thingTypeId, key);
    if (tt != null && tt.length > 0) {
      return;
    }
    
    PersistenceManager pm = sp.getPersistenceManager();
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
  
  public void insert() {
    this.dateCreated = new Date();
    
    PersistenceManager pm = sp.getPersistenceManager();
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
  
  public void save(ThingData[] thingData) {
  	
  	for (int i=0; i < thingData.length; i++) {
  		save(thingData[i]);
  	}
  	
  }
  
  public long save(ThingData thingData) {
    setData(thingData);

    PersistenceManager pm = sp.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      if (thingIdKey != null && thingIdKey.getId() > 0) { // update
        ThingJdo update = pm.getObjectById(ThingJdo.class, thingIdKey);
        update.setData(thingData);
        this.thingIdKey = update.thingIdKey;
        
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

    return getThingId();
  }
  
  public boolean savePassword(ThingData thingData, String secretHash) {
    setData(thingData);

    boolean b = false;
    PersistenceManager pm = sp.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      if (thingIdKey.getId() > 0) { // update
        ThingJdo update = pm.getObjectById(ThingJdo.class, thingIdKey);
        update.setSecret(secretHash);
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
    
  public ThingData query(long thingId) {
  	if (thingId == 0) {
  		return null;
  	}
    
  	ThingJdo thingJdo = null;
    
    PersistenceManager pm = sp.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      thingJdo = pm.getObjectById(ThingJdo.class, thingId);
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    
    ThingData td = convert(thingJdo);
    
    return td;
  }
  
  /**
   * query thing by key(usernameKey,ApplicationKey,GroupKey,...)
   * 
   * @param key
   * @return
   */
  public ThingJdo[] query(long thingTypeId, String key) {
    
    ArrayList<ThingJdo> aT = new ArrayList<ThingJdo>();

    String qfilter = "thingTypeId==" + thingTypeId + " && key==\"" + key + "\" ";
    
    PersistenceManager pm = sp.getPersistenceManager();
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

  public ThingData[] query(ThingFilterData filter) {
    
    long thingTypeId = filter.thingTypeId;
    
    ArrayList<ThingJdo> aT = new ArrayList<ThingJdo>();

    String qfilter = null;
    if (filter.thingTypeId > 0) {
      qfilter = "thingTypeId==" + thingTypeId + "";
    }
    
    PersistenceManager pm = sp.getPersistenceManager();
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
  
  /**
   * convert to data object for rpc transport
   * 
   * @param thingJdo
   * @return
   */
  public static ThingData[] convert(ThingJdo[] thingJdo) {
  	
    ThingData[] r = new ThingData[thingJdo.length];
    
    for (int i=0; i < thingJdo.length; i++) {
      r[i] = new ThingData();

    	r[i].setData(
    			thingJdo[i].getThingTypeId(), 
    			thingJdo[i].getThingId(), 
    			thingJdo[i].getKey(), 
    			thingJdo[i].getStartOf(), 
    			thingJdo[i].getEndOf(), 
    			thingJdo[i].getDateCreated(), 
    			thingJdo[i].getDateUpdated());
    }
    return r;
  }

  public static ThingData convert(ThingJdo thingJdo) {
  	
    ThingData r = new ThingData();
  	r.setData(
  			thingJdo.getThingTypeId(), 
  			thingJdo.getThingId(), 
  			thingJdo.getKey(), 
  			thingJdo.getStartOf(), 
  			thingJdo.getEndOf(), 
  			thingJdo.getDateCreated(), 
  			thingJdo.getDateUpdated());
    
    return r;
  }

  /**
   * delete thing
   * 
   * 
   * TODO - delete child objects
   * 
   * 
   * @param sp
   * @param thingData
   * @return
   */
  public boolean delete(ThingData thingData) {
    
    if (thingData == null) {
      return false;
    }
    
    if (thingData.getThingId() == 0) {
      return false;
    }
    
    // delete child objects that the thing owns first
    deleteSub(thingData);
    
    ThingJdo ttj = new ThingJdo(sp);
    ttj.setData(thingData);
    
    PersistenceManager pm = sp.getPersistenceManager();
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
  private void deleteSub(ThingData thingData) {
  	
  	ThingStuffJdo tsj = new ThingStuffJdo(sp);
  	
    // stuff data
    tsj.delete(thingData);
    
    // TODO - delete other data that this thing owns
    
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }
  
  public long getThingId() {
  	long id = thingIdKey.getId();
    return id;
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
  
	public void setThingId(long thingId) {
		if (thingId > 0) {
			thingIdKey = KeyFactory.createKey(ThingJdo.class.getSimpleName(), thingId);
		} else {
			thingIdKey = null;
		}
  }
	
  public Date getEndOf() {
	  return endOf;
  }

	public Date getStartOf() {
	  return startOf;
  }
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public Date getDateUpdated() {
		return dateUpdated;
	}
	
	public long getCreatedBy() {
		return createdByThingId;
	}
	
	public long getUpdatedBy() {
		return updatedByThingId;
	}
  
}
