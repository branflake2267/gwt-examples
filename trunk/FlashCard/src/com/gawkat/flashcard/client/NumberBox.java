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
    //hp.add(tbNumber);
    hp.add(pText);
    
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    // style
    hp.setWidth("100%");
    pText.setWidth("100%");
    
    
    // TODO
    tbNumber.addClickHandler(this);
    tbNumber.addBlurHandler(this);
    
    //hp.addStyleName("test1");
    //pText.addStyleName("test2");
    //pWidget.addStyleName("test3");
  }

  public void setNumber(int i) {
    this.inumber = i;
    drawText();
  }
  
  public void animate(boolean b) {
    
    if (b == true) {
      
    } else if (b == false) {
      
    }
    
  }
    
  private void drawInputBox() {
    pWidget.clear();
    // TODO - draw small size then grow it to 100%
  }
  
  private void drawText() {
    pText.clear();
    HTML h = new HTML(Integer.toString(inumber));
    h.setStyleName("flashcard-minmax");
    pText.add(h);
    
    pText.setCellHorizontalAlignment(h, HorizontalPanel.ALIGN_CENTER);
  }
  
  public void onClick(ClickEvent event) {
    
  }

  public void onBlur(BlurEvent event) {
    
  }
  

}
