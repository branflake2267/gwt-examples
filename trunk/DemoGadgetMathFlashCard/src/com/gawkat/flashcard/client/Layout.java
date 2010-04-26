package com.gawkat.flashcard.client;

import com.gawkat.flashcard.client.card.Answer;
import com.gawkat.flashcard.client.card.Card;
import com.gawkat.flashcard.client.card.MathData;
import com.gawkat.flashcard.client.card.MathDataServer;
import com.gawkat.flashcard.client.card.Menu;
import com.gawkat.flashcard.client.card.Notify;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Layout extends Composite {
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private Menu wMenu = new Menu();
  
  private Card wCard = new Card();
  
  private Answer wAnswer = new Answer();

  private Notify wNotify = new Notify();
  
  private MathData mathData = null;
  
  private MathDataServer mathDataServer = new MathDataServer();
  
  public Layout() {
    
    pWidget.add(wMenu);
    pWidget.add(wCard);
    pWidget.add(wAnswer);
    pWidget.add(wNotify);
    
    initWidget(pWidget);
    
    //style
    pWidget.setCellHorizontalAlignment(wMenu, HorizontalPanel.ALIGN_RIGHT);
    pWidget.setCellHorizontalAlignment(wCard, HorizontalPanel.ALIGN_CENTER);
    pWidget.setCellHorizontalAlignment(wAnswer, HorizontalPanel.ALIGN_CENTER);
    pWidget.setStyleName("flashcard-navigation");
    wNotify.setWidth("100%");
    wCard.setWidth("100%");
     
    setHandlers();
    
    Track.track("Load");
  }
  
  private void setHandlers() {
    
    wAnswer.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        int clickEventType = wAnswer.getClickEventType();
        if (clickEventType == Answer.ANSWER) {
          checkAnswer();
        } else if (clickEventType == Answer.HINT) {
          showAnswer();
        }
      }
    });
    
  }
  
  private void showAnswer() {
    mathData.checkAnswer(0);
    wNotify.setAnswer(mathData.getTheAnswer());
    wNotify.drawNotice(Notify.ANSWER_DISPLAY);
  }
  
  private void checkAnswer() {
    
    if (wAnswer.isAnswer() == true) {
      int answer = wAnswer.getAnswer();
      boolean isCorrect = mathData.checkAnswer(answer);
      if (isCorrect == true) {
        wNotify.drawNotice(Notify.ANSWER_CORRECT);
        getNewCard();
      } else if (isCorrect == false) {
        wNotify.setAnswer(mathData.getTheAnswer());
        wNotify.drawNotice(Notify.ANSWER_WRONG);
        wAnswer.resetInput();
      }
    }
    
  }
  
  private void getNewCard() {
  	/*
    Timer timer = new Timer() {
      public void run() {
        getMathData(mathData);
      }
    };
    timer.schedule(100);
    */
    getMathData(mathData);
  }
  
  /**
   * first things first
   */
  public void start() {
    MathData mathData = null;
    getMathData(mathData);
  }
  
  private void process(MathData mathData) {
    this.mathData = mathData;
    wCard.setMathData(mathData);
  }
  
  /**
   * this was a rpc call, but changing to see if I can get it to work in gadget form
   * @param mathData
   */
  private void getMathData(MathData mathData) {
    
    wAnswer.setState(false);
    wAnswer.resetInput();
    
    mathData = mathDataServer.getMathData(mathData);
    process(mathData);
    wAnswer.setState(true);
    
    //System.out.println("getMathData");
    
    /*
    getMathData(mathData, new AsyncCallback<MathData>() {
      public void onSuccess(MathData mathData) {
        process(mathData);
        wAnswer.setState(true);
      }

    });
    */
    
  }


  
}
