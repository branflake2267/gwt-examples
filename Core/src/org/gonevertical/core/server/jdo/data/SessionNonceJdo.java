package org.gonevertical.core.server.jdo.data;

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

import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SessionNonceJdo {

	@NotPersistent
	private ServerPersistence sp;
	
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key nonceIdKey;
  
  @Persistent
  private String url;
  
  @Persistent
  private long thingTypeId;
  
  @Persistent
  private long thingId;
  
  @Persistent
  private String nonce;
  
  @Persistent
  private Date dateCreated;
 
  @Persistent
  private Date dateUpdated;

  public SessionNonceJdo(ServerPersistence sp) {
  	this.sp = sp;
  }
  
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
    this.nonce = nonce;
    this.dateCreated = new Date();
    
    PersistenceManager pm = sp.getPersistenceManager();
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
   * does this nonce exist - nonce is a one time use only
   * 
   * @param thingTypeId
   * @param thingId
   * @param nonce
   * @return
   */
  public boolean doesNonceExist(Long thingTypeId, Long thingId, String nonce) {

    String qfilter = "thingTypeId==" + thingTypeId + " && thingId==" + thingId + " && nonce==\"" + nonce + "\" ";
    
    boolean found = false;

    PersistenceManager pm = sp.getPersistenceManager();
    try {
      Extent<SessionNonceJdo> e = pm.getExtent(SessionNonceJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();

      Collection<SessionNonceJdo> c = (Collection<SessionNonceJdo>) q.execute();
      Iterator<SessionNonceJdo> iter = c.iterator();
      while (iter.hasNext()) {
      	found = true;
      }

      q.closeAll();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      pm.close();
    }

    return found;
  }

}
