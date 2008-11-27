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
	 * NOTE: using rpc, I am only using the app base url, to sign, and no port
	 * 
	 * @param token
	 * @return
	 */
	public OAuthTokenData requestToken(OAuthTokenData token, String url) {
		
		// debug
		//System.out.println("request url: " + url);
		
		// Test if the request signature has been successfully verified
		boolean verify = token.verify(url);
		if (verify == false) {
			token.setResult(OAuthTokenData.ERROR);
		} else {
			token.setResult(OAuthTokenData.SUCCESS);
		}
		
		// TODO - request token has never been exchanged for an access token
		// TODO - the request token matches the consumerKey (is this a web site key, or username/email?)
		
		return token;
	}
	
	

	
}
