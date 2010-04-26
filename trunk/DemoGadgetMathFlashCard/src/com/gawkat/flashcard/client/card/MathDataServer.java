package com.gawkat.flashcard.client.card;


public class MathDataServer {

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
      skip = false;
    }
    
    
    return mathData;
  }
   
  private MathData getStart() { 
  	MathData  mathData = new MathData(); 
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
    
    try {
      mathData.setMath(a, mathData.getOperator(), b);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return mathData;
  }
  
}
