package org.gonevertical.upload.server;

import org.gonevertical.upload.client.RpcService;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); 
	
	public String getBlobStoreUrl() {
		
		String url = blobstoreService.createUploadUrl("/upload");
		
		return url;
	}
	
}
