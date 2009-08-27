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
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.gawkat.core.server.jdo.PMF;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingPermissionJdo {

  // can't see or write a thing
  public static final int PNO_ACCESS = 0;

  // read only
  public static final int PREADONLY = 1;

  // read / write
  public static final int PREADWRITE = 2;
  
  // write only
  public static final int PWRITEONLY = 3;
  
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long permissionId;
  
  // thing where talking about
  @Persistent
  private Long thingId;
  
  // has permission to another thing
  @Persistent
  private Long hasPermissionToThingId;
  
  // what type of permission - CONSTANT
  @Persistent
  private int access = PNO_ACCESS;

  /**
   * query permission
   * 
   * @param thingId
   * @param hasPermissionToThingId
   * @return
   */
  public static ThingPermissionJdo query(long thingId, long hasPermissionToThingId) {

    ArrayList<ThingPermissionJdo> aT = new ArrayList<ThingPermissionJdo>();

    String qfilter = "thingId==" + thingId + " && hasPermissionToThingId==" + hasPermissionToThingId + " ";

    PersistenceManager pm = PMF.get().getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Extent<ThingPermissionJdo> e = pm.getExtent(ThingPermissionJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      q.execute();

      Collection<ThingPermissionJdo> c = (Collection<ThingPermissionJdo>) q.execute();
      Iterator<ThingPermissionJdo> iter = c.iterator();
      while (iter.hasNext()) {
        ThingPermissionJdo t = (ThingPermissionJdo) iter.next();
        aT.add(t);
      }

      tx.commit();
      q.closeAll();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }

    ThingPermissionJdo[] r = null;
    if (aT.size() > 0) {
      r = new ThingPermissionJdo[aT.size()];
      aT.toArray(r);
    }
    
    ThingPermissionJdo rr = null;
    if (r != null && r.length > 0) {
      rr = r[0];
    }
    
    return rr;
  }

  /**
   * insert permission
   * 
   * @param thingId
   * @param hasPermissionToThingId
   * @param access
   */
  public void insert(long thingId, long hasPermissionToThingId, int access) {
    this.thingId = thingId;
    this.hasPermissionToThingId = hasPermissionToThingId;
    this.access = access;

    // do not insert duplicate
    ThingPermissionJdo tt = ThingPermissionJdo.query(thingId, hasPermissionToThingId);
    if (tt != null) {
      return;
    }

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
 
}
