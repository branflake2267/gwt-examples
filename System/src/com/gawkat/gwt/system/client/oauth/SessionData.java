package com.gawkat.gwt.system.client.oauth;

/**
 * store the session data to pass around to the widgets that need access
 * 
 * @author branflake2267
 *
 */
public class SessionData {

	public OAuthTokenData token = null;
	
	/**
	 * constructor
	 */
	public SessionData() {
	}
	
	public void setToken(OAuthTokenData token) {
	  this.token = token;
	}
	
	public OAuthTokenData getToken() {
	  return this.token;
	}
	
	public void logout() {
		token = null;
	}
}
