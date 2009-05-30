package com.gawkat.flashcard.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NumberBox extends Composite implements ClickHandler, BlurHandler {
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private TextBox tbNumber = new TextBox();
  
  private HorizontalPanel pText = new HorizontalPanel();
  
  private int inumber = 0;
  
  public NumberBox() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(tbNumber);
    hp.add(pText);
    
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    pWidget.setVisible(false);
    
    tbNumber.addClickHandler(this);
    tbNumber.addBlurHandler(this);
    
  }

  public void setNumber(int i) {
    this.inumber = i;
  }
  
  public void display(boolean b) {
    
    if (b == true) {
      pWidget.setVisible(true);
    } else if (b == false) {
      pWidget.setVisible(false);
    }
    
  }
    
  private void drawInputBox() {
    pWidget.clear();
    // TODO - draw small size then grow it to 100%
  }
  
  private void drawText(boolean b) {
    pText.clear();
    HTML h = new HTML(Integer.toString(inumber));
    h.setStyleName("flashcard-numbertxt");
    pText.add(h);
  }
  
  public void onClick(ClickEvent event) {
    
  }

  public void onBlur(BlurEvent event) {
    
  }
  

}
