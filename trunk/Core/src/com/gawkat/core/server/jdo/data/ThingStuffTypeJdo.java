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

import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeFilterData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingStuffTypeJdo {

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
	private int valueTypeId = ThingStuffTypeData.VT_STRING;

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
	private long createdByThingId;

	// who updated this object
	@Persistent
	private long updatedByThingId;

	/**
	 * constructor
	 */
	public ThingStuffTypeJdo(ServerPersistence sp) {
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

		if (stuffTypeIdKey != null && stuffTypeIdKey.getId() > 0) {
			dateUpdated = new Date();

		} else {
			dateCreated = new Date();
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

	public int getValueTypeId() {
		return valueTypeId;
	}

	/**
	 * insert only if unique
	 */
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

		 System.out.println("saved: thingStuffTypeId:" + getId());
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
		 Transaction tx = pm.currentTransaction();
		 try {
			 tx.begin();

			 Extent<ThingStuffTypeJdo> e = pm.getExtent(ThingStuffTypeJdo.class, true);
			 Query q = pm.newQuery(e, qfilter);
			 q.execute();

			 Collection<ThingStuffTypeJdo> c = (Collection<ThingStuffTypeJdo>) q.execute();
			 Iterator<ThingStuffTypeJdo> iter = c.iterator();
			 while (iter.hasNext()) {
				 ThingStuffTypeJdo t = (ThingStuffTypeJdo) iter.next();
				 aT.add(t);
			 }

			 tx.commit();
			 q.closeAll();
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 if (tx.isActive()) {
				 tx.rollback();
			 }
			 pm.close();
		 }


		 ThingStuffTypeJdo[] r = null;
		 if (aT.size() > 0) {
			 r = new ThingStuffTypeJdo[aT.size()];
			 aT.toArray(r);
		 }

		 return r;
	 }

	 public ThingStuffTypeJdo[] query(ThingStuffTypeFilterData filter) {

		 // TODO configure drill to setup filters

		 ArrayList<ThingStuffTypeJdo> aT = new ArrayList<ThingStuffTypeJdo>();

		 PersistenceManager pm = sp.getPersistenceManager();
		 Transaction tx = pm.currentTransaction();
		 try {
			 tx.begin();

			 // TODO build filter
			 Extent<ThingStuffTypeJdo> e = pm.getExtent(ThingStuffTypeJdo.class, true);
			 Query q = pm.newQuery(e);
			 //q.setRange(0, 10); // TODO - finish range
			 q.execute();

			 Collection<ThingStuffTypeJdo> c = (Collection<ThingStuffTypeJdo>) q.execute();
			 Iterator<ThingStuffTypeJdo> iter = c.iterator();
			 while (iter.hasNext()) {
				 ThingStuffTypeJdo t = (ThingStuffTypeJdo) iter.next();
				 aT.add(t);
			 }

			 tx.commit();
			 q.closeAll();
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 if (tx.isActive()) {
				 tx.rollback();
			 }
			 pm.close();
		 }

		 ThingStuffTypeJdo[] r = null;
		 if (aT.size() > 0) {
			 r = new ThingStuffTypeJdo[aT.size()];
			 aT.toArray(r);
		 }
		 return r;
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
					 thingStuffTypeJdo[i].name, 
					 thingStuffTypeJdo[i].valueTypeId,
					 thingStuffTypeJdo[i].startOf,
					 thingStuffTypeJdo[i].endOf,
					 thingStuffTypeJdo[i].dateCreated,
					 thingStuffTypeJdo[i].dateUpdated);
		 }
		 return r;
	 }


	 public Date getStartOf() {
		 return startOf;
	 }

	 public Date getEndOf() {
		 return endOf;
	 }

	 public long getCreatedBy() {
		 return createdByThingId;
	 }

	 public long getUpdatedBy() {
		 return updatedByThingId;
	 }

}
