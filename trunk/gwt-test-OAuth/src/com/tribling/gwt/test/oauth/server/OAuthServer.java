package com.tribling.gwt.test.oauth.server;

import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;

public class OAuthServer {
	
	/**
	 * constructor
	 */
	public OAuthServer() {
		
	}
	
	/**
	 * A. -> (B. grant request token): Can we go to the next step?
	 * grant request token?
	 * 
	 * @param tokenData
	 * @return
	 */
	public OAuthTokenData requestToken(OAuthTokenData tokenData) {
		
		// TODO - the request signature has been successfully verified
		// TODO - request token has never been exchanged for an access token
		// TODO - the request token matches the consumerKey (is this a web site key, or username/email?)
		
		return tokenData;
	}
	
	
	private boolean verifySignature(String url, OAuthTokenData token) {
		
		return false;
	}
	
}
