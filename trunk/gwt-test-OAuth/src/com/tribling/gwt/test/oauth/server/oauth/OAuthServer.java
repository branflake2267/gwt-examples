package com.tribling.gwt.test.oauth.server.oauth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	 * Service Provider (this), can grant request token?
	 * 
	 * NOTE: using rpc, I am only using the app base url, to sign, and no port
	 * 
	 * @param token
	 * @return
	 */
	public OAuthTokenData requestToken(OAuthTokenData token, String url) {
		
		// debug
		//System.out.println("request url: " + url);
		
	
		// get the application data according to the consumerKey Given, then lets see if it matches up
		ApplicationData appData = getApplicationId(token);
		
		// verify the signed signature from the client matches the local
		boolean verifySignature = token.verify(url, appData.consumerSecret);
		
		// Examine if we can go to the next step
		if (verifySignature == false) {
			token.setResult(OAuthTokenData.ERROR);
		} else {
			token.setResult(OAuthTokenData.SUCCESS);
		}
		
		// TODO - check nounce???
		// TODO - get access token ???
		
		return token;
	}
	
	/**
	 * get the applications id - from consumer key and password
	 * 
	 * @param token
	 * @return
	 */
	private ApplicationData getApplicationId(OAuthTokenData token) {
	
		if (token == null) {
			System.out.println("No tokenData exists. ERROR: getApplicationId()");
		}
		
		String ck = token.getConsumerKey();
		
		String sql = "SELECT ApplicationId, ConsumerKey, ConsumerSecret " +
				"FROM Application " +
				"WHERE (ConsumerKey='" + ck + "')";
		
		// debug
		System.out.println("sql query: " + sql);
		
		ApplicationData appData = new ApplicationData();
		try {
			Connection conn = this.getDbConnRead();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(sql);
			while (result.next()) {
				appData.applicationId = result.getInt(1);
				appData.consumerKey = result.getString(2);
				appData.consumerSecret = result.getString(3);
			}
			select.close();
			result.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Mysql Statement Error: " + sql);
			e.printStackTrace();
		}
		
		return appData;
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
