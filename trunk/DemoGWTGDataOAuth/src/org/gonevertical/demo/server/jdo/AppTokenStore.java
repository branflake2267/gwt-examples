package org.gonevertical.demo.server.jdo;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AppTokenStore {
  
  public static boolean getHasToken(String scope) {
    UserService userService = UserServiceFactory.getUserService();  
    if (userService.isUserLoggedIn() == false) {
      return false;
    }
    
    boolean b = false;
    if (getToken(scope) != null) {
      b = true;
    }
    return b;
  }

  public static AppTokenJdo getToken(String scope) {
    UserService userService = UserServiceFactory.getUserService();  
    if (userService.isUserLoggedIn() == false) {
      return null;
    }
    String id = getId(userService, scope);
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    AppTokenJdo token = null;
    try {
      token = pm.getObjectById(AppTokenJdo.class, id);
    } catch (JDOObjectNotFoundException e) {
      //e.printStackTrace(); //skip this, b/c it will throw if no id exists
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
  
  public static void deleteToken(String scope) {
    UserService userService = UserServiceFactory.getUserService();  
    if (userService.isUserLoggedIn() == false) {
      return;
    }
    
    String id = getId(userService, scope);
    
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      AppTokenJdo token = pm.getObjectById(AppTokenJdo.class, id);
      pm.deletePersistent(token);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      pm.close();
    }
    
  }

  private static String getId(UserService userService, String scope) {
    String id = userService.getCurrentUser().getUserId() + "_" + scope;
    return id;
  }
  
}
