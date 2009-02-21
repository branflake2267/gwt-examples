package com.gawkat.gwt.oauth.client.account;

import com.gawkat.gwt.oauth.client.oauth.OAuthTokenData;
import com.gawkat.gwt.oauth.client.oauth.Sha1;
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserData implements IsSerializable {

  // used on server side
  public int userId = 0;
  
  // consumer accessToken
  // web application has access
  // will be used to verify this application can create users
  // will be used to verify this application can login users
  // its like a sessionVars that are for a non-user session
  public OAuthTokenData accessToken = null; 
  
  // userName for login
  public String consumerKey = null;
  
  // hash of password
  public String consumerSecret = null;

  // accept terms of use
  public boolean acceptTerms = false;
  
  // error notifications
  public int error = 0;
  
  // possible errors for error notifications
  final public static int SYSTEM_ERROR = 1;
  final public static int KEY_EXISTS = 2;
  final public static int KEYS_DONTMATCH = 3;
  final public static int SECRETS_DONTMATCH = 4;
  final public static int BOTH_DONTMATCH = 5;
  final public static int KEYS_SHORT = 6;
  final public static int KEY1_SHORT = 7;
  final public static int KEY2_SHORT = 8;
  final public static int SECRETS_SHORT = 9;
  final public static int SECRET1_SHORT = 10;
  final public static int SECRET2_SHORT = 11;
  final public static int BOTH_SHORT = 12;
  final public static int SIGNATURE_ERROR = 13;
  final public static int ACCEPT_TERMS = 14;
  final public static int ERR_NO_USER = 15;
  
  // hash of this objects vars
  // verify it was disturbed during transit
  private String signature = null;
  
  
  /**
   * constructor - nothing to do
   */
  public UserData() {
  }
  
  /**
   * sign this object variables that I don't want messed with
   * TODO - add some salt? - keeping it simple for now, return to later
   * 
   */
  public void sign() {
    Sha1 sha = new Sha1();
    this.signature = sha.b64_sha1(getSignatureBaseString());
  }
  
  /**
   * verify this objects varibles
   * TODO - add some salt?
   * 
   * @return
   */
  public boolean verify() {
    Sha1 sha = new Sha1();
    
    boolean pass = false;
    if (this.signature.equals(sha.b64_sha1(getSignatureBaseString()))) {
      pass = true;
    }
    
    return pass;
  }
  
  private String getSignatureBaseString() {
    String s = "";
    s += "consumerKey=" + consumerKey + "&";
    s += "consumerSecret=" + consumerSecret;
    return s;
  }
  
  public String getNotification() {
    String s = "";
    switch (error) {
    case SIGNATURE_ERROR:
      s = "Signature does not match. Verification of signature problem. Internal error.";
      break;
    case UserData.SYSTEM_ERROR:
      s = "System error occurred. Contact the administrator.";
      break;
    case UserData.KEY_EXISTS:
      s = "This user name exists already. Please choose another.";
      break;
    case UserData.KEYS_DONTMATCH:
      s = "Usernames don't match.";
      break;
    case UserData.SECRETS_DONTMATCH:
      s = "Passwords don't match.";
      break;
    
    }
    return s;
  }
  
  /**
   * possible errors
   * 
   * TODO - switch to internation version later
   * 
   * @param error
   * @return
   */
  public static String getError(int error) {
    
    String s = "";
    switch (error) {
    case SYSTEM_ERROR:
      s = "System/Server error.";
      break;
    case KEY_EXISTS:
      s = "Username exists. Try another.";
      break;
    case KEYS_DONTMATCH:
      s = "Usernames do not match.";
      break;
    case SECRETS_DONTMATCH:
      s = "Passwords do not match.";
      break;
    case BOTH_DONTMATCH:
      s = "Your usernames and passwords do not match.";
      break;
    case KEYS_SHORT:
      s = "Add more characters to your usernames.";
      break;
    case KEY1_SHORT:
      s = "Add more characters to your username.";
      break;
    case KEY2_SHORT:
      s = "Add more characters to your username.";
      break;
    case SECRETS_SHORT:
      s = "Add more characters to your passwords.";
      break;
    case SECRET1_SHORT:
      s = "Add more characters to your password.";
      break;
    case SECRET2_SHORT:
      s = "Add more characters to your password.";
      break;
    case BOTH_SHORT:
      s = "Add more characters to both the username and password.";
      break;
    case ACCEPT_TERMS:
      s = "You need to accept the terms of use and privacy agreements to use this application.";
      break;
    }
    
    return s;
  }
  
  
  public String getError() {
    return getError(this.error);
  }
}
