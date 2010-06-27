package org.gonevertical.core.server.db;

import java.util.logging.Logger;

import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.login.ChangePasswordData;
import org.gonevertical.core.client.ui.login.UserData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.db.oauth.OAuthServer;
import org.gonevertical.core.server.jdo.data.SessionAccessTokenJdo;
import org.gonevertical.core.server.jdo.data.ThingJdo;
import org.gonevertical.core.server.jdo.data.ThingTypeJdo;

/**
 * database methods for user
 * 
 * @author branflake2267
 *
 */
public class Db_User {

	private static final Logger log = Logger.getLogger(Db_User.class.getName());
	
  private ServerPersistence sp = null;
	private Db_ThingStuff dbTs;
  
  /**
   * constructor
   */
  public Db_User(ServerPersistence sp) {
    this.sp = sp;
    dbTs = new Db_ThingStuff(sp);
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
    ud.setError(error);
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
    
    long thingTypeId = ThingTypeJdo.TYPE_USER;
    String key = userData.getConsumreKey();
    String secret = userData.getConsumerSecret();
    
    ThingJdo user = new ThingJdo(sp);
    user.insertUnique(thingTypeId, key, secret);
    
    Long newUserId = user.getThingId();
    
    // TODO - error???
    if (newUserId == 0) {
      // make an error?
    }
    
    saveAcceptTerms(newUserId, userData);
    
    // create user oAuthToken access token
    UserData newUd = new UserData();
    newUd.setAccessToken(getUserAccessToken(userData)); 
    newUd.sign();
    
    return newUd;
  }
  
  private void saveAcceptTerms(Long newUserId, UserData userData) {
  	if (newUserId == null) {
  		return;
  	}
  	
    boolean acceptTerms = userData.getAcceptTerms();
    
    dbTs.createThingStuff_Unique(newUserId, ThingStuffTypeData.THINGSTUFFTYPE_ACCEPTTERMS, acceptTerms);
    
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
    
    String userConsumerKey = userData.getConsumreKey();
    String userConsumerSecret = userData.getConsumerSecret();
    
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
    ThingJdo tj = new ThingJdo(sp);
    ThingJdo[] users = tj.query((long)ThingTypeJdo.TYPE_USER, userData.getConsumreKey());
    long userId = 0;
    if (users != null && users.length > 0) {
      userId = users[0].getThingId();
    }
    
    boolean exists = false;
    if (userId > 0) {
      exists = true;
    }
    
    UserData ud = new UserData();
    if (exists == true) {
      ud.setError(UserData.KEY_EXISTS);
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
    OAuthTokenData appAccessToken = userData.getAccessToken(); // this is the app token shipped in
    appAccessToken.setConsumerKey(userData.getConsumreKey());
    appAccessToken.sign(url, userData.getConsumerSecret());
    
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
    
    SessionAccessTokenJdo s = new SessionAccessTokenJdo(sp);
    ThingData t = s.getThingData(ct, cs);
    
    // the id has to be a user
    if (t.getThingTypeId() != 2) {
      t = null;
    }
    
    return t;
  }
  
  
  public boolean changePassword(OAuthTokenData accessToken, ChangePasswordData changePassswordData) {
    
    // TODO access ok?
        
    ThingData thingData = changePassswordData.getThingData();
    String secretHash = changePassswordData.getSecret();
    
    ThingJdo t = new ThingJdo(sp);
    t.savePassword(thingData, secretHash);
    
    return true;
  }
  
  
  
  
  
  
  
}
