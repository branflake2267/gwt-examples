package org.gonevertical.core.server.jdo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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

import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
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
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	
	

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
	 * constructor - nothing to do
	 */
	public DataJoinJdo(ServerPersistence sp) {
		this.sp = sp;
	}
	
	public void set(ServerPersistence sp) {
		this.sp = sp;
	}

	public void setData(ThingJdo thingJdo, ThingStuffJdo thingStuffJdo) {
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
	
	public long save(ThingJdo thingJdo, ThingStuffJdo thingStuffJdo) {
		setData(thingJdo, thingStuffJdo);

		DataJoinJdo update = idExist(thingJdo, thingStuffJdo);
		update.set(sp);
		
		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			if (update != null) { // update
				update.setData(thingJdo, thingStuffJdo);			
				this.id = update.id;
	
			} else { // insert
				id = null;
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

	private DataJoinJdo idExist(ThingJdo thingJdo, ThingStuffJdo thingStuffJdo) {
	  
		long stuffId = thingStuffJdo.getStuffId();
		
		DataJoinJdo[] djj = query(stuffId);
		
		DataJoinJdo r = null;
		if (djj == null || djj.length > 0) {
			r = djj[0];
			
		} else if (djj.length > 1) { // something must have went wrong, so lets take care of it now
			deleteExtras(djj);
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

	private void delete(DataJoinJdo djj) {

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean b = false;
		try {
			tx.begin();

			DataJoinJdo ttj2 = (DataJoinJdo) pm.getObjectById(DataJoinJdo.class, djj.getId());
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

  }

	private long getId() {
	  return this.getId();
  }
	
	public DataJoinJdo[] query(long stuffId) {

		ArrayList<DataJoinJdo> aT = new ArrayList<DataJoinJdo>();

		String qfilter = "stuffId==" + stuffId;

		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<ThingJdo> e = pm.getExtent(ThingJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();

			Collection<DataJoinJdo> c = (Collection<DataJoinJdo>) q.execute();
			Iterator<DataJoinJdo> iter = c.iterator();
			while (iter.hasNext()) {
				DataJoinJdo t = (DataJoinJdo) iter.next();
				aT.add(t);
			}

			q.closeAll();
		} finally {
			pm.close();
		}
		
		if (aT.size() == 0) {
			return null;
		}

		DataJoinJdo[] r = new DataJoinJdo[aT.size()];
		if (aT.size() > 0) {
			r = new DataJoinJdo[aT.size()];
			aT.toArray(r);
		}
		return r;
	}
	
	
	
}




