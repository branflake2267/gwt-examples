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

import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.server.ServerPersistence;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SessionAccessTokenJdo {

	@NotPersistent
	private ServerPersistence sp;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key idKey;

	@Persistent
	private long thingTypeId;

	@Persistent
	private long thingId;

	@Persistent
	private String accessToken;

	@Persistent
	private String accessTokenSecret;

	@Persistent
	private Date dateCreated;

	@Persistent
	private Date dateUpdated;

	public SessionAccessTokenJdo(ServerPersistence sp) {
		this.sp = sp;
	}

	/**
	 * insert access token
	 * 
	 * @param thingTypeId
	 * @param thingId
	 * @param accessToken
	 * @param accessTokenSecret
	 */
	public boolean insert(Long thingTypeId, Long thingId, String accessToken, String accessTokenSecret) {
		this.thingTypeId = thingTypeId;
		this.thingId = thingId;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
		this.dateCreated = new Date();

		boolean success = false;
		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(this);
			tx.commit();
			success = true;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return success;
	}

	/**
	 * get identity
	 * 
	 * @return
	 */
	public long getId() {
		return idKey.getId();
	}

	/**
	 * get thing id
	 * @param thingId
	 */
	public void setThingId(long thingId) {
		this.thingId = thingId;
	}

	public void setThingTypeId(long thingTypeId) {
		this.thingTypeId = thingTypeId;
	}

	public long getThingId() {
		return thingId;
	}

	public long getThingTypeId() {
		return thingTypeId;
	}

	/**
	 * query accessToken - this is probably overkill for what I need, only need one object at a time most of the time
	 * 
	 * @param accessToken
	 * @param accessTokenSecret
	 * @return
	 */
	public SessionAccessTokenJdo[] query(String accessToken, String accessTokenSecret) {

		String qfilter = "accessToken==\"" + accessToken + "\" && accessTokenSecret==\"" + accessTokenSecret + "\" ";

		ArrayList<SessionAccessTokenJdo> aT = new ArrayList<SessionAccessTokenJdo>();

		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<SessionAccessTokenJdo> e = pm.getExtent(SessionAccessTokenJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			q.execute();

			Collection<SessionAccessTokenJdo> c = (Collection<SessionAccessTokenJdo>) q.execute();
			Iterator<SessionAccessTokenJdo> iter = c.iterator();
			while (iter.hasNext()) {
				SessionAccessTokenJdo t = (SessionAccessTokenJdo) iter.next();
				aT.add(t);
			}

			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}

		SessionAccessTokenJdo[] r = null;
		if (aT.size() > 0) {
			r = new SessionAccessTokenJdo[aT.size()];
			aT.toArray(r);
		}

		return r;
	}

	/**
	 * get the owner of the session
	 * 
	 * @param accessToken
	 * @param accessTokenSecret
	 * @return
	 */
	public ThingData getThingData(String accessToken, String accessTokenSecret) {
		SessionAccessTokenJdo[] s = query(accessToken, accessTokenSecret);
		ThingData t = new ThingData(s[0].getThingTypeId(), s[0].getThingId());
		return t;
	}

	/**
	 * update Access Token and change the owner to userId;
	 * @param id
	 * @param userId
	 * @return
	 */
	public boolean updateAccessToken(long id, long userId) {

		boolean success = false;

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			
			SessionAccessTokenJdo sa = null;
			sa = pm.getObjectById(SessionAccessTokenJdo.class, id);
			sa.setThingId(userId);
			sa.setThingTypeId(ThingTypeJdo.TYPE_USER); // switch session var to the user and not the application
			
			pm.makePersistent(sa);
			
			success = true;
			
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return success;
	}


















}
