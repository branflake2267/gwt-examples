package com.gawkat.flashcard.client.card;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
@EmbeddedOnly
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
  private int maxA = 12;
  
  @Persistent
  private int minB = 0;
  
  @Persistent
  private int maxB = 12;
 
  @Persistent
  private int a = 0;
  
  @Persistent 
  private int operator = MULTIPLY;
  
  @Persistent
  private int b = 0;
  
  private long answer = 0;
  
  private int theAnswer;


  // TODO - compiler won't compile this import - transient should skip import during compile
  // I am using MathDataJdo to wrap this up on server side
  // @--PrimaryKey
  // @--Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  //transient private Key key;

  
  /**
   * constructor
   */
  public MathData() {
  }
  
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

  public void setMinA(int minA) {
    this.minA = minA;
  }
  
  public void setMaxA(int maxA) {
    this.maxA = maxA;
  }
  
  public void setMinB(int minB) {
    this.minB = minB;
  }
  
  public void setMaxB(int maxB) {
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
      s = "x";
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
  
  public MathData getMathData() {
    
    MathData mathData = new MathData();
    mathData.minA = minA;
    mathData.maxA = maxA;
    mathData.minB = minB;
    mathData.maxB = maxB;
    mathData.a = a;
    mathData.operator = operator;
    mathData.b = b;
    
    return mathData;
  }
  
  public void setMathData(MathData mathData) {
    
    this.set(mathData.getDifficulty(), 
        mathData.getMinA(), mathData.getMaxA(), 
        mathData.getMinB(), mathData.getMaxB());
    
    this.setMath(mathData.getA(), mathData.getOperator(), 
        mathData.getB());
    
  }
  
  
}
