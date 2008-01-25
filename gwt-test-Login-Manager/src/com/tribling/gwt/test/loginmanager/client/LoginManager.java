package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoginManager implements EntryPoint {

	final SessionManagerWidget SessionManager = new SessionManagerWidget();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {



		//Control the users session, UserID = Unique SessionID
		SessionManager.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				if (SessionManager.getLoginStatus() == true) {
					//load widgets with session
					loadPanel(SessionManager.getSessionID());
				}

				if (SessionManager.getLoginStatus() == false) {
					//unload widgets
					
				}
			}
		});
		RootPanel.get("LoginStatus").add(SessionManager);
		
	}
	
	
	public void loadPanel(String SessionID) {
		
		RootPanel.get().add(new Label("LoadWidgets b/c Logged In: SessionID: " + SessionID));
		
	}
	
	
}
