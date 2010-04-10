package org.gonevertical.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGoogleAnalytics implements EntryPoint, ValueChangeHandler<String> {
	
	private Hyperlink h1 = new Hyperlink("Click Me 1", "clickMe_link_1");
	private Hyperlink h2 = new Hyperlink("Click Me 2", "clickMe_link_2");
	private Hyperlink h3 = new Hyperlink("Click Me 3", "clickMe_link_3");
	private Hyperlink h4 = new Hyperlink("Click Me 4", "clickMe_link_4");
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		initHistoryObservations();
		
		HTML h = new HTML("Watch the http querystring change after #clickMe1...");
		HTML hh = new HTML("Each click will send an history event to Google Analytics.");
		HTML hhh = new HTML("<a href=\"http://gwt-examples.googlecode.com\">gwt-examples.googlecode.com</a> - go back to where you came from");
		
		VerticalPanel pWidget = new VerticalPanel();
		pWidget.add(h);
		pWidget.add(hh);
		pWidget.add(hhh);
		pWidget.add(new HTML("&nbsp;"));
		pWidget.add(h1);
		pWidget.add(h2);
		pWidget.add(h3);
		pWidget.add(h4);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(pWidget);
		vp.setWidth("100%");
		
		vp.setCellHorizontalAlignment(pWidget, HorizontalPanel.ALIGN_CENTER);
		
		RootPanel.get("content").add(vp);
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
