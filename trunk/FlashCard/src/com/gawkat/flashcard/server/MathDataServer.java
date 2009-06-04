package com.gawkat.flashcard.server;

import com.gawkat.flashcard.client.card.MathData;
import com.gawkat.flashcard.server.jdo.MathDataJdo;

public class MathDataServer {

  private LoginServer login = new LoginServer();
  
  public MathDataServer() {
  }
  
  /**
   * main rpc method
   * 
   * @param mathData
   * @return
   */
  public MathData getMathData(MathData mathData) {
    
    boolean skip = false;
    if (mathData == null) {
      mathData = getStart();
      skip = true;
    }
 
    if (mathData.getDifficulty() == MathData.HARD) {
      mathData = getHard(mathData);
    } else if (mathData.getDifficulty() == MathData.MEDIUM) {
      mathData = getMedium(mathData);
    } else { 
      mathData = getEasy(mathData);
    }
 
    if (skip != true) {
      save(mathData);
      skip = false;
    }
    
    
    return mathData;
  }
  
  private void save(MathData mathData) {
    MathDataJdo mdj = new MathDataJdo();
    mdj.setMathData(mathData);
    mdj.saveMathDataJdo();
  }
  
  private MathData getSavedMathData() {
    
    MathDataJdo mdj = new MathDataJdo();
    MathData mathData = mdj.getMathDataJdo();
    
    if (mathData == null) {
      mathData = new MathData();
    }
    
    return mathData;
  }

  private MathData getStart() {

    // is the user logged in?
    MathData mathData = null;
    if (login.getLoginData().isLoggedIn() == true) { // logged in, get last saved position
      
      mathData = getSavedMathData();
      
    } else { // not logged in, start fresh from 0 * 0
      mathData = new MathData(); 
    }

    return mathData;
  }
  
  private MathData getHard(MathData mathData) {
    return mathData;
  }
  
  private MathData getMedium(MathData mathData) {
    return mathData;
  }
  
  private MathData getEasy(MathData mathData) {
    
    mathData = move(mathData);
    
    return mathData;
  }
  
  private MathData move(MathData mathData) {
    
    int a = mathData.getA();
    int b = mathData.getB();
    
    boolean skipa = false;
    boolean skipb = false;
    
    if (a < mathData.getMinA()) {
      a = mathData.getMinA();
      skipa = true;
    } else if (a > mathData.getMaxA()) {
      a = mathData.getMaxA();
      skipa = true;
    }
    
    if (b < mathData.getMinB()) {
      b = mathData.getMinB();
      skipb = true;
    } else if (b > mathData.getMaxB()) {
      b = mathData.getMaxB();
      skipb = true;
    }
    
    // if b > max then start over to min
    if (b >= mathData.getMaxB() && skipa == false) { 
      b = mathData.getMinB();
      a++;
      if (a > mathData.getMaxA()) {
        a = mathData.getMinA();
      }
    } else  {
      if (skipb == false) {
        b++;
      }
      if (b > mathData.getMaxB()) {
        b = mathData.getMinB();
      }
    }
    
    mathData.setMath(a, mathData.getOperator(), b);
    
    return mathData;
  }
  
}
