package org.gonevertical.demo.server.jdo;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AppTokenStore {
  
  
  
  public static boolean getHasToken() {
    UserService userService = UserServiceFactory.getUserService();  
    User user = userService.getCurrentUser();
    if (userService.isUserLoggedIn() == false) {
      return false;
    }
    
    boolean b = false;
    if (getToken(user.getUserId()) != null) {
      b = true;
    }
    return b;
  }

  public static AppToken getToken(String id) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    AppToken token = null;
    try {
      token = pm.getObjectById(AppToken.class, id);
    } catch (JDOObjectNotFoundException e) {
      e.printStackTrace();
    } finally {
      pm.close();
    }
    
    return token;
  }

  public static void saveToken(AppToken token) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    try {
      pm.makePersistent(token);
    } finally {
      pm.close();
    }
    
  }
  
}
