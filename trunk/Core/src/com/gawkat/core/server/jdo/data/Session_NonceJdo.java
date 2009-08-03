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
public class Session_NonceJdo {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long nonceId;
  
  @Persistent
  private String url;
  
  @Persistent
  private Long thingTypeId;
  
  @Persistent
  private Long thingId;
  
  @Persistent
  private String nonce;
  
  @Persistent
  private Date dateCreated;
 
  @Persistent
  private Date dateUpdated;
  
  /**
   * insert nonce
   * 
   * @param url
   * @param thingTypeId
   * @param thingId
   * @param nonce
   */
  public void insert(String url, Long thingTypeId, Long thingId, String nonce) {
    this.url = url;
    this.thingTypeId = thingTypeId;
    this.thingId = thingId;
    this.dateCreated = new Date();
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(this);
    } finally {
      pm.close();
    }
  }
  
  /**
   * does this nonce exist, b/c if it does, its not to be used agian
   * 
   * @param thingTypeId
   * @param thingId
   * @param nonce
   * @return
   */
  public static boolean doesNonceExist(Long thingTypeId, Long thingId, String nonce) {

    boolean found = false;

    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {

      String filter = "thingTypeId==" + thingTypeId + " && thingId==\"" + thingId + "\" && nonce==\"" + nonce + "\" ";
      Query query = pm.newQuery(Session_NonceJdo.class,filter);

      try {
        List<Session_NonceJdo> results = (List<Session_NonceJdo>) query.execute();
        if (results.iterator().hasNext()) {
          found = true;
        } else {
          found = false;
        }
      } finally {
        query.closeAll();
      }

    } finally {
      pm.close();
    }

    return found;
  }

}
