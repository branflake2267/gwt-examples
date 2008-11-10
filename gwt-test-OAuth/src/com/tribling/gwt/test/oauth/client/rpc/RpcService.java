package com.tribling.gwt.test.oauth.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;

public interface RpcService extends RemoteService {

	/**
	 * example rpc request
	 * 
	 * @param s
	 * @return
	 */
	public String testMethod(String s);

	/**
	 * A. grant request token?
	 * 
	 * @param tokenData
	 * @return
	 */
	public OAuthTokenData requestToken(OAuthTokenData tokenData);
}
