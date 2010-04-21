package org.gonevertical.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoLogging implements EntryPoint {

  public void onModuleLoad() {
	 
  	LoggingWidget w = new LoggingWidget();
  	RootPanel.get("content").add(w);
	  
  }
  
}
