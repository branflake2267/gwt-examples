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

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {


		/*
		//Control the users session, UserID = Unique SessionID
		final SessionManagerWidget SessionManager = new SessionManagerWidget();
		SessionManager.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				if (SessionManager.getLoginStatus() == true) {
					loadPanel(SessionManager.getSessionID());
				}

				if (SessionManager.getLoginStatus() == false) {
					//unloadWidgets();
				}
			}
		});
		RootPanel.get("LoginStatus").add(SessionManager);
		*/
		
		
		
		AccountWidget2 Account = new AccountWidget2();
		/*
		Account.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				boolean LoginStatus = Account.getLoginStatus();
				String SessionID = Account.getSessionID();
				
				if (SessionID != null) {
					processSignIn(SessionID, false);
				}
			}
		});
		*/
		
		RootPanel.get().add(Account);
	}
	
	
	public void loadPanel(String SessionID) {
		
		RootPanel.get().add(new Label("Logged In: SessionID" + SessionID));
		
	}
	
	
}
