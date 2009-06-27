package com.gawkat.core.server.db.oauth;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.db.Db_Conn;

public class OAuthServer extends Db_Conn {

  final private static int APPLICATION = 1;
  final private static int USER = 2;
  
  /**
   * constructor
   */
  public OAuthServer() {
  }

  /**
   * A. -> (B. grant request token): Can we go to the next step? Service
   * Provider (this), can grant request token?
   * 
   * NOTE: using rpc, I am only using the app base url, to sign, and no port
   * 
   * @param token
   * @return
   */
  public OAuthTokenData requestToken(OAuthTokenData token, String url) {

    debug("requestToken: token: " + token.toString() + " url: " + url);

    // get the application data according to the consumerKey Given, then lets
    // see if it matches up
    ApplicationData appData = getApplicationId(token);

    // verify the signed signature from the client matches the local
    boolean verifySignature = token.verify(url, appData.consumerSecret);

    // check nonce
    boolean verifyNonce = verifyNonceHasntBeenUsed(token, APPLICATION, appData.applicationId);

    // prepare for transport back
    OAuthTokenData returnToken = new OAuthTokenData();

    // examine if we can go to the next step
    if (appData.applicationId == 0 | verifySignature == false | verifyNonce == false) {
      returnToken.setResult(OAuthTokenData.ERROR);
    } else {
      returnToken.setResult(OAuthTokenData.SUCCESS);
    }

    // on success - grant request token access
    AccessTokenData at = new AccessTokenData();
    if (returnToken.getResult() == OAuthTokenData.SUCCESS) {
      at = setAccessToken_application(appData.applicationId);
      returnToken.setAccessToken(at.accessToken, at.accessTokenSecret);
    }

    // set nonce, so it can't be used again
    setNonce(token, url, APPLICATION, appData.applicationId);

    debug("requestToken: url: " + url + " secret: " + at.accessTokenSecret);

    try {
      returnToken.sign(url, at.accessTokenSecret);
    } catch (Exception e) {
      debug("requestToken: ****** ERROR SIGNING");
      e.printStackTrace();
    }

    // transport back to client
    return returnToken;
  }

  /**
   * get user Access Token
   * 
   * TODO - I am going to have to come back and rewrite this later. This isn't
   * correct yet. Need more awakeness. :)
   * 
   * 
   * @param appAccessToken  - apps access token with users key and secret transfer app access
   *          to user access
   * @param url
   * @return
   */
  public OAuthTokenData getUserAccessToken(OAuthTokenData appAccessToken, String url) {

    // debug
    debug("getAccessToken: token: " + appAccessToken.toString() + " url: " + url);

    // get userData to verify agianst - this will also tell if the user exists
    UserData userData = getUserData(appAccessToken);

    // verify the signed signature from the client matches the local
    // TODO - should I be verifying with consumerSecret?
    boolean verifySignature = appAccessToken.verify(url, userData.consumerSecret);

    // check nonce
    boolean verifyNonce = verifyNonceHasntBeenUsed(appAccessToken, USER, userData.userId);

    // verify application's access, then transfer it to the user if all passes
    int accessId = getAccessId(appAccessToken);

    // examine if we can go to the next step
    if (accessId == 0 | userData.userId == 0 | userData.error > 0 | verifySignature == false | verifyNonce == false) {
      appAccessToken.setResult(OAuthTokenData.ERROR);
    } else {
      appAccessToken.setResult(OAuthTokenData.SUCCESS);
    }

    // change access token session to user
    if (appAccessToken.getResult() == OAuthTokenData.SUCCESS) {
      setAccessToken_user(accessId, userData.userId);
    } else {
      // TODO - what to do, what to do?
    }

    // set nonce, so it can't be used again.
    setNonce(appAccessToken, url, USER, userData.userId);

    // sign the token for transport back
    // TODO should I be signing with consumer secret?
    try {
      appAccessToken.sign(url, userData.consumerSecret);
    } catch (Exception e) {
      debug("requestToken: ****** ERROR SIGNING");
      e.printStackTrace();
    }

    // if we make it this far, it becomes userAccessToken
    // this is just for looks
    OAuthTokenData userAccessToken = appAccessToken;

    // transport back to client
    return userAccessToken;
  }

  /**
   * find the user from consumerKey and consumerSecret also finds if the user
   * actually exists will use it to verify the signing
   * 
   * @param token
   * @return
   */
  private UserData getUserData(OAuthTokenData token) {

    String consumerKey = escapeForSql(token.getConsumerKey());

    // find user
    String sql = "SELECT ThingId, Secret FROM `thing` " + 
          "WHERE " +
          "(ThingTypeId='2') AND " +
          "(`Key`='" + consumerKey + "');"; 
    
    int userId = 0;
    String consumerSecret = null;
    try {
      Connection conn = this.getDbConnRead();
      Statement select = conn.createStatement();
      ResultSet result = select.executeQuery(sql);
      while (result.next()) {
        userId = result.getInt(1);
        consumerSecret = result.getString(2);
      }
      select.close();
      result.close();
      conn.close();
    } catch (SQLException e) {
      System.err.println("Mysql Statement Error: " + sql);
      e.printStackTrace();
    }

    // no user Found - make an error
    if (userId == 0) {
      UserData udError = new UserData();
      udError.error = UserData.ERR_NO_USER;
      return udError;
    }

    // found a user
    UserData ud = new UserData();
    ud.consumerKey = token.getConsumerKey();
    ud.consumerSecret = consumerSecret;
    ud.userId = userId;

    return ud;
  }

