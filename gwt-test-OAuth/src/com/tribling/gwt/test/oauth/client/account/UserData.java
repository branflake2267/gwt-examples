package com.tribling.gwt.test.oauth.client.account;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;

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
  
  
  /**
   * constructor - nothing to do
   */
  public UserData() {
  }
  
  public void sign() {
    // TODO
  }
  
  public boolean verify() {
    // TODO
    return false;
  }
  
}
