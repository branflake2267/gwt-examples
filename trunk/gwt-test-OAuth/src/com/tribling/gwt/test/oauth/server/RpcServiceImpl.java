package com.tribling.gwt.test.oauth.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;
import com.tribling.gwt.test.oauth.client.rpc.RpcService;
import com.tribling.gwt.test.oauth.server.oauth.OAuthServer;

public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	/**
	 * clear a eclipse notification with this
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * get request url
	 * 
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
	 * 
	 * ajax requests come in on different ports, as far as I can tell
	 * maybe its only in hosted mode
	 * 
	 * @return
	 */
	private String getRequestUrlOAuth() {

		HttpServletRequest request = getThreadLocalRequest();
		String host = request.getRemoteHost();
		String path = request.getPathInfo();
		
		// take off the servlet context path
		String re = "(.*/)";
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(path);
		boolean found = m.find();
		String newPath = "";
		if (found == true) {
			newPath = m.group(1);
		}
		
		// work around for hosted mode
		if (host.equals("127.0.0.1")) {
			host = "localhost";
		}
		
		String url = "http://" + host + newPath;
		
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
		OAuthServer oauth = new OAuthServer();
		return oauth.requestToken(tokenData, url);
	}

	
	
}
