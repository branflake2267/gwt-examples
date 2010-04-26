package com.gawkat.flashcard.client.card;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AddMinus extends Composite implements ClickHandler, HasChangeHandlers {

  public static final int PLUS = 1;
  public static final int MINUS = 2;
  
  private int change = 0;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private HTML bPlus = new HTML("+");
  private HTML bMinus = new HTML("-");
  
  public AddMinus() {

    pWidget.add(bPlus);
    pWidget.add(bMinus);
    
    initWidget(pWidget);
    
    bPlus.addClickHandler(this);
    bMinus.addClickHandler(this);
    
    bPlus.addStyleName("flashcard-bplusminus");
    bMinus.addStyleName("flashcard-bplusminus");
    pWidget.setCellHorizontalAlignment(bPlus, HorizontalPanel.ALIGN_CENTER);
    pWidget.setCellHorizontalAlignment(bMinus, HorizontalPanel.ALIGN_CENTER);
    
    
    //pWidget.addStyleName("test1");
    

    this.setVisible(false);
  }
  
  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bPlus) {
      change = PLUS;
    } else if (sender == bMinus) {
      change = MINUS;
    }
    
    fireChange();
  }
  
  public int getChange() {
    return this.change;
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
