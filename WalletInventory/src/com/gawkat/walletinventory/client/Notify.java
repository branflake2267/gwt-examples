package com.gawkat.walletinventory.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Notify extends Composite {

  public static final int ANSWER_WRONG = 1;
  public static final int ANSWER_CORRECT = 2;
  public static final int ANSWER_DISPLAY = 3;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private FlowPanel pNotice = new FlowPanel();
  
  private int answer = 0; 
  
  public Notify() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(new HTML("&nbsp;"));
    hp.add(pNotice);
    hp.add(new HTML("&nbsp;"));
    
    //default
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    pNotice.setWidth("100%");
    pWidget.setCellHorizontalAlignment(hp, HorizontalPanel.ALIGN_CENTER);
    //hp.setCellHorizontalAlignment(pNotice, HorizontalPanel.ALIGN_CENTER);
    
    // test
    pWidget.addStyleName("flashcard-notify");
  }
  
  public void drawNotice(int type) {
    
    String s = "";
    switch (type) {
    case ANSWER_WRONG:
      s = getTryAgain();
      setBadJobColor();
      break;
    case ANSWER_CORRECT:
      s = getGoodJob();
      setGoodJobColor();
      break;
    case ANSWER_DISPLAY:
      s = displayAnswer();
      break;
    }
    
    draw(s);
    
    //reset
    this.answer = 0;
  }
  
  private String displayAnswer() {
    String s = "Answer is: " + answer;
    return s;
  }

  private void draw(String s) {
    pNotice.clear();
    HTML h = new HTML(s);
    pNotice.add(h);
    h.setStyleName("flashcard-notifyaffirm ");
  }
  
  public void setAnswer(int answer) {
    this.answer = answer;
  }
  
  private String getTryAgain() {
    String s = "";
    s = "Try Again";
    return s;
  }
  
  private void setGoodJobColor() {
    String color = Color.getRandomColor();
    DOM.setStyleAttribute(pNotice.getElement(), "color", color);
  }
  
  private void setBadJobColor() {
    String color = Color.getRandomColor();
    DOM.setStyleAttribute(pNotice.getElement(), "color", color); // #B8002E dark red
  }

  private String getGoodJob() {
    
    int num = getRandom();
    
    String s = "";
    switch (num) {
    case 0:
      s = "Good Job";
      break;
    case 1:
      s = "Excellent";
      break;
    case 2:
      s = "Terrific";
      break;
    case 3:
      s = "Way to go";
      break;
    case 4:
      s = "Awesome";
      break;
    case 5:
      s = "Stellar";
      break;
    case 6:
      s = "Radical";
      break;
    case 7:
      s = "Narly";
      break;
    case 8:
      s = "Spectacular";
      break;
    case 9:
      s = "Sweet";
      break;
    case 10:
      s = "Keep Going";
      break;
    case 11:
      s = "Cool";
      break;
    case 12:
      s = "Wonderful";
      break;
    case 13:
      s = "Nice";
      break;
    case 14:
      s = "Amazing";
      break;
    case 15:
      s = "Great";
      break;
    case 16:
      s = "Brilliant";
      break;
    case 17:
      s = "Sensational";
      break;
    case 18:
      s = "Outstanding";
      break;
    case 19:
      s = "Correct";
      break;
    case 20:
      s = "Wahooo!";
      break;
    case 21:
      s = "Lovely";
      break;
    case 22:
      s = "Beautiful";
      break;
    case 23:
      s = "You Can Do It";
      break;
    case 24:
      s = "Wow";
      break;
    case 25:
      s = "Yipeee";
      break;
    }
    
    return s;
  }

  private int getRandom() {
    int howmany = 26; // remember I use a zero ordinal number in the switch above
    return (int) Math.floor(Math.random() * howmany);
  }
}
