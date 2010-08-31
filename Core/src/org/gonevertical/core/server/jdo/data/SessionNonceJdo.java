package org.gonevertical.core.server.jdo.data;

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

import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SessionNonceJdo {

	@NotPersistent
	private static final Logger log = Logger.getLogger(SessionNonceJdo.class.getName());
	
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
      Collection<SessionNonceJdo> c = (Collection<SessionNonceJdo>) q.execute();
      if (c.size() > 0) {
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
  
  public long queryTotal() {
		long total = 0;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Query q = pm.newQuery("select nonceIdKey from " + SessionNonceJdo.class.getName());
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

}
