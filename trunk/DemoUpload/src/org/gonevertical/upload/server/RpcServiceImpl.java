package org.gonevertical.upload.server;

import org.gonevertical.upload.client.blobs.BlobData;
import org.gonevertical.upload.client.blobs.BlobDataFilter;
import org.gonevertical.upload.client.rpc.RpcService;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); 
	
	/**
	 * init a blobstore url
	 */
	public String getBlobStoreUrl() {
		String url = blobstoreService.createUploadUrl("/upload");
		return url;
	}
	
	/**
	 * get blob info list
	 * 
	 * @param filter
	 * @return
	 */
	public BlobData[] getBlobs(BlobDataFilter filter) {
	  
	  BlobInfoJdo db = new BlobInfoJdo();
	  BlobData[] r = db.getBlobs(filter);
	  
    return r;
	}
	
	public boolean deleteBlob(BlobDataFilter filter) {
	  
	  String blobKey = filter.getBlobKey();
	  
	  if (blobKey == null) {
	    return false;
	  }
	  
	  BlobKey blobKeys = new BlobKey(blobKey);
    blobstoreService.delete(blobKeys);
	  
	  return true;
	}

	private void test() {
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery pq = datastore.prepare(new Query("__BlobInfo__"));
    int ce = pq.countEntities();
    
    System.out.println("countEntities: " + ce);
  }
	
	private void test2() {
	  
	  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  Entity globalStat = datastore.prepare(new Query("__Stat_Total__")).asSingleEntity();
	  Long totalBytes = null;
    Long totalEntities = null;
    if (globalStat != null) {
      totalBytes = (Long) globalStat.getProperty("bytes");
      totalEntities = (Long) globalStat.getProperty("count");
	  }
    
    System.out.println("totalBytes: " + totalBytes + " totalEntities: " + totalEntities);
	}
	
	
}
