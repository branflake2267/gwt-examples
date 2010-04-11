package org.gonevertical.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoMySqlConn implements EntryPoint, ValueChangeHandler<String> {


	public void onModuleLoad() {
		
		initHistoryObservations();
		
		HTML h1 = new HTML("<a href=\"http://gwt-examples.googlecode.com\">Back to gwt-examples.googlecode.com</a>");
		HTML h2 = new HTML("Example of using MySql. Getting data from the server and bringing it back in an array object.");

		BibleInfoWidget wBibleInfo = new BibleInfoWidget();
		
		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.add(h1);
		vp.add(h2);
		vp.add(wBibleInfo);
		vp.setCellHorizontalAlignment(wBibleInfo, HorizontalPanel.ALIGN_CENTER);
		
		// add to dom
		RootPanel.get("content").add(vp);
		
		
		wBibleInfo.draw();
		
		//vp.addStyleName("test1");
		//wBibleInfo.addStyleName("test2");
		
		
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
