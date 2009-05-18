package com.gawkat.flashcard.client.card;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Menu extends Composite {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private PushButton bPlay = new PushButton("Play");
  
  private PushButton bSettings = new PushButton("Settings");
  
  private HorizontalPanel pPlaySet = new HorizontalPanel();
  
  // TODO
  public Menu() {
    
    // default has settings button
    pPlaySet.add(bSettings);
    
    pWidget.add(pPlaySet);
    
    initWidget(pWidget);
    
    // TODO
    bSettings.setEnabled(false);
    
    // TODO
    bPlay.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        
      }
    });
    
    // TODO
    bSettings.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {

      }
    });
    
  }
  
}
