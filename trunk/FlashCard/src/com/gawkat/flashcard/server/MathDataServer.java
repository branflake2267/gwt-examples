package com.gawkat.flashcard.server;

import com.gawkat.flashcard.client.card.MathData;

public class MathDataServer {

  public MathDataServer() {
  }
  
  public MathData getMathData(MathData mathData) {
    
    if (mathData == null) {
      mathData = getStart();
    }
 
    if (mathData.getDifficulty() == MathData.HARD) {
      mathData = getHard(mathData);
    } else if (mathData.getDifficulty() == MathData.MEDIUM) {
      mathData = getMedium(mathData);
    } else { 
      mathData = getEasy(mathData);
    }
    
    return mathData;
  }
    
  private MathData getStart() {
    
    MathData mathData = new MathData();
    
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
    
    // if b > max then start over to min
    if (b > mathData.getMaxB()) { 
      b = mathData.getMinB();
      a++;
      if (a > mathData.getMaxA()) {
        a = mathData.getMinA();
      }
    } else {
      b++;
    }
    
    mathData.setMath(a, mathData.getOperator(), b);
    
    return mathData;
  }
  
}
