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
public class ThingStuffAboutJdo {

	@NotPersistent
	private ServerPersistence sp = null;
	
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key thingStuffAboutIdKey;
  
  // Owner of this object - TODO add this to set data
  private long thingStuffJdoId;
  
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
    
  /**
   * constructor
   */
  public ThingStuffAboutJdo(ServerPersistence sp) {
  	this.sp = sp;
  }
  
  public void setData(long thingStuffJdoId, ThingStuffData thingStuffData) {
  	if (thingStuffData == null) {
  		return;
  	}
  	
  	setKey(thingStuffData.getId());
  	
  	this.thingStuffJdoId = thingStuffJdoId;
  	
    this.thingStuffTypeId = thingStuffData.getThingStuffTypeId();
    this.thingId = thingStuffData.getThingId();
    this.value = thingStuffData.getValue();
    this.valueBol = thingStuffData.getValueBol();
    this.valueDouble = thingStuffData.getValueDouble();
    this.valueInt = thingStuffData.getValueInt();
    
    this.startOf = thingStuffData.getStartOf();
    this.endOf = thingStuffData.getEndOf();
        
    if (thingStuffAboutIdKey != null && thingStuffAboutIdKey.getId() > 0) {
      this.dateUpdated = new Date();
    } else {
      this.dateCreated = new Date();
    }
  }

	public void setData(long thingStuffJdoId, ThingStuffAboutJdo thingStuffData) {
		if (thingStuffData == null) {
			return;
		}
		
		setKey(thingStuffData.getId());
		
		this.thingStuffJdoId = thingStuffJdoId;
		
    this.thingStuffTypeId = thingStuffData.getThingStuffTypeId();
    this.thingId = thingStuffData.getThingId();
    this.value = thingStuffData.getValue();
    this.valueBol = thingStuffData.getValueBol();
    this.valueDouble = thingStuffData.getValueDouble();
    this.valueInt = thingStuffData.getValueInt();

    this.startOf = thingStuffData.getStartOf();
    this.endOf = thingStuffData.getEndOf();
    
    if (thingStuffAboutIdKey != null && thingStuffAboutIdKey.getId() > 0) {
      this.dateUpdated = new Date();
    } else {
      this.dateCreated = new Date();
    }
  }
  
	private void setKey(long id) {
		
	  if (id > 0) {
	  	thingStuffAboutIdKey = getKey(id);
	  }
	 
  }

  public void save(long thingStuffJdoId, ThingStuffData thingStuffData) {
  	setData(thingStuffJdoId, thingStuffData);
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      
      if (thingStuffAboutIdKey != null && thingStuffAboutIdKey.getId() > 0) { // update
        ThingStuffAboutJdo update = pm.getObjectById(ThingStuffAboutJdo.class, thingStuffAboutIdKey);
        update.setData(thingStuffJdoId, thingStuffData);
        
      } else { // insert    
        pm.makePersistent(this);
      }
      
      tx.commit();
      
      // Debug - TODO - get it to save with List object
      System.out.println("ThingJdo: id: " + getId());
      
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
  public ThingStuffAboutJdo query(long thingStuffId) {
    ThingStuffAboutJdo thingStuff = null;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      thingStuff = pm.getObjectById(ThingStuffAboutJdo.class, thingStuffId);
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
    long thingStuffJdoId = filter.thingStuffJdoId;
    
    ArrayList<ThingStuffAboutJdo> aT = new ArrayList<ThingStuffAboutJdo>();

    // TODO filter by thing too
    String qfilter = null;
    if (filter.thingStuffJdoId > 0) {
      qfilter = "thingStuffJdoId==" + thingStuffJdoId + "";
    }
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<ThingStuffAboutJdo> e = pm.getExtent(ThingStuffAboutJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();
      
      Collection<ThingStuffAboutJdo> c = (Collection<ThingStuffAboutJdo>) q.execute();
      Iterator<ThingStuffAboutJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingStuffAboutJdo t = (ThingStuffAboutJdo) iter.next();
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

    ThingStuffAboutJdo[] tj = new ThingStuffAboutJdo[aT.size()];
    if (aT.size() > 0) {
      tj = new ThingStuffAboutJdo[aT.size()];
      aT.toArray(tj);
    }
    
    // TODO overkill here - can get the list up above
    List<ThingStuffAboutJdo> tjsa_list = Arrays.asList(tj);
    
    ThingStuffData[] td = convert(tjsa_list);
    
    return td;
  }
  
  public static ThingStuffData[] convert(List<ThingStuffAboutJdo> thingStuffJdoAbout) {
  	
    Iterator<ThingStuffAboutJdo> itr = thingStuffJdoAbout.iterator();

    ThingStuffData[] r = new ThingStuffData[thingStuffJdoAbout.size()];
    
    int i = 0;
    while(itr.hasNext()) {
    	
    	ThingStuffAboutJdo tsja = itr.next();
    	
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
          tsja.getDateUpdated());
    	
      i++;
    }
    
    return r;
  }
  
  public List<ThingStuffAboutJdo> convertStuffsAboutToJdo(ThingStuffsData thingStuffsData) {
  	
  	if (thingStuffsData == null) {
  		return null;
  	}
  	
  	ThingStuffData[] tsd = thingStuffsData.getThingStuffData();
  	
  	ThingStuffAboutJdo[] r = new ThingStuffAboutJdo[tsd.length];
  	
  	for (int i=0; i < tsd.length; i++) {
  		r[i] = new ThingStuffAboutJdo(sp);
  		r[i].thingId = tsd[i].getThingId();
  		r[i].thingStuffAboutIdKey = getKey(tsd[i].getId());
  		r[i].thingStuffTypeId = tsd[i].getThingStuffTypeId();
  		r[i].value = tsd[i].getValue();
  		r[i].valueBol = tsd[i].getValueBol();
  		r[i].valueDouble = tsd[i].getValueDouble();
  		r[i].valueInt = tsd[i].getValueInt();
  		r[i].startOf = tsd[i].getStartOf();
  		r[i].endOf = tsd[i].getEndOf();
  		
  	}
  	
  	List<ThingStuffAboutJdo> l = Arrays.asList(r);
  	
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
  
	// TODO - verify this gets deleted - b/c of the thingStuffJdoId
  public boolean delete(ServerPersistence sp, ThingStuffData thingStuffData) {
    
    ThingStuffAboutJdo ttj = new ThingStuffAboutJdo(sp);
    ttj.setData(thingStuffJdoId, thingStuffData);
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    boolean b = false;
    try {
      tx.begin();

      ThingStuffAboutJdo ttj2 = (ThingStuffAboutJdo) pm.getObjectById(ThingStuffAboutJdo.class, ttj.getId());
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

      Extent<ThingStuffAboutJdo> e = pm.getExtent(ThingStuffAboutJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();
      
      Collection<ThingStuffAboutJdo> c = (Collection<ThingStuffAboutJdo>) q.execute();

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
  	if (thingStuffAboutIdKey == null) {
  		return -1;
  	}
    return thingStuffAboutIdKey.getId();
  }

  /**
   * get id (same as getId()
   * @return
   */
  public long getStuffId() {
    return thingStuffAboutIdKey.getId();
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
  
  private Key getKey(long id) {
	  Key key = null;
  	if (id == 0) {
  		key = KeyFactory.createKey(ThingStuffAboutJdo.class.getSimpleName(), id);
	  }
	  return key;
  }

}
