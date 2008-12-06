package com.tribling.gwt.test.oauth.server.oauth;

import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;
import com.tribling.gwt.test.oauth.server.db.Db_Conn;

public class OAuthServer extends Db_Conn {
	
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
		boolean verifySignature = token.verify(url);

		// TODO - get application id 
		int applicationId = getApplicationId(token);
		
		// TODO - request token has never been exchanged for an access token - check nonce
		
		
		// TODO - the request token matches the consumerKey (is this a web site key, or username/email?)
		
		
		// TODO - for example 
		if (verifySignature == false) {
			token.setResult(OAuthTokenData.ERROR);
		} else {
			token.setResult(OAuthTokenData.SUCCESS);
		}
		
		return token;
	}
	
	/**
	 * get the applications id - from consumer key and password
	 * 
	 * @param token
	 * @return
	 */
	private int getApplicationId(OAuthTokenData token) {
	
		String query = "";
		
		
		return 0;
	}
	
	/**
	 * verify that this exact object has not been requested before
	 * 
	 * nonce - 6 chars, and is unique for each request 
	 * 
	 * @param token
	 * @return
	 */
	private boolean verifyNonce(OAuthTokenData token) {
		
		int requesting = token.getRequesting();
		
		String query = "";
		switch (requesting) { // application id check
		case OAuthTokenData.REQUEST_REQUEST_TOKEN:
			query = "";
			break;
		// TODO - add user 
		}
		
		
		
		return false;
	}


	
}
