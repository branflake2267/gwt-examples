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

  public static AppTokenJdo getToken(String id) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    AppTokenJdo token = null;
    try {
      token = pm.getObjectById(AppTokenJdo.class, id);
    } catch (JDOObjectNotFoundException e) {
      //e.printStackTrace(); //skip this, b/c it will throw with none found
    } finally {
      pm.close();
    }
    
    return token;
  }
  
  public static AppTokenJdo getToken() {
    
    UserService userService = UserServiceFactory.getUserService();  
    if (userService.isUserLoggedIn() == false) {
      return null;
    }
    User user = userService.getCurrentUser();
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    AppTokenJdo token = null;
    try {
      token = pm.getObjectById(AppTokenJdo.class, user.getUserId());
    } catch (JDOObjectNotFoundException e) {
      //e.printStackTrace(); //skip this, b/c it will throw with none found
    } finally {
      pm.close();
    }
    
    return token;
  }

  public static void saveToken(AppTokenJdo token) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    try {
      pm.makePersistent(token);
    } finally {
      pm.close();
    }
    
  }
  
}
