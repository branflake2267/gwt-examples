package com.gawkat.core.server.jdo.data;

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
public class SessionNonceJdo {

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

    String qfilter = "thingTypeId==" + thingTypeId + " && thingId==\"" + thingId + "\" && nonce==\"" + nonce + "\" ";
    
    boolean found = false;

    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<SessionNonceJdo> e = pm.getExtent(SessionNonceJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();

      Collection<SessionNonceJdo> c = (Collection<SessionNonceJdo>) q.execute();
      Iterator<SessionNonceJdo> iter = c.iterator();
      while (iter.hasNext()) {
        found = true;
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

    return found;
  }

}
