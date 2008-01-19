package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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

		//debugging
		RootPanel.get().add(new Label("My URL-Path: /GoneVerticalService"));

		RootPanel.get().add(new Label("GWT getModuleBaseURL: " + GWT.getModuleBaseURL()));

		RootPanel.get().add(new Label("GWT getHostPageBaseURL: " + GWT.getHostPageBaseURL()));

		RootPanel.get().add(new Label("GWT getModuleBaseURL: " + GWT.getModuleBaseURL()));
		
		RootPanel.get().add(new Label("GWT getModuleName: " + GWT.getModuleName()));

		//check session cookie first
		final SessionManagerWidget sm = new SessionManagerWidget();
		sm.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {

				if (sm.getLoginStatus() == true) {
					loadPanel(sm.getSessionID());
				}

				if (sm.getLoginStatus() == false) {
					//unloadWidgets();
				}

			}
		});

	}
	
	
	public void loadPanel(String SessionID) {
		
		RootPanel.get().add(new Label("Logged In: SessionID" + SessionID));
		
	}
	
	
}
