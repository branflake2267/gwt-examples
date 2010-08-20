package org.gonevertical.core.server.jdo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeDataFilter;
import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingTypeJdo {

	@NotPersistent
	private static final Logger log = Logger.getLogger(ThingTypeJdo.class.getName());
	
	@NotPersistent
	private ServerPersistence sp = null;

	// default required thing types - note these are in a couple other classes, probably should consolidate these
	public static final int TYPE_APPLICATION = 1;
	public static final int TYPE_USER = 2;
	public static final int TYPE_GROUP = 3;

	// identity
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key thingTypeIdKey;

	// type name
	@Persistent
	private String name;

	// when did this start in time
	@Persistent
	private Date startOf = null;

	// when did this end in time
	@Persistent
	private Date endOf = null;
	
	// order the list by this
	@Persistent
	private Double rank;

	// when this object was created
	@Persistent
	private Date dateCreated;

	// when this object was last updated
	@Persistent
	private Date dateUpdated;

	// who created this object
	@Persistent
	private long createdByThingId;

	// who last updated this object
	@Persistent
	private long updatedByThingId;

	// assign ownership of this thing to this thing
	@Persistent
	private long[] ownerThingIds;
	
	/**
	 * constructor 
	 * 
	 * @throws Exception
	 */
  public ThingTypeJdo() throws Exception {
  	//System.err.println("Don't use this constructor - Exiting");
  	//throw new Exception();
  }
  	
	/**
	 * constructor
	 */
	public ThingTypeJdo(ServerPersistence sp) {
		this.sp = sp;
	}
	
	public void set(ServerPersistence sp) {
		this.sp = sp;
	}

	/**
	 * set data
	 * 
	 * @param thingTypeData
	 */
	public void setData(ThingTypeData thingTypeData) {

		if (thingTypeData == null) {
			return;
		}

		setKey(thingTypeData.getId());

		this.name = thingTypeData.getName();

		this.startOf = thingTypeData.getStartOf();
		this.endOf = thingTypeData.getEndOf();
		
		this.rank = thingTypeData.getRank();
		this.ownerThingIds = thingTypeData.getOwners();

		if (thingTypeIdKey != null && thingTypeIdKey.getId() > 0) {
			this.dateUpdated = new Date();
			this.updatedByThingId = sp.getUserThingId();
		} else {
			this.dateCreated = new Date();
			this.createdByThingId = sp.getUserThingId();
		}
	}

	private void setKey(long id) {
		if (id > 0) {
			thingTypeIdKey = KeyFactory.createKey(ThingTypeJdo.class.getSimpleName(), id);
		}
	}

	/**
	 * can only insert unique names
	 * 
	 * @param name
	 */
	public void saveUnique() {
		this.dateCreated = new Date();

		// don't insert if name already exists
		if (getId() != null && getId() > 0) {
			
  		ThingTypeData tt = query(getId());
  		if (tt != null) {
  			save(convert(this));
  			return;
  		}
  		
		} else {
			
			// check to see if name exists already
			ThingTypeDataFilter filter = new ThingTypeDataFilter();
			filter.setName(getName());
			ThingTypeJdo[] t = query(filter);
			if (t != null && t.length > 0) {
				return;
			}
			
			insert();
		}

		System.out.println("saved: thingTypeId:" + getId());
	}
	
	/**
	 * can only insert unique names
	 * 
	 * @param name
	 */
	public void insertUnique() {
		this.dateCreated = new Date();

		// don't insert if name already exists
		ThingTypeJdo[] tt = query(name);
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

		System.out.println("saved: thingTypeId:" + getId());
	}	
	private long save(ThingTypeData thingTypeData) {
		setData(thingTypeData);

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			if (thingTypeData != null && thingTypeData.getId() > 0) { // update
				ThingTypeJdo update = pm.getObjectById(ThingTypeJdo.class, thingTypeData.getId());
				update.set(sp);
				update.setData(thingTypeData);
				
				this.thingTypeIdKey = update.thingTypeIdKey;
				
				//System.out.println(" " + update.getUpdatedBy());
				
			} else { // insert
				thingTypeIdKey = null;
				pm.makePersistent(this);
			}

			tx.commit();
			
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "save(): ", e);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return getId();
	}

	/**
	 * insert new 
	 */
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

		System.out.println("saved: thingTypeId:" + getId());
	}
	
	public ThingTypeData query(long thingTypeId) {
		if (thingTypeId == 0) {
			return null;
		}

		ThingTypeJdo thingTypeJdo = null;

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			thingTypeJdo = pm.getObjectById(ThingTypeJdo.class, thingTypeId);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		ThingTypeData t = convert(thingTypeJdo);

		return t;
	}

	/**
	 * query thingType
	 * 
	 * @param name
	 * @return
	 */
	public ThingTypeJdo[] query(String name) {
		String qfilter = "name==\"" + name + "\"";

		ThingTypeJdo[] r = null;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<ThingTypeJdo> e = pm.getExtent(ThingTypeJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();
			Collection<ThingTypeJdo> c = (Collection<ThingTypeJdo>) q.execute();
			if (c.size() > 0) {
				r = new ThingTypeJdo[c.size()];
				c.toArray(r);
			}
			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return r;
	}

	/**
	 * query thingType by filter
	 * 
	 * @param filter
	 * @return
	 */
	public ThingTypeJdo[] query(ThingTypeDataFilter filter) {

		// setup filters
		String qfilter = null;
		if (filter !=null) {
			qfilter = filter.getFilter_Or();
		}

		ThingTypeJdo[] r = null;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<ThingTypeJdo> e = pm.getExtent(ThingTypeJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			
			//TODO q.setRange(0, 10); // TODO - finish range
			
			q.execute();
			Collection<ThingTypeJdo> c = (Collection<ThingTypeJdo>) q.execute();
			if (c.size() > 0) {
				r = new ThingTypeJdo[c.size()];
				c.toArray(r);
			}
			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return r;
	}

	public boolean deleteThingTypeDataJdo(ThingTypeData thingTypeData) {
		ThingTypeJdo ttj = new ThingTypeJdo(sp);
		ttj.setData(thingTypeData);
		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean b = false;
		try {
			tx.begin();
			ThingTypeJdo ttj2 = (ThingTypeJdo) pm.getObjectById(ThingTypeJdo.class, ttj.getId());
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

	@SuppressWarnings("unchecked")
  public long queryTotal() {
		long total = 0;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Query q = pm.newQuery("select thingTypeIdKey from " + ThingTypeJdo.class.getName());
	    List<Key> ids = (List<Key>) q.execute();
			total = ids.size();
			q.closeAll();
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
			pm.close();
		}
		return total;
	}
	
	/**
	 * convert from jdo to data object type for rpc transit
	 * 
	 * @param thingTypeJdo
	 * @return
	 */
	public static ThingTypeData[] convert(ThingTypeJdo[] thingTypeJdo) {
		if (thingTypeJdo == null) {
			return null;
		}
		ThingTypeData[] r = new ThingTypeData[thingTypeJdo.length];
		for (int i=0; i < thingTypeJdo.length; i++) {
			r[i] = new ThingTypeData();
			r[i].setData(
					thingTypeJdo[i].getId(), 
					thingTypeJdo[i].getName(),
					thingTypeJdo[i].getStartOf(),
					thingTypeJdo[i].getEndOf(),
					thingTypeJdo[i].getRank(),
					thingTypeJdo[i].getCreatedBy(),
					thingTypeJdo[i].getDateCreated(),
					thingTypeJdo[i].getUpdatedBy(),
					thingTypeJdo[i].getDateUpdated(),
					thingTypeJdo[i].getOwners());
		}
		return r;
	}
	
	public static ThingTypeData convert(ThingTypeJdo thingTypeJdo) {
		if (thingTypeJdo == null) {
			return null;
		}
		ThingTypeData r = new ThingTypeData();
		r.setData(
				thingTypeJdo.getId(), 
				thingTypeJdo.getName(),
				thingTypeJdo.getStartOf(),
				thingTypeJdo.getEndOf(),
				thingTypeJdo.getRank(),
				thingTypeJdo.getCreatedBy(),
				thingTypeJdo.getDateCreated(),
				thingTypeJdo.getUpdatedBy(),
				thingTypeJdo.getDateUpdated(),
				thingTypeJdo.getOwners());
		return r;
	}

	public Long getId() {
		if (thingTypeIdKey == null) {
			return null;
		}
		return thingTypeIdKey.getId();
	}

	public String getName() {
		return this.name;
	}

	public Date getEndOf() {
		return endOf;
	}

	public Date getStartOf() {
		return startOf;
	}

	public void setRank(Double rank) {
		this.rank = rank;
	}
	
	public Double getRank() {
		return rank;
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
	
	public void setOwners(long[] ownerThingIds) {
		this.ownerThingIds = ownerThingIds;
	}
	
	public long[] getOwners() {
		return ownerThingIds;
	}
	
	
}
