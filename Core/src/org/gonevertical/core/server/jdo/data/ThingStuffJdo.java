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
public class ThingStuffJdo {

	@NotPersistent
	private static final Logger log = Logger.getLogger(ThingStuffJdo.class.getName());
	
	@NotPersistent
	private ServerPersistence sp;
	
	@NotPersistent 
	private DataJoinJdo dataJoin;

  // who is the parent
	@Persistent
	private long parentThingId;
	
  // recursive parent relationship
	@Persistent
	private long parentStuffId;
	
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key stuffIdKey;

	// why kind of stuff, defined as type
	@Persistent
	private long stuffTypeId;

	// values that can be stored
	@Persistent
	private String value;

	@Persistent
	private Boolean valueBol;

	@Persistent
	private Double valueDouble;

	@Persistent
	private Long valueLong;

	@Persistent 
	private Date valueDate;

	// when did this start in time
	@Persistent
	private Date startOf;

	// when did this end in time
	@Persistent
	private Date endOf;
	
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
	public ThingStuffJdo() throws Exception {
		//System.err.println("Don't use this constructor - Exiting");
		//throw new Exception();
	}
	
	/**
	 * constructor
	 */
	public ThingStuffJdo(ServerPersistence sp) {
		this.sp = sp;
		this.dataJoin = new DataJoinJdo(sp); 
	}
	
	public void set(ServerPersistence sp) {
		this.sp = sp;
		dataJoin.set(sp);
	}

	public void setData(ThingStuffData thingStuffData) {
		if (thingStuffData == null) {
			return;
		}

		// can't do this here, bc it messes up the key gotten from getObjectById, cant mutate key
		//setKey(thingStuffData.getStuffId());

		this.parentThingId = thingStuffData.getParentThingId();
		this.parentStuffId = thingStuffData.getParentStuffId();
		
		this.stuffTypeId = thingStuffData.getStuffTypeId();

		this.value = thingStuffData.getValue();
		this.valueBol = thingStuffData.getValueBol();
		this.valueDouble = thingStuffData.getValueDouble();
		this.valueLong = thingStuffData.getValueLong();
		this.valueDate = thingStuffData.getValueDate();

		this.startOf = thingStuffData.getStartOf();
		this.endOf = thingStuffData.getEndOf();
		
		this.rank = thingStuffData.getRank();
		this.ownerThingIds = thingStuffData.getOwners();

		if (thingStuffData != null && thingStuffData.getStuffId() > 0) {
			this.dateUpdated = new Date();
 			this.updatedByThingId = sp.getUserThingId();
		} else {
			this.dateCreated = new Date();
			this.createdByThingId = sp.getUserThingId();
		}
	}

	public void setData(ThingStuffJdo thingStuffJdo) {
		if (thingStuffJdo == null) {
			return;
		}
		setKey(thingStuffJdo.getStuffId());

		this.parentThingId = thingStuffJdo.getParentThingId();
		this.parentStuffId = thingStuffJdo.getParentStuffId();
		
		this.stuffTypeId = thingStuffJdo.getStuffTypeId();

		this.value = thingStuffJdo.getValue();
		this.valueBol = thingStuffJdo.getValueBol();
		this.valueDouble = thingStuffJdo.getValueDouble();
		this.valueLong = thingStuffJdo.getValueLong();
		this.valueDate = thingStuffJdo.getValueDate();

		this.startOf = thingStuffJdo.getStartOf();
		this.endOf = thingStuffJdo.getEndOf();
		
		this.rank = thingStuffJdo.getRank();
		this.ownerThingIds = thingStuffJdo.getOwners();

		if (stuffIdKey != null && stuffIdKey.getId() > 0) {
			this.dateUpdated = new Date();
			this.updatedByThingId = sp.getUserThingId();
		} else {
			this.dateCreated = new Date();
			this.createdByThingId = sp.getUserThingId();
		}
	}

	public long save(ThingStuffData stuffData) {
		setData(stuffData);

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			if (stuffData != null && stuffData.getStuffId() > 0) { // update
				ThingStuffJdo update = pm.getObjectById(ThingStuffJdo.class, stuffData.getStuffId());
				update.set(sp);
				update.setData(stuffData);
				this.stuffIdKey = update.stuffIdKey;
				
			} else { // insert
				stuffIdKey = null;
				pm.makePersistent(this);
			}
			tx.commit();

			boolean success = dataJoin.save(getStuffId());
			if (success == false) {
				tx.rollback();
				// TODO - need to figure out what to do with failure
			} 
			
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "save(): ", e);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		// debug
		//System.out.println("ThingJdo: thingStuffId: " + getId() + " thingStuffTypeId: " + thingStuffTypeId + " " +
		//"value: " + getString(value) + " valueBol: " + getString(valueBol) + " valueLong: " + getString(valueLong) + " valueDate: " + getString(valueDate));

