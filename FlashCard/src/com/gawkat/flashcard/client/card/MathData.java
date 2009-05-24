package com.gawkat.flashcard.client.card;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.gawkat.flashcard.server.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.gwt.user.client.rpc.IsSerializable;

public class MathData implements IsSerializable {

  // coming from client or heading back to client
  public final static int REQUEST = 1;
  public final static int RESPONSE = 2;
  
  // math operator type
  public final static int MULTIPLY = 1;
  public final static int DIVISION = 2;
  public final static int ADD = 3;
  public final static int SUBTRACT = 4;
  
  // experience level
  public final static int EASY = 1;
  public final static int MEDIUM = 2;
  public final static int HARD = 3;
  
  // variables
  @Persistent
  private int difficulty = EASY;
  
  @Persistent
  private int minA = 0;
  
  @Persistent
  private int maxA = 11;
  
  @Persistent
  private int minB = 0;
  
  @Persistent
  private int maxB = 11;
  
  private long answer = 0;
  
  @Persistent
  private int a = 0;
  
  @Persistent
  private int operator = MULTIPLY;
  
  @Persistent
  private int b = 0;
  
  @Persistent
  private int theAnswer;

  @PrimaryKey
  private User user = null;
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;


  /**
   * set data
   * 
   * @param difficulty
   * @param minA
   * @param maxA
   * @param minB
   * @param maxB
   */
  public void set(int difficulty, int minA, int maxA, int minB, int maxB) {
   this.difficulty = difficulty;
   this.minA = minA;
   this.minB = minB;
   this.maxA = maxA;
   this.maxB = maxB; 
  }

  /**
   * set math
   * 
   * @param a
   * @param o
   * @param b
   */
  public void setMath(int a, int o, int b) {
    this.a = a;
    this.operator = o;
    this.b = b;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public int getA() {
    return a;
  }
  
  public int getB() {
    return b;
  }
  
  public int getOperator() {
    return operator;
  }
  
  public int getDifficulty() {
    return difficulty;
  }
  
  public long getAnswer() {
    return answer;
  }
  
  public void setAnswer(long answer) {
    this.answer = answer;
  }
  
  public int getMinA() {
    return minA;
  }
  
  public int getMaxA() {
    return maxA;
  }
  
  public int getMinB() {
    return minB;
  }
  
  public int getMaxB() {
    return maxB;
  }
  
  /**
   * get operator type
   * 
   * @param o
   * @return
   */
  public static String getOperator(int o) {
    String s = "";
    if (o == MULTIPLY) {
      s = "*";
    } else if (o == DIVISION) {
      s = "/";
    } else if (o == ADD) {
      s = "+";
    } else if (o == SUBTRACT) {
      s = "-";
    }
    return s;
  }
  
  /**
   * save to jdo datastore
   */
  public void save() {

    // only save if user exists
    if (user == null) {
      return;
    }

    // setup the primary key
    key = KeyFactory.createKey(MathData.class.getSimpleName(), user.getEmail());

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
  public MathData getMathData(User user) {
    
    if (user == null) {
      return null;
    }

    // setup the primary key
    key = KeyFactory.createKey(MathData.class.getSimpleName(), user.getEmail());

    // save the object to the store
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    // query the mathData by key
    MathData mathData = pm.getObjectById(MathData.class, key);
    
    pm.close();
    
    return mathData;
  }

  public void setAnswer(int answer) {
    this.answer = answer;
  }
  
  public int getTheAnswer() {
    return theAnswer;
  }
  
  public boolean checkAnswer(int answer) {
    
    int theAnswer = 0;
    if (operator == MULTIPLY) {
      theAnswer = a * b;
    } else if (operator == DIVISION) { // todo use Double as default?
      theAnswer = a / b;
    } else if (operator == ADD) {
      theAnswer = a + b;
    } else if (operator == SUBTRACT) {
      theAnswer = a - b;
    }
    
    this.theAnswer = theAnswer;
    
    boolean b = false;
    if (theAnswer == answer) {
      b = true;
    }
    return b;
  }
}
