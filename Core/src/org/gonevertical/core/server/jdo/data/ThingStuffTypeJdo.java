package org.gonevertical.core.server.jdo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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

import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeDataFilter;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeDataFilter;
import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingStuffTypeJdo {

	private static final Logger log = Logger.getLogger(ThingStuffTypeJdo.class.getName());

	@NotPersistent
	private ServerPersistence sp;

	// identity
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key stuffTypeIdKey = null;

	// name of
	@Persistent
	private String name = null;

	// type of value, like int, double, string, ...
	@Persistent
	private long valueTypeId = ThingStuffTypeData.VALUETYPE_STRING;

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

	// when this object was updated
	@Persistent
	private Date dateUpdated;

	// who created this object
	@Persistent
	private long createdByThingId;

	// who updated this object
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
	public ThingStuffTypeJdo() throws Exception {
		//System.err.println("Don't use this constructor - Exiting");
		//throw new Exception();
	}
	
	/**
	 * constructor
	 */
	public ThingStuffTypeJdo(ServerPersistence sp) {
		this.sp = sp;
	}
	
	public void set(ServerPersistence sp) {
		this.sp = sp;
	}

	public void setData(ThingStuffTypeData thingStuffTypeData) {
		if (thingStuffTypeData == null) {
			return;
		}
		setKey(thingStuffTypeData.getId());
		this.name = thingStuffTypeData.getName();
		this.valueTypeId = thingStuffTypeData.getValueTypeId();

		this.startOf = thingStuffTypeData.getStartOf();
		this.endOf = thingStuffTypeData.getEndOf();
		
		this.rank = thingStuffTypeData.getRank();
		this.ownerThingIds = thingStuffTypeData.getOwners();

		if (stuffTypeIdKey != null && stuffTypeIdKey.getId() > 0) {
			this.dateUpdated = new Date();
			this.updatedByThingId = sp.getThingId();
		} else {
			this.dateCreated = new Date();
			this.createdByThingId = sp.getThingId();
		}
	}

	public void setKey(long id) {
		if (id > 0) {
			stuffTypeIdKey = KeyFactory.createKey(ThingStuffTypeJdo.class.getSimpleName(), id);
		}
	}

	public long getId() {
		return stuffTypeIdKey.getId();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public long getValueTypeId() {
		return valueTypeId;
	}

	/**
	 * insert only if unique
	 */
	public void saveUnique() {
		this.dateCreated = new Date();

		// don't insert if name already exists
		if (getId() > 0) {
  		ThingStuffTypeData tt = query(getId());
  		if (tt != null) {
  			save(convert(this));
  			return;
  		}
		} else {
			// check to see if name exists already
			ThingStuffTypeDataFilter filter = new ThingStuffTypeDataFilter();
			filter.setName(getName());
			ThingStuffTypeJdo[] t = query(filter);
			if (t != null && t.length > 0) {
				System.out.println("ThingStuffTypeJdo.saveUnique(): skipped b/c name already exists. name=" + getName());
				return;
			}
			
			insert();
		}

		System.out.println("ThingStuffTypeJdo.saveUnique(): thingTypeId=" + getId());
	}
	
	public void insertUnique() {
		this.dateCreated = new Date();

		// don't insert if name already exists
		ThingStuffTypeJdo[] tt = query(name);
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

		System.out.println("saved: thingStuffTypeId:" + getId());
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

		System.out.println("ThingStuffTypeJdo.saveUnique(): thingStuffTypeId=" + getId());
	}

	/**
	 * can only insert unique names
	 * 
	 * @param name
	 */
	public void save(ThingStuffTypeData thingStuffTypeData) {
		setData(thingStuffTypeData);

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			if (stuffTypeIdKey != null && stuffTypeIdKey.getId() > 0) {
				ThingStuffTypeJdo update = pm.getObjectById(ThingStuffTypeJdo.class, stuffTypeIdKey);
				update.set(sp);
				update.setData(thingStuffTypeData);
			} else {
				stuffTypeIdKey = null;
				pm.makePersistent(this);
			}

			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		System.out.println("saved: Id:" + getId());
	}

	public ThingStuffTypeJdo[] query(String name) {

		ArrayList<ThingStuffTypeJdo> aT = new ArrayList<ThingStuffTypeJdo>();

		String qfilter = "name==\"" + name + "\"";

		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<ThingStuffTypeJdo> e = pm.getExtent(ThingStuffTypeJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();

			Collection<ThingStuffTypeJdo> c = (Collection<ThingStuffTypeJdo>) q.execute();
			Iterator<ThingStuffTypeJdo> iter = c.iterator();
			while (iter.hasNext()) {
				ThingStuffTypeJdo t = (ThingStuffTypeJdo) iter.next();
				aT.add(t);
			}

			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
		if (aT.size() == 0) {
			return null;
		}

		ThingStuffTypeJdo[] r = null;
		if (aT.size() > 0) {
			r = new ThingStuffTypeJdo[aT.size()];
			aT.toArray(r);
		}

		return r;
	}

	public ThingStuffTypeJdo[] query(ThingStuffTypeDataFilter filter) {

		String qfilter = filter.getFilter_Or();

		ArrayList<ThingStuffTypeJdo> aT = new ArrayList<ThingStuffTypeJdo>();

		PersistenceManager pm = sp.getPersistenceManager();
		try {
			// TODO build filter
			Extent<ThingStuffTypeJdo> e = pm.getExtent(ThingStuffTypeJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			//q.setRange(0, 10); // TODO - finish range
			q.execute();

			Collection<ThingStuffTypeJdo> c = (Collection<ThingStuffTypeJdo>) q.execute();
			Iterator<ThingStuffTypeJdo> iter = c.iterator();

			int i=0;
			while (iter.hasNext()) {

				ThingStuffTypeJdo t = (ThingStuffTypeJdo) iter.next();

				// TODO work around, this sucks - change it later to something better - ran out of time today.
				if (i >= filter.getRangeStart() && i <= filter.getRangeFinish()) {
					aT.add(t);
				}

				i++;
			}

			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
		if (aT.size() == 0) {
			return null;
		}

		ThingStuffTypeJdo[] r = null;
		if (aT.size() > 0) {
			r = new ThingStuffTypeJdo[aT.size()];
			aT.toArray(r);
		}
		return r;
	}
	
	public ThingStuffTypeData query(long thingStuffTypeId) {
		if (thingStuffTypeId == 0) {
			return null;
		}

		ThingStuffTypeJdo thingStuffTypeJdo = null;

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			thingStuffTypeJdo = pm.getObjectById(ThingStuffTypeJdo.class, thingStuffTypeId);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		ThingStuffTypeData t = convert(thingStuffTypeJdo);

		return t;
	}

	public boolean delete(ThingStuffTypeData thingStuffTypeData) {

		ThingStuffTypeJdo ttj = new ThingStuffTypeJdo(sp);
		ttj.setData(thingStuffTypeData);

		PersistenceManager pm = sp.getPersistenceManager();
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

	public long queryTotal() {

		/* future spec I think
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

			com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query("__Stat_Kind__");
			query.addFilter("kind_name", FilterOperator.EQUAL, TThingStuffAboutJdo.class);

	    Entity globalStat = datastore.prepare(query).asSingleEntity();
	    Long totalBytes = (Long) globalStat.getProperty("bytes");
	    Long totalEntities = (Long) globalStat.getProperty("count");
		 */

		// TODO - work around, have to wait for the api/gae to make it to hosted mode
		long total = 0;

		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<ThingStuffTypeJdo> e = pm.getExtent(ThingStuffTypeJdo.class, true);
			Query q = pm.newQuery(e);
			q.execute();

			Collection<ThingStuffTypeJdo> c = (Collection<ThingStuffTypeJdo>) q.execute();
			total = c.size();

			q.closeAll();
		} finally {
			pm.close();
		}

		return total;
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
			r[i].setData(
					thingStuffTypeJdo[i].stuffTypeIdKey.getId(), 
					thingStuffTypeJdo[i].getName(), 
					thingStuffTypeJdo[i].getValueTypeId(),
					thingStuffTypeJdo[i].getStartOf(),
					thingStuffTypeJdo[i].getEndOf(),
					thingStuffTypeJdo[i].getRank(),
					thingStuffTypeJdo[i].getCreatedBy(),
					thingStuffTypeJdo[i].getCreatedDt(),
					thingStuffTypeJdo[i].getUpdatedBy(),
					thingStuffTypeJdo[i].getUpdatedDt(),
					thingStuffTypeJdo[i].getOwners());
		}
		return r;
	}

	public static ThingStuffTypeData convert(ThingStuffTypeJdo thingStuffTypeJdo) {
		if (thingStuffTypeJdo == null) {
			return null;
		}
		ThingStuffTypeData r = new ThingStuffTypeData();
		r.setData(
				thingStuffTypeJdo.stuffTypeIdKey.getId(), 
				thingStuffTypeJdo.getName(), 
				thingStuffTypeJdo.getValueTypeId(),
				thingStuffTypeJdo.getStartOf(),
				thingStuffTypeJdo.getEndOf(),
				thingStuffTypeJdo.getRank(),
				thingStuffTypeJdo.getCreatedBy(),
				thingStuffTypeJdo.getCreatedDt(),
				thingStuffTypeJdo.getUpdatedBy(),
				thingStuffTypeJdo.getUpdatedDt(),
				thingStuffTypeJdo.getOwners());

		return r;
	}

	public Date getStartOf() {
		return startOf;
	}

	public Date getEndOf() {
		return endOf;
	}
	
	public void setRank(Double rank) {
		this.rank = rank;
	}
	
	public Double getRank() {
		return rank;
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
	
	public Date getCreatedDt() {
		return dateCreated;
	}
	
	public Date getUpdatedDt() {
		return dateUpdated;
	}

}
