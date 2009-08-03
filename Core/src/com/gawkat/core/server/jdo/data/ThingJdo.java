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
public class ThingJdo {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long thingId;
  
  // what type of thing is this, user, group, object?
  @Persistent
  private Long thingTypeId;
  
  @Persistent
  private String key;
  
  @Persistent
  private String secret;
  
  @Persistent
  private Date dateCreated;
  
  @Persistent
  private Date dateUpdated;
  
  /**
   * constructor
   */
  public ThingJdo() {
  }
  
  /**
   * insert thing
   * 
   * @param thingTypeId
   * @param key
   * @param secret
   */
  public void insert(long thingTypeId, String key, String secret) {
    this.thingTypeId = thingTypeId;
    this.key = key;
    this.secret = secret;
    this.dateCreated = new Date();
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(this);
    } finally {
      pm.close();
    }
  }
  
  public Long getId() {
    return this.thingId;
  }
  
  public String getKey() {
    return this.key;
  }
  
  public String getSecret() {
    return this.secret;
  }
  
  /**
   * query a thing by its id
   * 
   * @param thingId
   * @return
   */
  public static ThingJdo query(long thingId) {
    ThingJdo thing = null;
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      thing = pm.getObjectById(ThingJdo.class, thingId);
    } finally {
      pm.close();
    }
    return thing;
  }
  
  /**
   * query thing by key
   * 
   * @param key
   * @return
   */
  public static ThingJdo[] query(long thingTypeId, String key) {
    
    ThingJdo[] things = null;

    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {

      String filter = "thingTypeId==" + thingTypeId + " && key==\"" + key + "\" ";
      Query query = pm.newQuery(ThingJdo.class, filter);

      try {
        List<ThingJdo> results = (List<ThingJdo>) query.execute();
        if (results.iterator().hasNext()) {
          things = new ThingJdo[results.size()];
          int i=0;
          for (ThingJdo o : results) {
            things[i] = new ThingJdo();
            things[i] = o;
            i++;
          }
        } else {
          things = null;
        }
      } finally {
        query.closeAll();
      }

    } finally {
      pm.close();
    }

    return things;
  }





  
}
