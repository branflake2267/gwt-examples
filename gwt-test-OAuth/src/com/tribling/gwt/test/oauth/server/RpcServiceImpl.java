package com.tribling.gwt.test.oauth.server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.HTTPRequest;
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
		
		HttpServletRequest request = getThreadLocalRequest();
		String url = request.getRequestURL().toString();
		
		OAuthServer oauth = new OAuthServer();
		return oauth.requestToken(tokenData, url);
	}

	
	
}
