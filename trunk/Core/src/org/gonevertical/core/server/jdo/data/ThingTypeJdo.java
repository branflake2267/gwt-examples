package org.gonevertical.core.server.jdo.data;

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

import org.gonevertical.core.client.account.thingtype.ThingTypeData;
import org.gonevertical.core.client.account.thingtype.ThingTypeFilterData;
import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingTypeJdo {

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

	/**
	 * constructor
	 */
	public ThingTypeJdo(ServerPersistence sp) {
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

		if (thingTypeIdKey != null && thingTypeIdKey.getId() > 0) {
			dateUpdated = new Date();
		} else {
			dateCreated = new Date();
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

	/**
	 * query thingType
	 * 
	 * @param name
	 * @return
	 */
	public ThingTypeJdo[] query(String name) {

		ArrayList<ThingTypeJdo> aT = new ArrayList<ThingTypeJdo>();

		String qfilter = "name==\"" + name + "\"";

		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<ThingTypeJdo> e = pm.getExtent(ThingTypeJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();

			Collection<ThingTypeJdo> c = (Collection<ThingTypeJdo>) q.execute();
			Iterator<ThingTypeJdo> iter = c.iterator();
			while (iter.hasNext()) {
				ThingTypeJdo t = (ThingTypeJdo) iter.next();
				aT.add(t);
			}

			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}

		ThingTypeJdo[] r = null;
		if (aT.size() > 0) {
			r = new ThingTypeJdo[aT.size()];
			aT.toArray(r);
		}

		return r;
	}

	/**
	 * query thingType by filter
	 * 
	 * @param filter
	 * @return
	 */
	public ThingTypeJdo[] query(ThingTypeFilterData filter) {

		// TODO configure drill to setup filters

		ArrayList<ThingTypeJdo> aT = new ArrayList<ThingTypeJdo>();

		PersistenceManager pm = sp.getPersistenceManager();
		try {
			
			// TODO build filter
			Extent<ThingTypeJdo> e = pm.getExtent(ThingTypeJdo.class, true);
			Query q = pm.newQuery(e);
			//q.setRange(0, 10); // TODO - finish range
			q.execute();

			Collection<ThingTypeJdo> c = (Collection<ThingTypeJdo>) q.execute();
			Iterator<ThingTypeJdo> iter = c.iterator();
			while (iter.hasNext()) {
				ThingTypeJdo t = (ThingTypeJdo) iter.next();
				aT.add(t);
			}

			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}

		ThingTypeJdo[] r = null;
		if (aT.size() > 0) {
			r = new ThingTypeJdo[aT.size()];
			aT.toArray(r);
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
			Extent<ThingTypeJdo> e = pm.getExtent(ThingTypeJdo.class, true);
			Query q = pm.newQuery(e);
			q.execute();

			Collection<ThingTypeJdo> c = (Collection<ThingTypeJdo>) q.execute();
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
					thingTypeJdo[i].getDateCreated(),
					thingTypeJdo[i].getDateUpdated());
		}
		return r;
	}

	public Long getId() {
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
