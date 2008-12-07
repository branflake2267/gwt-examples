package com.tribling.gwt.test.oauth.server.oauth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
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

		// check nonce 
		boolean verifyNonce = verifyNonceHasntBeenUsed(token, appData.applicationId, 0);

		// prepare for transport back
		OAuthTokenData returnToken = new OAuthTokenData();
		
		// Examine if we can go to the next step
		if (verifySignature == false | verifyNonce == false) {
			returnToken.setResult(OAuthTokenData.ERROR);
		} else {
			returnToken.setResult(OAuthTokenData.SUCCESS);
		}
		
		// TODO - on success
		if (returnToken.getResult() == OAuthTokenData.SUCCESS) {
			// TODO - create/produce access token and token secret and send it back
		}
		
		// set nonce, so it can't be used again
		setNonce(token, url, appData.applicationId, 0);
		
		return returnToken;
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
	 * verify this nonce has not been used before
	 * nonce - 30 chars, and is unique for each request 
	 * 
	 * @param token - token sent from client
	 * @param appId - app that generated the request
	 * @param userId - user that generated the request
	 * @return
	 */
	private boolean verifyNonceHasntBeenUsed(OAuthTokenData token, int appId, int userId) {
		
		String whereQuery = "";
		if (appId > 0) {
			whereQuery = "(ApplicationId='" + appId + "')";
		} else if (userId > 0) {
			whereQuery = "(UserId='" + appId + "')";
		}
		
		if (whereQuery.length() == 0) {
			System.out.println("ERROR: verifyNonce(), for some reason there is no appId or userId");
			return false;
		}

		//NOTE: Nonce is case sensitive in sql table
		String sql = "SELECT NonceId " +
				"FROM session_nonce " +
				"WHERE " + whereQuery + " AND " +
				"(Nonce='" + token.getNonce() + "');"; 
		
		// debug
		System.out.println("Query Nonce:" + sql);
		
		int i = getQueryInt(sql);
		boolean b = true;
		if (i > 0) {
			b = false;
		}
		return b;
	}

	/**
	 * record nonce that has been used
	 * 
	 * @param token
	 * @param url
	 * @param appId
	 * @param userId
	 */
	private void setNonce(OAuthTokenData token, String url, int appId, int userId) {
		String sql = "INSERT INTO session_nonce SET " +
				"Url='" + escapeForSql(url) + "', " + 
				"ApplicationId='" + escapeForSql(appId) + "', " +
				"UserId='" + escapeForSql(userId) + "', " +
				"Nonce='" + escapeForSql(token.getNonce()) + "', " +
				"DateCreated=UNIX_TIMESTAMP(NOW());";
		
		System.out.println("setNonce query: " + sql);
		setQuery(sql);
	}

	
}
