package com.tribling.gwt.test.oauth.server;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;
import com.tribling.gwt.test.oauth.client.rpc.RpcService;

public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	/**
	 * clear a eclipse notification with this
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * get request url
	 * @return
	 */
	private String getRequestUrl() {

		HttpServletRequest request = getThreadLocalRequest();
		String host = request.getRemoteHost();
		String path = request.getPathInfo();
		int port = request.getRemotePort();
		
		String url = "";
		if (port == 80) {
			url = "http://" + host + path;
		} else if (port == 443) {
			url = "https://" + host + path;
		} else {
			url = "http://" + host + ":" + port + path;
		}
		
		return url;
	}
	
	/**
	 * get request url path for oauth - minus port
	 * ajax requests come in on different ports, as far as I can tell
	 * maybe its only in hosted mode
	 * 
	 * @return
	 */
	private String getRequestUrlOAuth() {

		HttpServletRequest request = getThreadLocalRequest();
		String host = request.getRemoteHost();
		String path = request.getPathInfo();
		
		String url = "http://" + host + path;
		
		return url;		
	}
	
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
		
		String url = getRequestUrlOAuth();
		
		
		// get the url the client came in on
		HttpServletRequest request = getThreadLocalRequest();
		String url = request.getRequestURL().toString();
		
		OAuthServer oauth = new OAuthServer();
		return oauth.requestToken(url, tokenData);
		return oauth.requestToken(tokenData, url);
	}

	
	
}
