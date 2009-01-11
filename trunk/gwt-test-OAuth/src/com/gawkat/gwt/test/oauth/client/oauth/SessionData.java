package com.gawkat.gwt.test.oauth.client.oauth;

/**
 * store the session data to pass around to the widgets that need access
 * 
 * @author branflake2267
 *
 */
public class SessionData {

	public OAuthTokenData token = new OAuthTokenData();
	
	/**
	 * constructor
	 */
	public SessionData() {
	}
	
	public void logout() {
		token = null;
	}
}
