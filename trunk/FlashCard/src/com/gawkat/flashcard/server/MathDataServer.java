package com.gawkat.flashcard.server;

import com.gawkat.flashcard.client.card.MathData;
import com.google.appengine.api.users.User;

public class MathDataServer {

  private LoginServer login = new LoginServer();
  
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
    
    // TODO remember the last values the user pulled
    // this will only save if a user is logged in
    //mathData.save();
    
    return mathData;
  }
  
  private MathData getStart() {

    // is the user logged in?
    User user = null;
    if (login.getLoginData().isLoggedIn() == true) {
      user = login.getUser();
    }
    
    // TODO - if the user is logged in, see if there is a previous mathData to use
    
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
