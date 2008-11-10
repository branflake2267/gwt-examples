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
		
		
		
		return tokenData;
	}
	
	
	private boolean verifySignature(String url, OAuthTokenData token) {
		
		return false;
	}
	
}
