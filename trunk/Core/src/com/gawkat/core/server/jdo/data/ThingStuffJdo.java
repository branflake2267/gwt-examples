package com.gawkat.core.server.jdo.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstuff.ThingStuffFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffsData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingStuffJdo {

	@NotPersistent
	private ServerPersistence sp = null;
	
	// identity
  //@PrimaryKey
  //@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  //private Long thingStuffId;
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key thingStuffIdKey;
  
  // why kind of stuff, defined as type
  @Persistent
  private long thingStuffTypeId;
  
  // who is the owner
  @Persistent
  private long thingId;
   
  // values that can be stored
  @Persistent
  private String value = null;
  
  @Persistent
  private boolean valueBol = false;
  
  @Persistent
  private double valueDouble = 0.0;
  
  @Persistent
  private long valueInt = 0;
  
  // when did this start in time
  @Persistent
  private Date startOf = null;
  
  // when did this end in time
  @Persistent
  private Date endOf = null;
  
  // when this object was created
  @Persistent
  private Date dateCreated;
  
  // when this object was updated
  @Persistent
  private Date dateUpdated;
  
  // who created this object
  @Persistent
  private long createdByThingId = 0;
  
  // who updated this object
  @Persistent
  private long updatedByThingId = 0;
  
  // define the about - adds another demension to the data
  @Persistent
  private List<ThingStuffJdo> thingStuffJdo_About = null;
  
  /**
   * constructor
   */
  public ThingStuffJdo(ServerPersistence sp) {
  	this.sp = sp;
  }
  
  public void setData(ThingStuffData thingStuffData) {
  	if (thingStuffData == null) {
  		return;
  	}
  	setKey(thingStuffData.getId());
    this.thingStuffTypeId = thingStuffData.getThingStuffTypeId();
    this.thingId = thingStuffData.getThingId();
    this.value = thingStuffData.getValue();
    this.valueBol = thingStuffData.getValueBol();
    this.valueDouble = thingStuffData.getValueDouble();
    this.valueInt = thingStuffData.getValueInt();
    this.thingStuffJdo_About = convertStuffsAboutToJdo(thingStuffData.getThingStuffsAbout());
    
    this.startOf = thingStuffData.getStartOf();
    this.endOf = thingStuffData.getEndOf();
        
    if (thingStuffIdKey != null && thingStuffIdKey.getId() > 0) {
      this.dateUpdated = new Date();
    } else {
      this.dateCreated = new Date();
    }
  }

	public void setData(ThingStuffJdo thingStuffData) {
		if (thingStuffData == null) {
			return;
		}
		setKey(thingStuffData.getId());
    this.thingStuffTypeId = thingStuffData.getThingStuffTypeId();
    this.thingId = thingStuffData.getThingId();
    this.value = thingStuffData.getValue();
    this.valueBol = thingStuffData.getValueBol();
    this.valueDouble = thingStuffData.getValueDouble();
    this.valueInt = thingStuffData.getValueInt();
    this.thingStuffJdo_About = convertStuffsAboutToJdo(thingStuffData.getThingStuffsAbout());
    
    this.startOf = thingStuffData.getStartOf();
    this.endOf = thingStuffData.getEndOf();
    
    if (thingStuffIdKey != null && thingStuffIdKey.getId() > 0) {
      this.dateUpdated = new Date();
    } else {
      this.dateCreated = new Date();
    }
  }

	/**
	 * get thingstuffjdo about from this object
	 * 
	 * @return
	 */
	public ThingStuffsData getThingStuffsAbout() {
		
		// change into the data format
		ThingStuffData[] tsd = convert(thingStuffJdo_About);
		
		// setup the object array
		ThingStuffsData tsds = new ThingStuffsData();
		tsds.thingStuffData = tsd;
		
	  return tsds;
  }
  
	private void setKey(long id) {
		
	  if (id > 0) {
	  	thingStuffIdKey = KeyFactory.createKey(ThingStuffJdo.class.getSimpleName(), id);
	  }
	 
  }

  public void save(ThingStuffData thingStuffData) {
    setData(thingStuffData);
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      
      if (thingStuffIdKey != null && thingStuffIdKey.getId() > 0) { // update
        ThingStuffJdo update = pm.getObjectById(ThingStuffJdo.class, thingStuffIdKey);
        update.setData(thingStuffData);
        
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
  public ThingStuffJdo query(long thingStuffId) {
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
  
  public ThingStuffData[] query(ThingStuffFilterData filter) {
    
    long thingId = filter.thingId;
    
    ArrayList<ThingStuffJdo> aT = new ArrayList<ThingStuffJdo>();

    String qfilter = null;
    if (filter.thingId > 0) {
      qfilter = "thingId==" + thingId + "";
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
    
    // TODO overkill here - can get the list up above
    List<ThingStuffJdo> tjsa_list = Arrays.asList(tj);
    
    ThingStuffData[] td = convert(tjsa_list);
    
    return td;
  }
  
  public static ThingStuffData[] convert(List<ThingStuffJdo> thingStuffJdoAbout) {
  	
    Iterator<ThingStuffJdo> itr = thingStuffJdoAbout.iterator();

    ThingStuffData[] r = new ThingStuffData[thingStuffJdoAbout.size()];
    
    int i = 0;
    while(itr.hasNext()) {
    	
    	ThingStuffJdo tsja = itr.next();
    	
    	r[i] = new ThingStuffData();
      r[i].setData(
          tsja.getThingId(),
          tsja.getStuffId(), 
          tsja.getStuffTypeId(), 
          tsja.getValue(), 
          tsja.getValueBol(), 
          tsja.getValueDouble(),
          tsja.getValueInt(), 
          tsja.getStartOf(),
          tsja.getEndOf(), 
          tsja.getDateCreated(),
          tsja.getDateUpdated(),
          tsja.getThingStuffsAbout());
    	
      i++;
    }
    
    return r;
  }
  
  public List<ThingStuffJdo> convertStuffsAboutToJdo(ThingStuffsData thingStuffsData) {
  	
  	if (thingStuffsData == null) {
  		return null;
  	}
  	
  	ThingStuffData[] tsd = thingStuffsData.getThingStuffData();
  	
  	ThingStuffJdo[] r = new ThingStuffJdo[tsd.length];
  	
  	for (int i=0; i < tsd.length; i++) {
  		r[i] = new ThingStuffJdo(sp);
  		r[i].thingId = tsd[i].getThingId();
  		r[i].thingStuffIdKey = KeyFactory.createKey(ThingStuffJdo.class.getSimpleName(), tsd[i].getId());
  		r[i].thingStuffTypeId = tsd[i].getThingStuffTypeId();
  		r[i].value = tsd[i].getValue();
  		r[i].valueBol = tsd[i].getValueBol();
  		r[i].valueDouble = tsd[i].getValueDouble();
  		r[i].valueInt = tsd[i].getValueInt();
  		r[i].startOf = tsd[i].getStartOf();
  		r[i].endOf = tsd[i].getEndOf();
  		r[i].thingStuffJdo_About = convertStuffsAboutToJdo(tsd[i].getThingStuffsAbout()); // recursive
  	}
  	
  	List<ThingStuffJdo> l = Arrays.asList(r);
  	
  	return l;
  }
  
  public boolean delete(ServerPersistence sp, long thingStuffId) {
    if (thingStuffId == 0) {
      return false;
    }
    
    ThingStuffData thingStuffData = new ThingStuffData();
    thingStuffData.setId(thingStuffId);
    
    boolean b = delete(sp, thingStuffData);
    
    return b;
  }
  
  public boolean delete(ServerPersistence sp, ThingStuffData thingStuffData) {
    
    ThingStuffJdo ttj = new ThingStuffJdo(sp);
    ttj.setData(thingStuffData);
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    boolean b = false;
    try {
      tx.begin();

      ThingStuffJdo ttj2 = (ThingStuffJdo) pm.getObjectById(ThingStuffJdo.class, ttj.getId());
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

  public boolean delete(ThingData thingData) {
    
    if (thingData == null) {
      return false;
    }
    
    long thingId = thingData.getThingId();
    
    if (thingId == 0) {
      return false;
    }
    
    String qfilter = "thingId==" + thingId + "";

    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<ThingStuffJdo> e = pm.getExtent(ThingStuffJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();
      
      Collection<ThingStuffJdo> c = (Collection<ThingStuffJdo>) q.execute();

      // delete all
      pm.deletePersistentAll(c);

      tx.commit();
      q.closeAll();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    
    return true;
  }

  public long getId() {
    return thingStuffIdKey.getId();
  }

  /**
   * get id (same as getId()
   * @return
   */
  public long getStuffId() {
    return thingStuffIdKey.getId();
  }
  
  public long getStuffTypeId() {
    return thingStuffTypeId;
  }
  
  public long getThingId() {
    return thingId;
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
 
  public long getValueInt() {
    return valueInt;
  }

	public Date getStartOf() {
	  return startOf;
  }
	
  public Date getEndOf() {
	  return endOf;
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
