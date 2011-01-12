package org.gonevertical.demo.server;

import org.gonevertical.demo.client.LoginData;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Google_Login {

 public LoginData getLoginData(String currentUrl) {
    
    UserService userService = UserServiceFactory.getUserService();
    
    String loginUrl = userService.createLoginURL(currentUrl);
    String logoutUrl = userService.createLogoutURL(currentUrl);
    
    User u = userService.getCurrentUser();
    String userId = null;
    String nick = null;
    boolean isAdmin = false;
    boolean isLoggedIn = false;
    if (u != null) {
      userId = u.getUserId();
      nick = u.getNickname();
      isLoggedIn = userService.isUserLoggedIn();
      isAdmin = userService.isUserAdmin();
    }
    
    LoginData loginData = new LoginData();
    loginData.setGoogleLoginUrl(loginUrl);
    loginData.setGoogleLogoutUrl(logoutUrl);
    loginData.setGoogleUserId(userId);
    loginData.setGoogleNick(nick);
    loginData.setGoogleLoggedIn(isLoggedIn);
    loginData.setGoogleAdmin(isAdmin);
    
    return loginData;
  }
  
}
