package com.gawkat.flashcard.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FlashCard implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    RootPanel.get("content").add(vp);
    
    Navigation nav = new Navigation();
    vp.add(nav);
    nav.start();
    
    vp.setCellHorizontalAlignment(nav, HorizontalPanel.ALIGN_CENTER);
    
  }
  
  
}
