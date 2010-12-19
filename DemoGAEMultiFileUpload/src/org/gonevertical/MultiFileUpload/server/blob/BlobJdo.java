package org.gonevertical.MultiFileUpload.server.blob;

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

import org.gonevertical.MultiFileUpload.client.blobs.BlobDataFilter;
import org.gonevertical.MultiFileUpload.server.ServerPersistence;

import com.google.appengine.api.blobstore.BlobKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class BlobJdo {

  @NotPersistent
  private static final Logger log = Logger.getLogger(BlobJdo.class.getName());

  @NotPersistent 
  private ServerPersistence sp = null;
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private String blob;
  
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
    this.blob = blob;
    this.path = path;
    
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
    return blob;
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
  
  public BlobJdo queryKey(String blob) {
    if (blob == null) {
      return null;
    }
    BlobJdo r = null;
    PersistenceManager pm = sp.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      r = pm.getObjectById(BlobJdo.class, blob);
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    return r;
  }
  
  public void save(String fileName, String filePath, String directorySelected, String virtualPath, BlobKey blobKey) {

    String dir = directorySelected.replaceAll(".*/", "");
    filePath = filePath.replaceAll(".*?" + directorySelected, virtualPath + "/" + dir);
    
    // delete the previous and replace with new upload
    deletePrevious(filePath);
    
    insertUnique(blobKey.getKeyString(), filePath);
   
    System.out.println("BlobJdo.save(): saved blobjdo filepath: " + filePath);
  }

  /**
   * delete the previous database entry and file - b/c a new one is replacing it
   * 
   * @param filePath
   */
  private void deletePrevious(String filePath) {
    if (filePath == null) {
      return;
    }
    
    BlobJdo b = queryBlob(filePath);
    if (b != null) {
      delete(b.getBlobKey());
    }
    
  }

  public String getPath() {
    return path;
  }

  public boolean delete(String blobKey) {
    if (blobKey == null) {
      return false;
    }
    
    // delete file that goes with this
    deleteFile(blobKey);
    
    PersistenceManager pm = sp.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      BlobJdo bj = pm.getObjectById(BlobJdo.class, blobKey);
      pm.deletePersistent(bj);
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
        return false;
      }
      pm.close();
    }
    
    return true;
  }

  private void deleteFile(String blobKey) {
    if (blobKey == null) {
      return;
    }
    
    BlobDataFilter filter = new BlobDataFilter();
    filter.setBlobKey(blobKey);
    
    BlobInfoJdo bij = new BlobInfoJdo(sp);    
    boolean b = bij.deleteBoth(filter);
    
  }
  
}
