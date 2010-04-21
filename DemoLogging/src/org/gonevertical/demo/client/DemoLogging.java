package org.gonevertical.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoLogging implements EntryPoint, ValueChangeHandler<String> {

  public void onModuleLoad() {
	 
  	LoggingWidget w = new LoggingWidget();
  	RootPanel.get("content").add(w);
	  
  	initHistoryObservations();
  }
  
  /**
	 * watch the historyTokens after the querystring#historyToken
	 *   or you could say querystring#[historyEvent|applicationState]
	 */
	private void initHistoryObservations() {
		
		History.addValueChangeHandler(this);
	 
		// first load
		Track.track("home");
  }

  public void onValueChange(ValueChangeEvent<String> event) {
	  
  	// get the querystring token
  	String historyToken = History.getToken();
  	
  	// send to static method that will send the __utm.gif to google's server fro tracking
  	Track.track(historyToken);
	  
  }
  
}
