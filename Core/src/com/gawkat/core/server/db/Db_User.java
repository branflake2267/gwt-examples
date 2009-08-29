package com.gawkat.core.server.db;

import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.db.oauth.OAuthServer;
import com.gawkat.core.server.jdo.data.SessionAccessTokenJdo;
import com.gawkat.core.server.jdo.data.ThingJdo;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

/**
 * database methods for user
 * 
 * @author branflake2267
 *
 */
public class Db_User {

  private ServerPersistence sp = null;
  
  /**
   * constructor
   */
  public Db_User(ServerPersistence sp) {
    this.sp = sp;
  }
  
  private boolean verifyUserData(UserData userData) {
    boolean pass = false;
    if (userData.verifySignature() == true) {
      pass = true;
    }
    return pass;
  }
  
  private UserData getError(int error) {
    UserData ud = new UserData();
    ud.error = error;
    return ud;
  }
  
  /**
   * crate user
   * 
   * TODO - this could be vulnerable?
   * 
   * @param userData
   * @param url - used for signing
   * @return
   */
  public UserData createUser(UserData userData) {
    
    if (verifyUserData(userData) == false) {
      UserData udErr = getError(UserData.SIGNATURE_ERROR);
      return udErr;
    }
    
    
    // TODO - deal with this in a thing hash table
    boolean acceptTerms = userData.acceptTerms;
    
    
    long thingTypeId = ThingTypeJdo.TYPE_USER;
    String key = userData.consumerKey;
    String secret = userData.consumerSecret;
    
    ThingJdo user = new ThingJdo();
    user.insert(thingTypeId, key, secret);
    
    Long newUserId = user.getId();
    
    // TODO - error???
    if (newUserId == 0) {
      // make an error?
    }
    
    // create user oAuthToken access token
    UserData newUd = new UserData();
    newUd.accessToken = getUserAccessToken(userData); 
    newUd.sign();
    
    return newUd;
  }
  
  /**
   * does the user exist in the database?
   * 
   * @param userData
   * @return
   */
  public UserData getUserExist(UserData userData) {
    
    // did everything pass?
    if (verifyUserData(userData) == false) {
      UserData udErr = getError(UserData.SIGNATURE_ERROR);
      return udErr;
    }
    
    String userConsumerKey = userData.consumerKey;
    String userConsumerSecret = userData.consumerSecret;
    
    if (userConsumerKey == null) {
      userConsumerKey = "";
    }
    if (userConsumerSecret == null) {
      userConsumerSecret = "";
    }
    
    // TODO - incase something goes wrong
    if (userConsumerKey.length() == 0) {
      System.out.println("isUserNameExist(); No consumerKey.");
      return null;
    }
    if (userConsumerSecret.length() == 0) {
      System.out.println("isUserNameExist(); No consumerSecret.");
      return null;
    }
    
    // TODO - I think I need to run more checking later

    // get userId
    ThingJdo[] users = ThingJdo.query((long)ThingTypeJdo.TYPE_USER, userData.consumerKey);
    long userId = 0;
    if (users != null && users.length > 0) {
      userId = users[0].getId();
    }
    
    boolean exists = false;
    if (userId > 0) {
      exists = true;
    }
    
    UserData ud = new UserData();
    if (exists == true) {
      ud.error = UserData.KEY_EXISTS;
    }
    
    return ud;
  }
  
  /**
   * 
   * TODO - need email utility
   * 
   * @param userData
   * @return
   */
  public UserData forgotPassword(UserData userData) {
    // TODO
    return null;
  }
  
  /**
   * for a new user (ONLY), create an access token right off the bat to auto login
   * TODO - make it an option
   *   People may want a verification to happen before login. 
   *   But for now, I will auto sign the newly created user in, and send back that access token for that.
   * 
   * @param userId
   * @return
   */
  private OAuthTokenData getUserAccessToken(UserData userData) {
    
    String url = sp.getRequestUrlOAuth();
    
    // create a userAccessToken
    // which is used to exchange the appAccessToken to userAccessToken
    OAuthTokenData appAccessToken = userData.accessToken; // this is the app token shipped in
    appAccessToken.setConsumerKey(userData.consumerKey);
    appAccessToken.sign(url, userData.consumerSecret);
    
    OAuthServer oAuthServer = new OAuthServer(sp);
    OAuthTokenData userAccessToken = oAuthServer.getUserAccessToken(appAccessToken);
    
    return userAccessToken;
  }
  
  /**
   * get the user from the session
   *   this looks up the session token and secret created for this session
   *   
   * @param accessToken
   * @return
   */
  public ThingData getUser(OAuthTokenData accessToken) {
    
    String ct = accessToken.getAccessToken_key(); // at this point its a consumer token, and not the hash
    String cs = accessToken.getAccessToken_secret();
    
    SessionAccessTokenJdo s = new SessionAccessTokenJdo();
    ThingData t = s.getThingData(ct, cs);
    
    // the id has to be a user
    if (t.thingTypeId != 2) {
      t = null;
    }
    
    return t;
  }
  
  
  
  
  
  
  
  
  
  
}
