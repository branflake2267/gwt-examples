package com.tribling.gwt.test.oauth.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;
import com.tribling.gwt.test.oauth.client.rpc.RpcService;

public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	/**
	 * clear a eclipse notification with this
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * test method for rpc
	 */
	public String testMethod(String s) {
		s += " was modified on the server.";
		return s;
	}

	/**
	 * A. ->(B.?) grant request token?
	 */
	public OAuthTokenData requestToken(OAuthTokenData tokenData) {
		OAuthServer oauth = new OAuthServer();
		return oauth.requestToken(tokenData);
	}

	
	
}
