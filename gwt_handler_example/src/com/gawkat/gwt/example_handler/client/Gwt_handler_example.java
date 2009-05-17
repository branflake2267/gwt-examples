package com.gawkat.gwt.example_handler.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt_handler_example implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
        
    Combine c = new Combine();
    RootPanel.get().add(c);
   
    
    String s = "";
    s += "<li>This example uses addClickHandler, addChangeHandler and integrated Google Analytics tracking.<br> ";
    s += "<li>I observe events in a composite widget and its interior widgets using the handlers.<br>";
    s += "<li>This is also deployed using Google App Engine.<br>";
    s += "<li>I fire a change event to notify the event handler of change<br>";
    
    HTML h = new HTML(s);
    RootPanel.get().add(h);
  }
}
