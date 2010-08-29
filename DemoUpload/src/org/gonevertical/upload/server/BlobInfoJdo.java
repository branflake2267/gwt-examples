package org.gonevertical.upload.server;

import java.util.Collection;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gonevertical.upload.client.blobs.BlobData;
import org.gonevertical.upload.client.blobs.BlobDataFilter;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobKey;

public class BlobInfoJdo {

  private PersistenceManager pm = PMF.get().getPersistenceManager();
  
  public BlobData[] getBlobs(BlobDataFilter filter) {
    
    BlobInfo[] bi = null;
    try {
      Query q = pm.newQuery("select from __BlobInfo__");
      //q.setFilter(qfilter);
      //q.setRange(filter.getRangeStart(), filter.getRangeFinish());
      q.execute();
      
      Collection<BlobInfo> c = (Collection<BlobInfo>) q.execute();
    
      bi = new BlobInfo[c.size()];
      if (c.size() > 0) {
        bi = new BlobInfo[c.size()];
        c.toArray(bi);
      }
      
      q.closeAll();
    } finally {
      pm.close();
    }
    
    BlobData[] blobData = convert(bi);
    
    return blobData;
  }

  private BlobData[] convert(BlobInfo[] bi) {
    if (bi == null || bi.length == 0) {
      return null;
    }
    
    BlobData[] b = new BlobData[bi.length];
    for (int i=0; i < bi.length; i++) {
      
      BlobKey key = bi[i].getBlobKey();
      String ct = bi[i].getContentType();
      String fn = bi[i].getFilename();
      long size = bi[i].getSize();
      Date creation = bi[i].getCreation();
  
      b[i] = new BlobData();
      b[i].setKey(key.getKeyString());
      b[i].setContentType(ct);
      b[i].setFilename(fn);
      b[i].setSize(size);
      b[i].setCreation(creation);
      
    }
    
    return b;
  }

  
  
}
