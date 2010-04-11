package org.gonevertical.loc.client;

import org.gonevertical.loc.client.digit.Digits;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Winner extends Composite {
  
  public static final int A = 1;
  public static final int B = 2;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private int ab = 0;
  
  private boolean tie = false;
  
  private int loopIndex = 0;
  
  private boolean animate = false;
  
  // use these to control the digits on winning
  private Digits wDigitsA;
  private Digits wDigitsB;
  private ButtonLights wButtonLights;
  
  public Winner() {
    
    initWidget(pWidget);
    
    pWidget.setVisible(false);
  }
  
  public void showWinner(int ab, boolean tie) {
    this.ab = ab;
    this.tie = tie;
    drawWinner();
    animate = true;
    loop(); 
  }
  
  public void loop() {
    
    Timer t = new Timer() {
      public void run() {
        displayWinner();
        if (loopIndex >= 7) {
          reset();
        }
        System.out.println("loop: " + loopIndex);
        if (animate == true) {
          loop();
        }
        loopIndex++;
      }
    };
    t.schedule(1000);
  }
  
  private void reset() {
    loopIndex = 0;
    tie = false;
    ab = 0;
    animate = false;
    
    // notify game controls of a stop
    fire();
  }
  
  private void displayWinner() {
    pWidget.setVisible(true);
    flashDigits(true);
    
    Timer t = new Timer() {
      public void run() {
        pWidget.setVisible(false);
        flashDigits(false);
      }
    };
    t.schedule(900);
  }
  
  private void drawWinner() {
    pWidget.clear();
    
    String p = "";
    if (ab == A && tie == false) {
      p = "Player 1";
    } else if (ab == B && tie == false) {
      p = "Player 2";
    }
    
    String s = "";
    if (tie == true) {
      s = "Players Tie";
    } else {
      s = p + " Wins";
    }
    
    HTML h = new HTML(s);
   
    pWidget.add(h);
  }

  public void displayAll(boolean b) {
    pWidget.setVisible(b);
    if (b == true) {
      ab = EventManager.A;
      drawWinner();
    } else if (b == false) {
      ab = 0;
      pWidget.clear();
    }
  }
  
  private void flashDigits(boolean b) {
    if (tie == true) {
      wDigitsA.displayOnly(b);
      wDigitsB.displayOnly(b);
      wButtonLights.turnOnPlayer(3, b);
    } else if (ab == A) {
      wDigitsA.displayOnly(b);
      wButtonLights.turnOnPlayer(1, b);
    } else if (ab == B) {
      wDigitsB.displayOnly(b);
      wButtonLights.turnOnPlayer(2, b);
    }
  }
  
  private void fire() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  public void setDigitControl(Digits wDigitsA, Digits wDigitsB, ButtonLights wButtonLights) {
    this.wDigitsA = wDigitsA;
    this.wDigitsB = wDigitsB;
    this.wButtonLights = wButtonLights;
  }

}
