package com.tribling.gwt.test.oauth.server.db;

import com.tribling.gwt.test.oauth.client.account.UserData;

public class Db_User extends Db_Conn {

  public Db_User() {
    
  }
  
  public UserData createUser(UserData userData) {
    // TODO
    
    return null;
  }
  
  /**
   * check to see if the user exists already
   *   TODO - key could be a hash?
   * 
   * @param userData
   * @return
   */
  public UserData isUserNameExist(UserData userData) {
    
    String key = userData.consumerKey;
    String secret = userData.consumerSecret;
    
    if (key == null) {
      key = "";
    }
    if (secret == null) {
      secret = "";
    }
    
    // TODO - incase something goes wrong
    if (key.length() == 0) {
      System.out.println("isUserNameExist(); No consumerKey.");
      return null;
    }
    if (secret.length() == 0) {
      System.out.println("isUserNameExist(); No consumerSecret.");
      return null;
    }
    
    // TODO - I think I need to run more checking later
    key = escapeForSql(key);
    secret = escapeForSql(secret);
  
    String sql = "SELECT UserId FROM `user` " +
    		"WHERE " +
    		"ConsumerKey='" + key + "' AND " +
    		"consumerSecret='"+secret+"';";
    
    int userId = getQueryInt(sql);
    
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
  
}
