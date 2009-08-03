package com.gawkat.core.server.jdo.data;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.gawkat.core.server.jdo.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Session_AccessTokenJdo {

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
    this.thingId = thingId;
    this.accessToken = accessToken;
    this.accessTokenSecret = accessTokenSecret;
    this.dateCreated = new Date();
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    boolean success = false;
    try {
      pm.makePersistent(this);
      success = true;
    } finally {
      pm.close();
    }
    return success;
  }
  
  public Long getId() {
    return this.id;
  }
  
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
  public static Session_AccessTokenJdo[] query(String accessToken, String accessTokenSecret) {
    
   Session_AccessTokenJdo[] at = null;

    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {

      String filter = "accessToken==\"" + accessToken + "\" && accessTokenSecret==\"" + accessTokenSecret + "\" ";
      Query query = pm.newQuery(Session_AccessTokenJdo.class, filter);

      try {
        List<Session_AccessTokenJdo> results = (List<Session_AccessTokenJdo>) query.execute();
        if (results.iterator().hasNext()) {
          at = new Session_AccessTokenJdo[results.size()];
          int i=0;
          for (Session_AccessTokenJdo o : results) {
            at[i] = new Session_AccessTokenJdo();
            at[i] = o;
            i++;
          }
        } else {
          at = null;
        }
      } finally {
        query.closeAll();
      }

    } finally {
      pm.close();
    }

    return at;
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
    try {
      Session_AccessTokenJdo sa = null;
      sa = pm.getObjectById(Session_AccessTokenJdo.class, id);
      sa.setThingId(userId);
      // TODO - was I supposed to change the ThingTypeId to user?
      pm.makePersistent(sa);
      success = true;
    } finally {
      pm.close();
    }
    
    return success;
  }



  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
