package com.tribling.gwt.test.oauth.client.account;

public class UserData {

  
  // userName for login
  public String email = null;
  
  
  // password will be hashed (always) in transit
  public String password = null;

  
  // accept terms of use
  boolean acceptTerms = false;
  
  
  // error notifications
  public int error = 0;
  
  
  /**
   * constructor - nothing to do
   */
  public UserData() {
  }
  
}
