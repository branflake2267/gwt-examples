package com.tribling.gwt.test.oauth.client;

import com.tribling.gwt.test.oauth.client.oauth.SessionData;

/**
 * manages the login system 
 * 
 * @author branflake2267
 *
 */
public class Manager {

	// store the granted access token and related info in here
	private SessionData session = null;
	
	/**
	 * constructor
	 */
	public Manager() {
		
	}
	
	public SessionData getSession() {
		if (session == null) {
			// TODO - move to login?
			return null;
		}
		
		return session;
	}
	
	private void drawLoginUi() {
		
	}
	
	private void drawForgotPasswordUi() {
		
	}
	
	/**
	 * A. request request token
	 */
	private void request_Request_Token() {
		
	}
	
	/**
	 * B. server replies back with
	 */
	private void request_Request_Token_Response() {
		
	}
	
	/**
	 * C. if B. passes, get users authorization
	 */
	private void getUsersAuthorization() {
		
		drawLoginUi();
	}
	
	/**
	 * C.2 if C doesn't pass error check the credentials
	 *   - ask agian
	 *   - show the errors in processing
	 */
	private void getUsersAuthorization_Reponse() {
		
	}
	
	private void setSessionCookie() {
		// TODO - set the session as a cookie to remember to login agian
	}
	
}
