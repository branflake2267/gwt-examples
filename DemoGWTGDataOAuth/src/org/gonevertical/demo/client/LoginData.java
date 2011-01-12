package org.gonevertical.demo.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginData implements IsSerializable {

  private String googleLoginUrl;
  
  private String googleLogoutUrl;
  
  private String googleUserId;
  
  private String googleNick;

  private boolean googleIsLoggedIn;

  private boolean googleIsAdmin;
  
  public void setGoogleLoginUrl(String url) {
    this.googleLoginUrl = url;
  }
  
  public String getGoogleLoginUrl() {
    return googleLoginUrl;
  }
  
  public void setGoogleLogoutUrl(String url) {
    this.googleLogoutUrl = url;
  }
  
  public String getGoogleLogoutUrl() {
    return googleLogoutUrl;
  }
  
  public void setGoogleUserId(String userId) {
    this.googleUserId = userId;
  }
  
  public String getGoogleUserId() {
    return googleUserId;
  }
  
  public void setGoogleNick(String nick) {
    this.googleNick = nick;
  }
  
  public String getGoogleNick() {
    return googleNick;
  }

  public void setGoogleLoggedIn(boolean isLoggedIn) {
    this.googleIsLoggedIn = isLoggedIn;
  }
  
  public boolean getGoogleLoggedIn() {
    return googleIsLoggedIn;
  }
  
  public void setGoogleAdmin(boolean isAdmin) {
    this.googleIsAdmin = isAdmin;
  }
  
  public boolean getGoogleAdmin() {
    return googleIsAdmin;
  }

  public String getGoogleNickNotNull() {
    String s = googleNick;
    if (googleNick == null) {
      s = "&nbsp;";
    }
    return s;
  }
}
