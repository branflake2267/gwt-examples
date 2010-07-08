package org.gonevertical.core.server.jdo.data;

import java.util.ArrayList;
import java.util.Arrays;
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

import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffDataFilter;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingStuffAboutJdo {

	private static final Logger log = Logger.getLogger(ThingStuffAboutJdo.class.getName());
	
	@NotPersistent
	private ServerPersistence sp = null;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key thingStuffAboutIdKey;

	// who is the owner
	@Persistent
	private long thingId;

	// parent
	@Persistent
	private long thingStuffId;

	// why kind of stuff, defined as type
	@Persistent
	private long thingStuffTypeId;

	// values that can be stored
	@Persistent
	private String value;

	@Persistent
	private Boolean valueBol;

	@Persistent
	private Double valueDouble;

	@Persistent
	private Long valueLong;

	// TODO - could stick this in valueLong, but wondering how to deal with timezone
	@Persistent 
	private Date valueDate;

	// when did this start in time
	@Persistent
	private Date startOf;

	// when did this end in time
	@Persistent
	private Date endOf;

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
	public ThingStuffAboutJdo(ServerPersistence sp) {
		this.sp = sp;
	}

	public void setData(ThingStuffData thingStuffData) {
		if (thingStuffData == null) {
			return;
		}
		// don't do this here messes with the getObjectById key
		//setKey(thingStuffData.getStuffAboutId());

		// parent id
		this.thingStuffId = thingStuffData.getStuffId();

		this.thingStuffTypeId = thingStuffData.getThingStuffTypeId();
		this.thingId = thingStuffData.getThingId();

		this.value = thingStuffData.getValue();
		this.valueBol = thingStuffData.getValueBol();
		this.valueDouble = thingStuffData.getValueDouble();
		this.valueLong = thingStuffData.getValueLong();
		this.valueDate = thingStuffData.getValueDate(); 
			
		this.startOf = thingStuffData.getStartOf();
		this.endOf = thingStuffData.getEndOf();

		if (thingStuffData != null && thingStuffData.getStuffAboutId() > 0) {
			this.dateUpdated = new Date();
		} else {
			this.dateCreated = new Date();
		}
	}

	public void setData(ThingStuffAboutJdo thingStuffJdo) {
		if (thingStuffJdo == null) {
			return;
		}
		setKey(thingStuffJdo.getStuffId());

		// parent id
		this.thingStuffId = thingStuffJdo.getStuffId();

		this.thingStuffTypeId = thingStuffJdo.getThingStuffTypeId();
		this.thingId = thingStuffJdo.getThingId();

		this.value = thingStuffJdo.getValue();
		this.valueBol = thingStuffJdo.getValueBol();
		this.valueDouble = thingStuffJdo.getValueDouble();
		this.valueLong = thingStuffJdo.getValueLong();
		this.valueDate = thingStuffJdo.getValueDate();

		this.startOf = thingStuffJdo.getStartOf();
		this.endOf = thingStuffJdo.getEndOf();

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

	public long save(ThingStuffData thingStuffData) {
		setData(thingStuffData);

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			if (thingStuffData != null && thingStuffData.getStuffAboutId() > 0) { // update
				ThingStuffAboutJdo update = pm.getObjectById(ThingStuffAboutJdo.class, thingStuffData.getStuffAboutId());
				update.setData(thingStuffData);
				this.thingStuffAboutIdKey = update.thingStuffAboutIdKey;

			} else { // insert
				this.thingStuffAboutIdKey = null;
				pm.makePersistent(this);
			}

			tx.commit();

		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		// debug
		//System.out.println("ThingStuffAboutJdo.save(): thingStuffAboutId: " + getStuffAboutId() + " thingStuffId(Parent): " + thingStuffId + " thingStuffTypeId: " + thingStuffTypeId + " " +
		//"value: " + getString(value) + " valueBol: " + getString(valueBol) + " valueLong: " + getString(valueLong) + " valueDate: " + getString(valueDate));
		
		return getStuffAboutId();
	}
	
	public long saveUnique(ThingStuffData thingStuffData) {
		setData(thingStuffData);

		// setup filter so that I only create unique by identities [thingId, thingStuffTypeId)
		ThingStuffDataFilter filter = new ThingStuffDataFilter();
		filter.setThingId(thingStuffData.getThingId());
		filter.setThingStuffTypeId(thingStuffData.getThingStuffTypeId());
		filter.setThingStuffId(thingStuffData.getStuffId());
		
		if (thingStuffData.getValue() != null) {
			filter.setValueString(thingStuffData.getValue());
		}
		
		if (thingStuffData.getValueBol() != null) {
			filter.setValueBoolean(thingStuffData.getValueBol());
		}
		
		if (thingStuffData.getValueDouble() != null) {
			filter.setValueDouble(thingStuffData.getValueDouble());
		}
		
		if (thingStuffData.getValueLong() != null) {
			filter.setValueLong(thingStuffData.getValueLong());
		}

		ThingStuffData[] tsds = query(filter);
		if (tsds != null && tsds.length > 0) {
			ThingStuffData tsd = tsds[0];
			tsds[0].setValue(thingStuffData.getValue());
			tsds[0].setValue(thingStuffData.getValueBol());
			tsds[0].setValue(thingStuffData.getValueDouble());
			tsds[0].setValue(thingStuffData.getValueLong());
			tsds[0].setValue(thingStuffData.getValueDate());
		
			save(tsd);
			return thingStuffData.getStuffId();
		}

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			thingStuffAboutIdKey = null;
			pm.makePersistent(this);
			
			tx.commit();

		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "saveUnique()", e);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		// debug
		//System.out.println("ThingJdo: thingStuffId: " + getId() + " thingStuffTypeId: " + thingStuffTypeId + " " +
		//"value: " + getString(value) + " valueBol: " + getString(valueBol) + " valueLong: " + getString(valueLong) + " valueDate: " + getString(valueDate));

		return getStuffAboutId();
	}

	/**
	 * query a thing by its id
	 * 
	 * @param thingStuffId
	 * @return
	 */
	public ThingStuffAboutJdo query(long thingStuffId) {

		ThingStuffAboutJdo thingStuff = null;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			thingStuff = pm.getObjectById(ThingStuffAboutJdo.class, thingStuffId);
		} finally {
			pm.close();
		}

		return thingStuff;
	}

	/**
	 * get stuff about - by stuffId(parent)
	 * @param filter
	 * @return
	 */
	public ThingStuffData[] query(ThingStuffDataFilter filter) {

		ArrayList<ThingStuffAboutJdo> aT = new ArrayList<ThingStuffAboutJdo>();

		String qfilter = filter.getFilter_And();

		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<ThingStuffAboutJdo> e = pm.getExtent(ThingStuffAboutJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();

			Collection<ThingStuffAboutJdo> c = (Collection<ThingStuffAboutJdo>) q.execute();
			Iterator<ThingStuffAboutJdo> iter = c.iterator();
			while (iter.hasNext()) {
				ThingStuffAboutJdo t = (ThingStuffAboutJdo) iter.next();
				aT.add(t);
			}

			q.closeAll();
		} finally {
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
	
	/**
	 * query total
	 * 
	 *  TODO API not ready to do this effectively in hosted mode yet
	 * 
	 * @return
	 */
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
			Extent<ThingStuffAboutJdo> e = pm.getExtent(ThingStuffAboutJdo.class, true);
			Query q = pm.newQuery(e);
			q.execute();

			Collection<ThingStuffAboutJdo> c = (Collection<ThingStuffAboutJdo>) q.execute();
			total = c.size();
			
			q.closeAll();
		} finally {
			pm.close();
		}
		
		return total;
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
					tsja.getStuffAboutId(),
					tsja.getStuffTypeId(), 

					tsja.getValue(), 
					tsja.getValueBol(), 
					tsja.getValueDouble(),
					tsja.getValueLong(), 
					tsja.getValueDate(),
					
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
			r[i].thingStuffId = tsd[i].getStuffId();
			r[i].thingStuffAboutIdKey = getKey(tsd[i].getStuffId());
			r[i].thingStuffTypeId = tsd[i].getThingStuffTypeId();

			r[i].value = tsd[i].getValue();
			r[i].valueBol = tsd[i].getValueBol();
			r[i].valueDouble = tsd[i].getValueDouble();
			r[i].valueLong = tsd[i].getValueLong();

			r[i].startOf = tsd[i].getStartOf();
			r[i].endOf = tsd[i].getEndOf();

		}

		List<ThingStuffAboutJdo> l = Arrays.asList(r);

		return l;
	}

	/**
	 * delete thing about the about id
	 * 
	 * @param thingStuffAboutId
	 * @return
	 */
	public boolean delete(long thingStuffAboutId) {
		
		System.out.println("ThingStuffAboutJdo.delete(): deleting: " + thingStuffAboutId);

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean b = false;
		try {
			tx.begin();

			ThingStuffAboutJdo ttj2 = (ThingStuffAboutJdo) pm.getObjectById(ThingStuffAboutJdo.class, thingStuffAboutId);
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

		PersistenceManager pm = sp.getPersistenceManager();
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
	
	/**
	 * delete by parent id, thingStuffId
	 * 
	 * @param thingStuffId
	 * @return
	 */
	public boolean deleteByParent(long thingStuffId) {

		String qfilter = "thingStuffId==" + thingStuffId + "";

		PersistenceManager pm = sp.getPersistenceManager();
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

	public long getStuffAboutId() {
		if (thingStuffAboutIdKey == null) {
			return -1;
		}
		return thingStuffAboutIdKey.getId();
	}

	/**
	 * parent of this object (owner of this object)
	 * 
	 * @return
	 */
	public long getStuffId() {
		return thingStuffId;
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

	public void setValue(Boolean value) {
		this.valueBol = value;
	}

	public void setValue(Double value) {
		this.valueDouble = value;
	}

	public String getValue() {
		return value;
	}

	public Boolean getValueBol() {
		return valueBol;
	}

	public Double getValueDouble() {
		return valueDouble;
	}

	public Long getValueLong() {
		return valueLong;
	}
	
	private Date getValueDate() {
	  return valueDate;
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
	

	
	private String getString(Boolean value) {
		String s = "";
		if (value != null) {
			s = value.toString();
		}
		return s;
	}

	private String getString(Date value) {
		String s = "";
		if (value != null) {
			s = value.toString();
		}
		return s;
	}

	private String getString(Long value) {
		String s = "";
		if (value != null) {
			s = Long.toString(value);
		}
		return s;
	}

	private String getString(String value) {
		String s = "";
		if (value != null) {
			s = value;
		}
		return s;
	}
}
