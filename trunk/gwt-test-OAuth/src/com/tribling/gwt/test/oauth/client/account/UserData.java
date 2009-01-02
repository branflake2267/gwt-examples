package com.tribling.gwt.test.oauth.client.account;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;
import com.tribling.gwt.test.oauth.client.oauth.Sha1;

public class UserData implements IsSerializable {

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
  boolean acceptTerms = false;
  
  // error notifications
  public int error = 0;
  
  // possible errors for error notifications
  final public static int SYSTEM_ERROR = 1;
  final public static int USER_EXISTS = 2;
  final public static int USERNAME_DONTMATCH = 3;
  final public static int PASSWORD_DONTMATCH = 4;
  final public static int BOTH_DONTMATCH = 5;
  
  
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
  
}
