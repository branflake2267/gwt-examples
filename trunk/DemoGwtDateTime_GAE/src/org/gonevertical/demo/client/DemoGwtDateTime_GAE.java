package org.gonevertical.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGwtDateTime_GAE implements EntryPoint {


  public void onModuleLoad() {

  	DateTimeWidget w = new DateTimeWidget();
  	
	  RootPanel.get().add(w);
	  
  }
	
}
