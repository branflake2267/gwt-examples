package org.gonevertical.MultiFileUpload.server.jdo;

import java.util.Collection;
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

import org.gonevertical.MultiFileUpload.server.ServerPersistence;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class BlobJdo {

  @NotPersistent
  private static final Logger log = Logger.getLogger(BlobJdo.class.getName());

  @NotPersistent 
  private ServerPersistence sp = null;
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;
  
  @Persistent
  private String blobKeyStr;
  
  @Persistent
  private String path;
  
  
  public BlobJdo() {
    // don't use this
  }
  
  public BlobJdo(ServerPersistence sp) {
    this.sp = sp;
  }

  public void set(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public void insertUnique(String blob, String path) {
    this.blobKeyStr = blob;
    this.path = path;
    
    // TODO unique, do i have to look something up before insert?
    
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
  
  public BlobJdo queryBlob(String path) {
    String qfilter = "path== '" + path + "'";
    BlobJdo[] r = null;
    PersistenceManager pm = sp.getPersistenceManager();
    try {
      Extent<BlobJdo> e = pm.getExtent(BlobJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      Collection<BlobJdo> c = (Collection<BlobJdo>) q.execute();
      r = new BlobJdo[c.size()];
      if (c.size() > 0) {
        r = new BlobJdo[c.size()];
        c.toArray(r);
      }
      q.closeAll();
    } finally {
      pm.close();
    }
    
    if (r == null || r.length == 0) {
      return null;
    }
    
    BlobJdo ra = null;
    if (r != null && r.length > 0) {
      ra = r[0];
    }
    
    return ra;
  }
  
  public String queryBlobKey(String path) {
    String qfilter = "path== '" + path + "'";
    BlobJdo[] r = null;
    PersistenceManager pm = sp.getPersistenceManager();
    try {
      Extent<BlobJdo> e = pm.getExtent(BlobJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      Collection<BlobJdo> c = (Collection<BlobJdo>) q.execute();
      int size = c.size();
      r = new BlobJdo[c.size()];
      if (c.size() > 0) {
        r = new BlobJdo[c.size()];
        c.toArray(r);
      }
      q.closeAll();
    } finally {
      pm.close();
    }
    
    if (r == null || r.length == 0) {
      return null;
    }
    
    BlobJdo ra = null;
    if (r != null && r.length > 0) {
      ra = r[0];
    }
    
    String blobKey = ra.getBlobKey();
    
    return blobKey;
  }
  
  private String getBlobKey() {
    return blobKeyStr;
  }

  public BlobJdo[] query(String path) {
    String qfilter = "path== '" + path + "'";
    BlobJdo[] r = null;
    PersistenceManager pm = sp.getPersistenceManager();
    try {
      Extent<BlobJdo> e = pm.getExtent(BlobJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      Collection<BlobJdo> c = (Collection<BlobJdo>) q.execute();
      r = new BlobJdo[c.size()];
      if (c.size() > 0) {
        r = new BlobJdo[c.size()];
        c.toArray(r);
      }
      q.closeAll();
    } finally {
      pm.close();
    }
    return r;
  }
  
  public BlobJdo[] query() {
    
    BlobJdo[] r = null;
    PersistenceManager pm = sp.getPersistenceManager();
    try {
      Extent<BlobJdo> e = pm.getExtent(BlobJdo.class, true);
      Query q = pm.newQuery(e, null);
      Collection<BlobJdo> c = (Collection<BlobJdo>) q.execute();
      int size = c.size();
      r = new BlobJdo[c.size()];
      if (c.size() > 0) {
        r = new BlobJdo[c.size()];
        c.toArray(r);
      }
      q.closeAll();
    } finally {
      pm.close();
    }
    return r;
  }
  
  public BlobJdo queryKey(String blobKey) {
    String qfilter = "blobKeyStr== '" + blobKey + "'";
    BlobJdo[] r = null;
    PersistenceManager pm = sp.getPersistenceManager();
    try {
      Extent<BlobJdo> e = pm.getExtent(BlobJdo.class, true);
      Query q = pm.newQuery(e, qfilter);
      Collection<BlobJdo> c = (Collection<BlobJdo>) q.execute();
      r = new BlobJdo[c.size()];
      if (c.size() > 0) {
        r = new BlobJdo[c.size()];
        c.toArray(r);
      }
      q.closeAll();
    } finally {
      pm.close();
    }
    
    if (r == null || r.length == 0) {
      return null;
    }
    
    BlobJdo ra = null;
    if (r != null && r.length > 0) {
      ra = r[0];
    }
    
    return ra;
  }
  
 
  public void save(String fileName, String filePath, String directorySelected, String virtualPath, BlobKey blobKey) {
   
    String dir = directorySelected.replaceAll(".*/", "");
    
    filePath = filePath.replaceAll(".*?" + directorySelected, virtualPath + "/" + dir);
    
    insertUnique(blobKey.getKeyString(), filePath);
   
    System.out.println("saved blobjdo filepath: " + filePath);
  }

  public String getPath() {
    return path;
  }
  
}
