package org.gonevertical.MultiFileUpload.server;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gonevertical.MultiFileUpload.client.BlobData;
import org.gonevertical.MultiFileUpload.client.BlobDataFilter;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class BlobInfoJdo {
  
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  
  private ServerPersistence sp;

  public BlobInfoJdo(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public String getBlobStoreUrl() {
    String url = blobstoreService.createUploadUrl("/upload");
    return url;
  }
  
  public BlobData[] getBlobs(BlobDataFilter filter) {
    
    Entity[] entities = null;
    try {
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      PreparedQuery pq = datastore.prepare(new Query("__BlobInfo__"));
      List<Entity> entList = pq.asList(FetchOptions.Builder.withLimit((int) filter.getRangeFinish()).offset((int) filter.getRangeStart()));
      
      entities = new Entity[entList.size()];
      entList.toArray(entities);
      
    } finally {
      
    }
    
    BlobData[] blobData = convert(entities);
    
    return blobData;
  }

  private BlobData[] convert(Entity[] es) {
    if (es == null || es.length == 0) {
      return null;
    }
    
    BlobData[] b = new BlobData[es.length];
    for (int i=0; i < es.length; i++) {
      
      Map<String, Object> p = es[i].getProperties();
      
      long id = es[i].getKey().getId();
      Key key = es[i].getKey();
      String ct = (String) es[i].getProperty("content_type");
      String fn = (String) es[i].getProperty("filename");
      Long size = (Long) es[i].getProperty("size");
      Date creation = (Date) es[i].getProperty("creation");
  
      b[i] = new BlobData();
      b[i].setKey(key.getName());
      b[i].setContentType(ct);
      b[i].setFilename(fn);
      b[i].setSize(size);
      b[i].setCreation(creation);
      
    }
    
    return b;
  }

  public boolean delete(BlobDataFilter filter) {
    
    BlobKey blobKeys = new BlobKey(filter.getBlobKey());
    blobstoreService.delete(blobKeys);
    
    return true;
  }

}