  /**
   * get the applications id - from app consumer key
   * 
   * @param token
   * @return
   */
  private ApplicationData getApplicationId(OAuthTokenData token) {

    if (token == null) {
      System.out.println("No tokenData exists. ERROR: getApplicationId()");
    }

    String ck = escapeForSql(token.getConsumerKey());

    String sql = "SELECT ThingId, `Key`, Secret " +
    		"FROM `thing` " +
    		"WHERE " +
    		"(ThingTypeId='1') AND " +
    		"(`Key`='" + ck + "')";

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
   * verify this nonce has not been used before nonce - 30 chars, and is unique
   * for each request
   * 
   * @param token - token sent from client
   * @param thingTypeid 
   * @param thingId 
   * @return
   */
  private boolean verifyNonceHasntBeenUsed(OAuthTokenData token, int thingTypeId, int thingId) {

    String whereQuery = "(ThingTypeID='" + thingTypeId + "') AND (ThingId='" + thingId + "')";
   
    if (whereQuery.length() == 0) {
      System.out.println("ERROR: verifyNonce(), for some reason there is no appId or userId");
      return false;
    }

    // NOTE: Nonce is case sensitive in sql table
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
   * @param thingTypeId
   * @param thingId
   */
  private void setNonce(OAuthTokenData token, String url, int thingTypeId, int thingId) {

    // TODO - this seems like there could be collissions possibly with Nonce?

    String sql = "INSERT INTO session_nonce SET " + 
      "Url='" + escapeForSql(url) + "', " + 
      "ThingTypeId='" + thingTypeId + "', " + 
      "ThingId='" + thingId + "', " + 
      "Nonce='" + escapeForSql(token.getNonce()) + "', " + 
      "DateCreated=NOW();";

    System.out.println("setNonce query: " + sql);
    setQuery(sql);
  }

  /**
   * set a unique access token for the session for the application
   * 
   * @param applicationId
   * @return
   */
  private AccessTokenData setAccessToken_application(int applicationId) {

    // TODO - on getAccessId - do I get applicationId?
    
    String accessKey = escapeForSql(getAccessKey());
    String accessSecret = escapeForSql(getAccessSecret());

    String sql = "INSERT INTO session_accesstoken SET " +
      "ThingTypeId='1', " +
      "ThingId='" + applicationId + "', " +
      "AccessToken='" + accessKey + "'," + 
      "AccessTokenSecret='" + accessSecret + "', " + 
      "DateCreated=NOW();";

    setQuery(sql);

    // transport back
    AccessTokenData at = new AccessTokenData();
    at.accessToken = accessKey;
    at.accessTokenSecret = accessSecret;
    return at;
  }

  /**
   * get the AccessId - verify there is a session token
   * 
   * TODO For user??
   * 
   * @param appAccessToken
   * @return
   */
  private int getAccessId(OAuthTokenData appAccessToken) {

    // TODO - look for applicationID ??????
    
    String appConsumerKey = escapeForSql(appAccessToken.getAccessToken_key());
    String appConsumerSecret = escapeForSql(appAccessToken.getAccessToken_secret());

    String sql = "SELECT Id " + 
      "FROM session_accesstoken " + 
      "WHERE " + 
      // TODO ??? "(ThingTypeId='2') AND " +
      "(AccessToken='" + appConsumerKey + "') AND " + 
      "(AccessTokenSecret='" + appConsumerSecret + "');";

    int accessId = getQueryInt(sql);

    return accessId;
  }

  /**
   * set the access token for the session, but then then transfer it to this
   * user here
   * 
   * @param accessId
   * @param userId
   * @return
   */
  private boolean setAccessToken_user(int accessId, int userId) {

    String sql = "UPDATE session_accesstoken SET " +
      "ThingId='" + userId + "', " + 
      "DateUpdated=NOW() " + 
      "WHERE (Id='" + accessId + "') AND (ThingTypeId='2');";

    int id = setQuery(sql);

    if (id > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * get accessToken (key) Creates a unique AccessKey for the session
   * 
   * The goal here is to have a unique Key and Secret for the Session which the
   * App or User can own for the session. Everything builds up to this.
   * 
   * @return
   */
  private String getAccessKey() {
    UUID uId = UUID.randomUUID();
    return uId.toString();
  }

  /**
   * get accesstoken (secret) Creates a unique Secret for teh session
   * 
   * The goal here is to have a unique Key and Secret for the Session which the
   * App or User can own for the session. Everything builds up to this.
   * 
   * @return
   */
  private String getAccessSecret() {

    // TODO - is this the length i want?
    int nounceLength = 15;
    String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
    String s = "";
    for (int i = 0; i < nounceLength; i++) {
      int rnum = (int) Math.floor(Math.random() * chars.length());
      s += chars.substring(rnum, rnum + 1);
    }
    return s;

  }

  // TODO
  private boolean verifyAccessToken(int applicationId, String accessToken, String accessTokenSecret) {
    return false;
  }

}
