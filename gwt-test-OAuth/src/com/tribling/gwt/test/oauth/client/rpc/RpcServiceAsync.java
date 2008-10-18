package com.tribling.gwt.test.oauth.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RpcServiceAsync {

	/**
	 * example rpc request with the callback
	 * 
	 * @param s
	 * @param callback
	 */
	public void testMethod(String s, AsyncCallback<String> callback);
	
}
