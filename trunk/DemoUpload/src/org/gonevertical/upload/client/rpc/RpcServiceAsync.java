package org.gonevertical.upload.client.rpc;

import org.gonevertical.upload.client.blobs.BlobData;
import org.gonevertical.upload.client.blobs.BlobDataFilter;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 */
public interface RpcServiceAsync {
	
	public void getBlobStoreUrl(AsyncCallback<String> callback);
	
	public void getBlobs(BlobDataFilter filter, AsyncCallback<BlobData[]> callback);
	 
	public void deleteBlob(BlobDataFilter filter, AsyncCallback<Boolean> callback);
	
}
