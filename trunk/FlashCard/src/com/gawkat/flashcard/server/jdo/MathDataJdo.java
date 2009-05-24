package com.gawkat.flashcard.server.jdo;

import javax.jdo.PersistenceManager;

import com.gawkat.flashcard.client.card.MathData;
import com.gawkat.flashcard.server.LoginServer;
import com.gawkat.flashcard.server.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;

public class MathDataJdo {
  
  /**
   * save to jdo datastore
   */
  public void saveMathData(MathData mathData) {

    LoginServer login = new LoginServer();
    User user = login.getUser();
    
    // only save if user exists
    if (user == null) {
      return;
    }

    // setup the primary key
    Key key = KeyFactory.createKey(MathData.class.getSimpleName(), user.getEmail());
    mathData.setKey(key);
    
    // save the object to the store
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(mathData);
    } finally {
      pm.close();
    }

  }
  
  /**
   * get the mathData for the user, which was last saved
   * 
   * @param user
   * @return
   */
  public MathData getMathData() {
    
    LoginServer login = new LoginServer();
    User user = login.getUser();
    
    if (user == null) {
      return null;
    }

    // setup the primary key
    Key key = KeyFactory.createKey(MathData.class.getSimpleName(), user.getEmail());

    // save the object to the store
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    // query the mathData by key
    MathData mathData = pm.getObjectById(MathData.class, key);
    
    pm.close();
   
    return mathData;
  }
}
