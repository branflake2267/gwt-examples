package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoginManager implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

	  //check session cookie first
	  LoginManagerSessionCheck sc = new LoginManagerSessionCheck();
	  
	  
	  
	  
	  //display the widget
	  LoginPanelWidget lpw = new LoginPanelWidget();
	  lpw.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {

				//do anything u want here?
				//Window.alert("Sign In Selected");
				
				//notify another widget, use widgets methods here to do stuff
				//in widget's onclick method set a variable/object
				//then use widgets method right here to get the variable/object
				
			}
		});

	  
	  RootPanel.get().add(lpw);
	  
  }
}
