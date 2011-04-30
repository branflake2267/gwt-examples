package org.gonevertical.democanvas.server;

import org.gonevertical.democanvas.client.BlobDataFilter;
import org.gonevertical.democanvas.client.rpc.RpcService;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  public String getBlobStoreUrl(BlobDataFilter filter) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    String url = blobstoreService.createUploadUrl("/upload");
    return url;
  }

  
  
}
