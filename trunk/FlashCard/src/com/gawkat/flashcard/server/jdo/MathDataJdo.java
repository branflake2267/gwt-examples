package com.gawkat.flashcard.server.jdo;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.gawkat.flashcard.client.card.MathData;
import com.gawkat.flashcard.server.LoginServer;
import com.gawkat.flashcard.server.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MathDataJdo {
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key = null;
    
  @Persistent
  private MathData mathData = null;
  
  /**
   * constructor
   */
  public MathDataJdo() {
  }
   
  /**
   * save to jdo datastore
   */
  public void saveMathDataJdo(MathData mathData) {

    LoginServer login = new LoginServer();
    User user = login.getUser();
    
    // only save if user exists
    if (user == null) {
      return;
    }
    
    this.mathData = mathData;
    
    // setup the primary key
    key = KeyFactory.createKey(MathDataJdo.class.getSimpleName(), user.getEmail());
    
    // save the object to the store
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(this);
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
  public MathData getMathDataJdo() {
    
    LoginServer login = new LoginServer();
    User user = login.getUser();
    
    if (user == null) {
      return null;
    }

    // setup the primary key
    key = KeyFactory.createKey(MathDataJdo.class.getSimpleName(), user.getEmail());

    // save the object to the store
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    // query the mathData by key
    MathDataJdo mathDataJdo = pm.getObjectById(MathDataJdo.class, key);
    
    pm.close();
  
    mathData = mathDataJdo.mathData;
    
    return mathData;
  }
  
  
}
