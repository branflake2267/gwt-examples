package org.gonevertical.gaeloadtest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 */
public interface RpcServiceAsync {
	
	public void saveTest(AsyncCallback<Boolean> callback); 
	
}
