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

import org.gonevertical.core.client.ui.admin.thing.ThingDataFilter;
import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;

/**
 * supplemental join system, until one is born from gae
 *    Until then, this will be redundant data storage, but I have to build it somehow. This seems to work in theory sor far.
 *    Joins are basically flat tables of both left and right tables, so this will be specific and excatly what I need for now.
 *    I hope the data storage doesn't go out of control, guess I don't know until I try
 * 
 * This Join represents ThingJdo and Thing StuffJdo, and maybe thingStuffAboutJdo
 * 
 * @author branflake2267
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class DataJoinJdo {

	@NotPersistent
	private static final Logger log = Logger.getLogger(DataJoinJdo.class.getName());

	@NotPersistent
	private ServerPersistence sp;

	//@NotPersistent
	//private ThingJdo tj;

	//@NotPersistent
	//private ThingStuffJdo tsj;

	@NotPersistent
	private Date buildDate;



	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private Date joinUpdatedDate;


	// left table
	@Persistent
	private long thingId; 

	//what type of thing is this, user, group, object?
	@Persistent
	private long thingTypeId;

	// username is represented here
	@Persistent
	private String thingKey;

	// password sha1 hash is represented here
	@Persistent
	private String thingSecret;

	// when did this start in time
	@Persistent
	private Date thingStartOf;

	// when did this end in time
	@Persistent
	private Date thingEndOf;

	// order the list by this
	@Persistent
	private Double thingRank;

	// when this object was created
	@Persistent
	private Date thingDateCreated;

	// when this object was updated last
	@Persistent
	private Date thingDateUpdated;

	// who created this object
	@Persistent
	private long thingCreatedByThingId;

	// who last updated this object
	@Persistent
	private long thingUpdatedByThingId;

	// assign ownership of this thing to this thing
	@Persistent
	private long[] thingOwnerThingIds;




	// right table
	@Persistent
	private long stuffParentStuffId;

	@Persistent
	private long stuffId;

	// why kind of stuff, defined as type
	@Persistent
	private long stuffTypeId;

	// values that can be stored
	@Persistent
	private String stuffValue;

	@Persistent
	private Boolean stuffValueBol;

	@Persistent
	private Double stuffValueDouble;

	@Persistent
	private Long stuffValueLong;

	@Persistent 
	private Date stuffValueDate;

	// when did this start in time
	@Persistent
	private Date stuffStartOf;

	// when did this end in time
	@Persistent
	private Date stuffEndOf;

	// order the list by this
	@Persistent
	private Double stuffRank;

	// when this object was created
	@Persistent
	private Date stuffDateCreated;

	// when this object was updated
	@Persistent
	private Date stuffDateUpdated;

	// who created this object
	@Persistent
	private long stuffCreatedByThingId;

	// who updated this object
	@Persistent
	private long stuffUpdatedByThingId;

	// assign ownership of this thing to this thing
	@Persistent
	private long[] ownerThingIds;

	/**
	 * constructor - use the one with sp
	 * @throws NullPointerException
	 */
	public DataJoinJdo() throws NullPointerException {
		// don't use this one
	}

	/**
	 * constructor - nothing to do
	 */
	public DataJoinJdo(ServerPersistence sp) {
		this.sp = sp;
	}

	public void set(ServerPersistence sp) {
		this.sp = sp;
		//tj.set(sp);
		//tsj.set(sp);
	}

	private void setData(ThingJdo thingJdo, ThingStuffJdo thingStuffJdo) {
		setThingJdo(thingJdo);
		setStuffJdo(thingStuffJdo);
	}

	private void setThingJdo(ThingJdo tj) {
		this.thingId = tj.getThingId();
		this.thingTypeId = tj.getThingTypeId();
		this.thingKey = tj.getKey();
		this.thingSecret = tj.getSecret();
		this.thingStartOf = tj.getStartOf();
		this.thingEndOf = tj.getEndOf();
		this.thingRank = tj.getRank();
		this.thingDateCreated = tj.getDateCreated();
		this.thingDateUpdated = tj.getDateUpdated();
		this.thingCreatedByThingId = tj.getCreatedBy();
		this.thingUpdatedByThingId = tj.getUpdatedBy();
		this.thingOwnerThingIds = tj.getOwners();
	}

	private void setStuffJdo(ThingStuffJdo sj) {
		this.stuffParentStuffId = sj.getParentStuffId();
		this.stuffId = sj.getStuffId();
		this.stuffTypeId = sj.getStuffTypeId();
		this.stuffValue = sj.getValue();
		this.stuffValueBol = sj.getValueBol();
		this.stuffValueDouble = sj.getValueDouble();
		this.stuffValueLong = sj.getValueLong();
		this.stuffValueDate = sj.getValueDate();
		this.stuffStartOf = sj.getStartOf();
		this.stuffEndOf = sj.getEndOf();
		this.stuffRank = sj.getRank();
		this.stuffDateCreated = sj.getDateCreated();
		this.stuffDateUpdated = sj.getDateUpdated();
		this.stuffCreatedByThingId = sj.getCreatedBy();
		this.stuffUpdatedByThingId = sj.getUpdatedBy();
		this.ownerThingIds = sj.getOwners();
	}

	private long getId() {
		long i = 0;
		if (id != null) {
			i = id.getId();
		}
		return i;
	}

	public void setBuildDate(Date date) {
		buildDate = date;
	}

	private void setJoinBuildDate(Date date) {
		joinUpdatedDate = date;
	}
	
	private long getThingId() {
	  return thingId;
  }

	public boolean save(long stuffId) {

		if (stuffId == 0) {
			return false;
		}

		// get stuffJdo
		// TODO change back to class var for ThinStuffJdo
		ThingStuffJdo tsd = new ThingStuffJdo(sp).queryJdo(stuffId);

		if (tsd == null) {
			return false;
		}

		// get ThingJdo 
		// TODO - change back to class var for ThingJdo
		ThingJdo td = new ThingJdo(sp).queryJdo(tsd.getParentThingId());

		if (td == null) {
			return false;
		}

		// save
		boolean success = save(td, tsd);

		return success;
	}

	private boolean save(ThingJdo thingJdo, ThingStuffJdo thingStuffJdo) {
		setData(thingJdo, thingStuffJdo);

		DataJoinJdo update = idExist(thingJdo, thingStuffJdo);
		if (update != null) {
			update.set(sp);
		}

		boolean success = false;
		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			if (update != null) { // update
				if (joinUpdatedDate != null) {
					update.setJoinBuildDate(buildDate);
				}
				update.setData(thingJdo, thingStuffJdo);
				this.id = update.id;

			} else { // insert
				if (joinUpdatedDate != null) {
					joinUpdatedDate = buildDate;
				}
				id = null;
				pm.makePersistent(this);
			}

			tx.commit();
			success = true;
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "save(): ", e);
			success = false;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return success;
	}

	private DataJoinJdo idExist(ThingJdo thingJdo, ThingStuffJdo thingStuffJdo) {

		long stuffId = thingStuffJdo.getStuffId();

		DataJoinJdo[] djj = queryByStuffId(stuffId);

		DataJoinJdo r = null;
		if (djj != null && djj.length > 0) {
			r = djj[0];

		} else if (djj != null && djj.length > 1) { // something must have went wrong, so lets take care of it now
			deleteExtras(djj);
			// TODO report the problem
		}

		return r;
	}

	/**
	 * shouldn't be more than one of these
	 *   This could change if I add in another join of stuff
	 * @param djj
	 */
	private void deleteExtras(DataJoinJdo[] djj) {
		if (djj == null) {
			return;
		}
		for (int i=0; i < djj.length; i++) {
			if (i > 1) {
				delete(djj[i]);
			}
		}
	}

	private boolean delete(DataJoinJdo djj) {

		System.out.println("deleting DataJoin, something must have went wrong.");

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean success = false;
		try {
			tx.begin();
			DataJoinJdo ttj2 = (DataJoinJdo) pm.getObjectById(DataJoinJdo.class, djj.getId());
			pm.deletePersistent(ttj2);
			tx.commit();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
				success = false;
			}
			pm.close();
		}
		return success;
	}

	private DataJoinJdo[] queryByStuffId(long stuffId) {

		String qfilter = "stuffId==" + stuffId;

		DataJoinJdo[] r = null;
		Collection<DataJoinJdo> c = null;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<DataJoinJdo> e = pm.getExtent(DataJoinJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			c = (Collection<DataJoinJdo>) q.execute();

			// I wonder why I can't have this outside of query yet? have to disconnect the collection Or does it stream somehow, which I think it does.. hmmm
			if (c != null && c.size() != 0) {
				r = new DataJoinJdo[c.size()];
				if (c.size() > 0) {
					r = new DataJoinJdo[c.size()];
					c.toArray(r);
				}
			}

			q.closeAll();
		} finally {
			pm.close();
		}

		return r;
	}

	/**
	 * delete by stuff id
	 *   TODO - maybe switch to Key query
	 * @param stuffId
	 * @return
	 */
	@SuppressWarnings("unchecked")
  public boolean deleteByStuffId(long stuffId) {
		String qfilter = "stuffId==" + stuffId + "";
		boolean success = false;
		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Extent<DataJoinJdo> e = pm.getExtent(DataJoinJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();
			Collection<DataJoinJdo> c = (Collection<DataJoinJdo>) q.execute();
			pm.deletePersistentAll(c);
			tx.commit();
			q.closeAll();
			success = true;
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
	
	/**
	 * delete by thingId
	 *   TODO maybe switch to Key query
	 * @param thingId
	 * @return
	 */
	@SuppressWarnings("unchecked")
  public boolean deleteByThingId(long thingId) {
		String qfilter = "thingId==" + thingId + "";
		boolean success = false;
		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Extent<DataJoinJdo> e = pm.getExtent(DataJoinJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();
			Collection<DataJoinJdo> c = (Collection<DataJoinJdo>) q.execute();
			pm.deletePersistentAll(c);
			tx.commit();
			q.closeAll();
			success = true;
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

	/**
	 * purge records before this date - which stands for the latest update
	 * @param buildDate
	 */
	@SuppressWarnings("unchecked")
  public boolean deleteRecordsBefore(Date buildDate) {
		String qfilter = "joinUpdatedDate > buildDate";
		boolean success = false;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Query q = pm.newQuery("select id from " + DataJoinJdo.class.getName());
			q.setFilter(qfilter);
			q.declareImports("import java.util.Date");
			q.declareParameters("Date buildDate");
			q.setOrdering("joinUpdatedDate desc");
			List<Key> ids = (List<Key>) q.execute(buildDate);
			Iterator<Key> itr = ids.iterator();
			while(itr.hasNext()) {
				Key idkey = itr.next();
				long id = idkey.getId();
				deleteById(id);
			}
			success = true;
			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "deleteRecordsBefore(): ", e);
		} finally {
			pm.close();
		}
		return success;
	}

	private boolean deleteById(long id) {
		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean b = false;
		try {
			tx.begin();
			DataJoinJdo djj = (DataJoinJdo) pm.getObjectById(DataJoinJdo.class, id);
			pm.deletePersistent(djj);
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
  public boolean deleteByStuffParent(long parentStuffId) {
		String qfilter = "parentStuffId==" + parentStuffId;
		boolean success = false;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Query q = pm.newQuery("select stuffIdKey from " + ThingStuffJdo.class.getName());
			q.setFilter(qfilter);
			List<Key> ids = (List<Key>) q.execute();
			Iterator<Key> itr = ids.iterator();
			while(itr.hasNext()) {
				Key stuffIdkey = itr.next();
				long stuffId = stuffIdkey.getId();
				deleteByStuffId(stuffId);
			}
			success = true;
			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "joinUpdatedDate(): ", e);
		} finally {
			pm.close();
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	public long queryTotal() {
		long total = 0;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Query q = pm.newQuery("select id from " + DataJoinJdo.class.getName());
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
	
	/* TODO query by keys, but need a filter setup?
	public Key[] query_Keys(ThingDataFilter filter) {
		if (filter.getThingIdLink() > 0) {
			DataJoinJdo db = new DataJoinJdo(sp);
			long[] thingIds = db.getThingIds_ByLinkers(filter.getThingIdLink());
			if (thingIds  == null) {
				return null;
			}
			filter.setThingIds(thingIds);
		}

		String qfilter = filter.getFilter_Or();
		//System.out.println("queryfilter: " + qfilter);

		Key[] keys = null;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Query q = pm.newQuery("select thingIdKey from " + ThingJdo.class.getName());
			q.setFilter(qfilter);
			q.setRange(filter.getRangeStart(), filter.getRangeFinish());
			q.execute();
			Collection<Key> c = (Collection<Key>) q.execute();
			keys = new Key[c.size()];
			if (c.size() > 0) {
				keys = new Key[c.size()];
				c.toArray(keys);
			}
			q.closeAll();
		} finally {
			pm.close();
		}
		return keys;
	}
	*/
	
	/**
	 * look into the stuff of other things, and find this thingId
	 * 
	 * TODO add range?
	 * 
	 * @param thingIdLink
	 * @return
	 */
	public long[] getThingIds_ByLinkers(long thingIdLink) {
		if (thingIdLink == 0) {
			return null;
		}
		// filter by links
		String qfilter =  "stuffTypeId==2 && stuffValueLong==" + thingIdLink + "";
		long[] thingIds = null;;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<DataJoinJdo> e = pm.getExtent(DataJoinJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();
			Collection<DataJoinJdo> c = (Collection<DataJoinJdo>) q.execute();
			thingIds = new long[c.size()];
			Iterator<DataJoinJdo> iter = c.iterator();
			int i =0;
			while (iter.hasNext()) {
				DataJoinJdo djj = (DataJoinJdo) iter.next();
				Long thingId = djj.getThingId();
				if (thingId != null) {
					thingIds[i] = thingId;
				}
				i++;
			}
			q.closeAll();
		} finally {
			pm.close();
		}
		return thingIds;
	}

	



}




