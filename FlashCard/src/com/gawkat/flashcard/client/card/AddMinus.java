package com.gawkat.flashcard.client.card;

import com.gawkat.flashcard.client.card.images.Images;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AddMinus extends Composite implements ClickHandler, HasChangeHandlers {

  private static final int PLUS = 1;
  private static final int MINUS = 2;
  
  private int clicked = 0;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private PushButton bPlus = new PushButton("+");
  private PushButton bMinus = new PushButton("-");
  
  public AddMinus() {

    pWidget.add(bPlus);
    pWidget.add(bMinus);
    
    initWidget(pWidget);
    
    bPlus.addClickHandler(this);
    bMinus.addClickHandler(this);
    
    bPlus.addStyleName("flashcard-bplusminus");
    bMinus.addStyleName("flashcard-bplusminus");
  }

  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bPlus) {
      clicked = PLUS;
    } else if (sender == bMinus) {
      clicked = MINUS;
    }
    
    fireChange();
  }
  
  /**
   * fire a change event
   */
  private void fireChange() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }

  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }
  
}
