package com.tribling.gwt.test.oauth.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;

public interface RpcServiceAsync {

	/**
	 * example rpc request with the callback
	 * 
	 * @param s
	 * @param callback
	 */
	public void testMethod(String s, AsyncCallback<String> callback);
	
	/**
	 * A. grant request token?
	 * 
	 * @param tokenData
	 * @param callback
	 */
	public void requestToken(OAuthTokenData tokenData, AsyncCallback<OAuthTokenData> callback);
}
