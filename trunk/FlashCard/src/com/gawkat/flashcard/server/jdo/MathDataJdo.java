package com.gawkat.flashcard.server.jdo;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Embedded;
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

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class MathDataJdo {
 
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;
 
  // this will save the data and I see it on the dataviewer
  // TODO - now why can't I get the data
  @Persistent
  @Embedded
  private MathData mathData = null;
 
  @Persistent
  private int testing = 0;
  
  /**
   * constructor
   */
  public MathDataJdo() {
  }
  
  /**
   * save to jdo datastore
   */
  public void saveMathDataJdo() {

    LoginServer login = new LoginServer();
    User user = login.getUser();
    
    // only save if user exists
    if (user == null) {
      return;
    }

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
    Key key = KeyFactory.createKey(MathDataJdo.class.getSimpleName(), user.getEmail());
 
    // save the object to the store
    PersistenceManager pm = PMF.get().getPersistenceManager();
    pm.setDetachAllOnCommit(true);

    // query the mathData by key
    MathDataJdo mathDataJdo = null;
    try {
      mathDataJdo = pm.getObjectById(MathDataJdo.class, key);
    } catch (Exception e) {
      e.printStackTrace();
    }

    pm.close();
  
    MathData mathData = null;
    if (mathDataJdo == null) {
      mathData = null;
    } else {
      if (mathDataJdo.getMathData() != null) {
        mathData = mathDataJdo.getMathData();
      }
    }
    
    return mathData;
  }

  public void setMathData(MathData mathData) {
    this.mathData = mathData;
  }

  public void setKey(Key key) {
    this.key = key;
  }
  
  public void setTesting(int testing)  {
    this.testing = testing;
  }
  
  public MathData getMathData() {
    return this.mathData;
  }
  
}
