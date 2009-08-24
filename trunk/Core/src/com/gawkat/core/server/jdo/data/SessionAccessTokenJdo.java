package com.gawkat.core.server.jdo.data;

import java.util.ArrayList;
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
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.gawkat.core.server.jdo.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SessionAccessTokenJdo {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;
  
  @Persistent
  private Long thingTypeId;
  
  @Persistent
  private Long thingId;
  
  @Persistent
  private String accessToken;
  
  @Persistent
  private String accessTokenSecret;
  
  @Persistent
  private Date dateCreated;
  
  @Persistent
  private Date dateUpdated;
  
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
    PersistenceManager pm = PMF.get().getPersistenceManager();
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
  public Long getId() {
    return this.id;
  }
  
  /**
   * get thing id
   * @param thingId
   */
  public void setThingId(Long thingId) {
    this.thingId = thingId;
  }
  
  /**
   * query accessToken - this is probably overkill for what I need, only need one object at a time most of the time
   * 
   * @param accessToken
   * @param accessTokenSecret
   * @return
   */
  public static SessionAccessTokenJdo[] query(String accessToken, String accessTokenSecret) {

    String qfilter = "accessToken==\"" + accessToken + "\" && accessTokenSecret==\"" + accessTokenSecret + "\" ";

    ArrayList<SessionAccessTokenJdo> aT = new ArrayList<SessionAccessTokenJdo>();

    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<SessionAccessTokenJdo> e = pm.getExtent(SessionAccessTokenJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();

      Collection<SessionAccessTokenJdo> c = (Collection<SessionAccessTokenJdo>) q.execute();
      Iterator<SessionAccessTokenJdo> iter = c.iterator();
      while (iter.hasNext()) {
        SessionAccessTokenJdo t = (SessionAccessTokenJdo) iter.next();
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

    SessionAccessTokenJdo[] r = null;
    if (aT.size() > 0) {
      r = new SessionAccessTokenJdo[aT.size()];
      aT.toArray(r);
    }
    
    return r;
  }
  
  /**
   * update Access Token and change the owner to userId;
   * @param id
   * @param userId
   * @return
   */
  public static boolean updateAccessToken(Long id, Long userId) {
    
    boolean success = false;
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      SessionAccessTokenJdo sa = null;
      sa = pm.getObjectById(SessionAccessTokenJdo.class, id);
      sa.setThingId(userId);
      // TODO - was I supposed to change the ThingTypeId to user?
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