		return getStuffId();
	}

	public long saveUnique(ThingStuffData stuffData) {
		setData(stuffData);

		// setup filter so that I only create unique by identities [thingId, thingStuffTypeId)
		ThingStuffDataFilter filter = new ThingStuffDataFilter();
		filter.setParentThingId(stuffData.getParentThingId());
		filter.setStuffTypeId(stuffData.getStuffTypeId());
		
		if (stuffData.getValue() != null) {
			filter.setValueString(stuffData.getValue());
		}
		
		if (stuffData.getValueBol() != null) {
			filter.setValueBoolean(stuffData.getValueBol());
		}
		
		if (stuffData.getValueDouble() != null) {
			filter.setValueDouble(stuffData.getValueDouble());
		}
		
		if (stuffData.getValueLong() != null) {
			filter.setValueLong(stuffData.getValueLong());
		}

		ThingStuffData[] tsds = query(filter);
		if (tsds != null && tsds.length > 0) {
			ThingStuffData tsd = tsds[0];
			tsds[0].setValue(stuffData.getValue());
			tsds[0].setValue(stuffData.getValueBol());
			tsds[0].setValue(stuffData.getValueDouble());
			tsds[0].setValue(stuffData.getValueLong());
			tsds[0].setValue(stuffData.getValueDate());
			
			save(tsd);
			return stuffData.getStuffId();
		}

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			stuffIdKey = null;
			pm.makePersistent(this);
			
			boolean success = dataJoin.save(getStuffId());
			if (success == false) {
				tx.rollback();
				// TODO - need to figure out what to do with failure
			} 
			
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

		return getStuffId();
	}

	/**
	 * query a thing by its id
	 * 
	 * @param stuffId
	 * @return
	 */
	public ThingStuffJdo queryJdo(long stuffId) {

		ThingStuffJdo thingStuff = null;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			thingStuff = pm.getObjectById(ThingStuffJdo.class, stuffId);
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
			pm.close();
		}

		return thingStuff;
	}

	public ThingStuffsData queryStuffs(ThingStuffDataFilter filter) {

		ThingStuffData[] tsd = query(filter);

		ThingStuffsData tsds = new ThingStuffsData();
		tsds.setTotal(queryTotal());
		tsds.setThingStuffData(tsd);

		return tsds;
	}

	public ThingStuffData[] query(ThingStuffDataFilter filter) {

		if (filter == null) {
			log.severe("ERROR: ThingStuffJdo.query() Set a filter");
			return null;
		}

		String qfilter = filter.getFilter_And();
		
		System.out.println("filter=" + qfilter);
		
		ArrayList<ThingStuffJdo> aT = new ArrayList<ThingStuffJdo>();
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<ThingStuffJdo> e = pm.getExtent(ThingStuffJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();

			Collection<ThingStuffJdo> c = (Collection<ThingStuffJdo>) q.execute();
			Iterator<ThingStuffJdo> iter = c.iterator();
			
			// TODO - making my own pagination for now, fix this later
			int i=0;
			while (iter.hasNext()) {
				
				ThingStuffJdo t = (ThingStuffJdo) iter.next();
				
				// TODO work around, this sucks - change it later to something better - ran out of time today.
				if (i >= filter.getRangeStart() && i <= filter.getRangeFinish()) {
					aT.add(t);
				}
				
				i++;
			}

			q.closeAll();
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
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
			Extent<ThingStuffJdo> e = pm.getExtent(ThingStuffJdo.class, true);
			Query q = pm.newQuery(e);
			q.execute();

			Collection<ThingStuffJdo> c = (Collection<ThingStuffJdo>) q.execute();
			total = c.size();

			q.closeAll();
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
			pm.close();
		}

		return total;
	}

	public static ThingStuffData[] convert(List<ThingStuffJdo> thingStuffJdoAbout) {

		Iterator<ThingStuffJdo> itr = thingStuffJdoAbout.iterator();

		ThingStuffData[] r = new ThingStuffData[thingStuffJdoAbout.size()];

		int i = 0;
		while(itr.hasNext()) {

			ThingStuffJdo tsja = itr.next();

			r[i] = new ThingStuffData();
			r[i].setData(
					tsja.getParentThingId(),
					tsja.getStuffId(), 
					tsja.getStuffTypeId(), 

					tsja.getValue(), 
					tsja.getValueBol(), 
					tsja.getValueDouble(),
					tsja.getValueLong(), 
					tsja.getValueDate(),
					
					tsja.getStartOf(),
					tsja.getEndOf(), 
					tsja.getRank(),
					tsja.getCreatedBy(),
					tsja.getDateCreated(),
					tsja.getUpdatedBy(),
					tsja.getDateUpdated(),
					tsja.getOwners());

			i++;
		}

		return r;
	}

	public boolean delete(long stuffId) {

		System.out.println("ThingStuffJdo.delete(): deleting: " + stuffId);

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean b = false;
		try {
			tx.begin();

			ThingStuffJdo ttj2 = (ThingStuffJdo) pm.getObjectById(ThingStuffJdo.class, stuffId);
			pm.deletePersistent(ttj2);
			
			tx.commit();
			b = true;
			
			// delete join data
			boolean success = dataJoin.deleteByStuffId(stuffId);
			if (success = false) {
				b = success;
				tx.rollback();
			}
			
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
	
	public boolean deleteByParentStuffId(long parentStuffId) {

		if (parentStuffId == 0) {
			return false;
		}

		String qfilter = "parentStuffId==" + parentStuffId + "";
		
		boolean success = false;
		PersistenceManager pm = sp.getPersistenceManager();
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
			success = true;
			
			// delete join data
			success = dataJoin.deleteByStuffParent(parentStuffId);
			if (success = false) {	
				tx.rollback();
			}
			
			q.closeAll();
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return success;
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

			Extent<ThingStuffJdo> e = pm.getExtent(ThingStuffJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();

			Collection<ThingStuffJdo> c = (Collection<ThingStuffJdo>) q.execute();

			// delete all
			pm.deletePersistentAll(c);

			tx.commit();
			q.closeAll();
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return true;
	}

	
	
	private void setParentThingId(long thingId) {
		this.parentThingId = thingId;
  }

	public long getParentThingId() {
		return parentThingId;
	}
	
	
	public void setParentStuffId(long parentStuffId) {
		this.parentStuffId = parentStuffId;
	}
	
	public long getParentStuffId() {
		return parentStuffId;
	}
	
	
	
	
	public void setStuffIdKey(Key stuffIdKey) {
		this.stuffIdKey = stuffIdKey;
  }
	
	public long getStuffId() {
		if (stuffIdKey == null) {
			return -1;
		}
		return stuffIdKey.getId();
	}

		
	
	private void setKey(long id) {
		if (id > 0) {
			stuffIdKey = getKey(id);
		}
	}
	
	private Key getKey(long id) {
		Key key = null;
		if (id == 0) {
			key = KeyFactory.createKey(ThingStuffJdo.class.getSimpleName(), id);
		}
		return key;
	}


	
	private void setStuffTypeId(long stuffTypeId) {
		this.stuffTypeId = stuffTypeId;
  }
	
	public long getStuffTypeId() {
		return stuffTypeId;
	}




	private void setValueDouble(Double valueDouble) {
		this.valueDouble = valueDouble;
  }

	public void setValue(Double value) {
		this.valueDouble = value;
	}
	
	public Double getValueDouble() {
		return valueDouble;
	}
	
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	
	public void setValue(Boolean value) {
		this.valueBol = value;
	}
	
	private void setValueBol(Boolean valueBol) {
		this.valueBol = valueBol;
  }

	public Boolean getValueBol() {
		return valueBol;
	}

	


	private void setValueLong(Long valueLong) {
	  this.valueLong = valueLong;
  }
	
	public Long getValueLong() {
		return valueLong;
	}
	
	
	private void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	
	public Date getValueDate() {
	  return valueDate;
  }
	
	

	private void setStartOf(Date startOf) {
	  this.startOf = startOf;
  }
	
	public Date getStartOf() {
		return startOf;
	}

	
	private void setEndOf(Date endOf) {
	  this.endOf = endOf;
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
	
	
	private void setOwnerThingIds(long[] ownerThingIds) {
		this.ownerThingIds = ownerThingIds;
  }
	
	public long[] getOwners() {
		return ownerThingIds;
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
