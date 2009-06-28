package com.gawkat.walletinventory.server;

import com.gawkat.walletinventory.client.login.LoginData;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginServer {

  public LoginServer() {
  }
  
  public LoginData getLoginData(String requestUri) {
    
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    LoginData loginData = new LoginData();
    if (user != null) {
      loginData.setLoggedIn(true);
      loginData.setEmailAddress(user.getEmail());
      loginData.setNickname(user.getNickname());
      loginData.setLogoutUrl(userService.createLogoutURL(requestUri));
    } else {
      loginData.setLoggedIn(false);
      loginData.setLoginUrl(userService.createLoginURL(requestUri));
    }

    return loginData; 
  }
  
  public LoginData getLoginData() {
  
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    LoginData loginData = new LoginData();
    if (user != null) {
      loginData.setLoggedIn(true);
      loginData.setEmailAddress(user.getEmail());
      loginData.setNickname(user.getNickname());
      //loginData.setLogoutUrl(userService.createLogoutURL(requestUri));
    } else {
      loginData.setLoggedIn(false);
      //loginData.setLoginUrl(userService.createLoginURL(requestUri));
    }

    return loginData; 
  }
  
  public User getUser() {
    UserService userService = UserServiceFactory.getUserService();
    return userService.getCurrentUser();
  }
}
