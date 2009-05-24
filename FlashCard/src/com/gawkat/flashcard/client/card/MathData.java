package com.gawkat.flashcard.client.card;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
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
  
  public void setKey(Key key) {
    this.key = key;
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
