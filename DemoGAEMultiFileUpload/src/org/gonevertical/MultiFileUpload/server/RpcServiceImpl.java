package org.gonevertical.MultiFileUpload.server;

import org.gonevertical.MultiFileUpload.client.blobs.BlobData;
import org.gonevertical.MultiFileUpload.client.blobs.BlobDataFilter;
import org.gonevertical.MultiFileUpload.client.rpc.RpcService;
import org.gonevertical.MultiFileUpload.server.jdo.BlobJdo;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  /**
   * store server persistence items
   */
  private ServerPersistence sp = new ServerPersistence();

  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); 
  
  public String getBlobStoreUrl() {
    sp.start(getThreadLocalRequest());
    BlobInfoJdo bij = new BlobInfoJdo(sp);
    String url = bij.getBlobStoreUrl();
    sp.end();
    return url;
  }
  
  /**
   * get blob info list
   * 
   * @param filter
   * @return
   */
  public BlobData[] getBlobs(BlobDataFilter filter) {
    
    //test();
    
    BlobData[] r = new BlobData[0];
    try {
      BlobInfoJdo db = new BlobInfoJdo(sp);
      r = db.getBlobs(filter);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
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
    
    BlobJdo bj = new BlobJdo(sp);
    
    BlobJdo[] test = bj.query();
    
    for (int i=0; i < test.length; i++) {
      System.out.println(test[i].getPath());
    }
  }
  
}
