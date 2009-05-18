package com.gawkat.flashcard.client.card;

import com.gawkat.flashcard.client.Track;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Answer extends Composite implements HasChangeHandlers, ClickHandler, KeyDownHandler, KeyPressHandler {

  public static final int ANSWER = 1;

  public static final int HINT = 2;

  private VerticalPanel pWidget = new VerticalPanel();
  
  private TextBox tbAnswer = new TextBox();
  
  private PushButton bAnswer = new PushButton("Answer");
  
  private PushButton bHint = new PushButton("Hint");
  
  // this probably could be done with a custom event setup
  private int clickEventType = 0;
  
  public Answer() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(tbAnswer);
    hp.add(new HTML("&nbsp;"));
    hp.add(bAnswer);
    hp.add(new HTML("&nbsp;"));
    hp.add(bHint);
    
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    tbAnswer.setWidth("30px");
    
    bAnswer.addClickHandler(this);
    bHint.addClickHandler(this);
    tbAnswer.addKeyDownHandler(this);
    tbAnswer.addKeyPressHandler(this);
  }
  
  public void setState(boolean b) {
    bAnswer.setEnabled(b);
    tbAnswer.setEnabled(b);
    if (b == true) {
      tbAnswer.setFocus(true);
    }
  }
  
  public void resetInput() {
    tbAnswer.setText("");
  }
  
  public boolean isAnswer() {
    boolean b = false;
    if (tbAnswer.getText().length() > 0) {
      b = true;
    }
    return b;
  }
  
  public int getAnswer() {
    String s = tbAnswer.getText();
    int r = 0;
    if (s.length() > 0) {
      r = Integer.parseInt(s);
    }
    return r;
  }
  
  private void fireChange() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }
  
  public int getClickEventType() {
    return this.clickEventType;
  }

  public void onClick(ClickEvent event) {
   
    Widget sender = (Widget) event.getSource();
    
    if (sender == bAnswer) {
      clickEventType = ANSWER;
    } else if (sender == bHint) {
      clickEventType = HINT;
    }
    fireChange();
    
    Track.track("AnswerClick");
  }

  public void onKeyDown(KeyDownEvent event) {

  }

  public void onKeyPress(KeyPressEvent event) {
 
    char keyCode = event.getCharCode();
    
    //System.out.println("KeyCode: " + keyCode);
    
    if (keyCode == KeyCodes.KEY_ENTER) {
      clickEventType = ANSWER;
      fireChange();
    } else if (Character.isDigit((char) keyCode) == false) {
      tbAnswer.cancelKey();
    }
    
  }
  
}
