package org.gonevertical.upload.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 */
public interface RpcServiceAsync {
	
	public void getBlobStoreUrl(AsyncCallback<String> callback);
	
}
