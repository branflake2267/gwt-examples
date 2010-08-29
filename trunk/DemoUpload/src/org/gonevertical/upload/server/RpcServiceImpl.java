package org.gonevertical.upload.server;

import org.gonevertical.upload.client.blobs.BlobData;
import org.gonevertical.upload.client.blobs.BlobDataFilter;
import org.gonevertical.upload.client.rpc.RpcService;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
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
	
	
}
