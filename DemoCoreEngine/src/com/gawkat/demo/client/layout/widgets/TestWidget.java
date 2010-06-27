package com.gawkat.demo.client.layout.widgets;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.ui.login.LoginUi;
import org.gonevertical.core.client.ui.login.LoginWidget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TestWidget extends Composite {
	
	private ClientPersistence cp;
	
	//private LoginWidget wLogin = null;
	
	private VerticalPanel pContentLoggedIn = new VerticalPanel();
	private VerticalPanel pContentLoggedOut = new VerticalPanel();
	
	public TestWidget(ClientPersistence cp) {
		this.cp = cp;
		
		//wLogin = LoginWidget.getInstance(cp, LoginUi.LOGIN_VERTICAL);
		
		VerticalPanel pWidget = new VerticalPanel();
		pWidget.add(pContentLoggedOut);
		//pWidget.add(wLogin);
		pWidget.add(pContentLoggedIn);
		
		
		initWidget(pWidget);
		
		// observe login events
		if (cp != null) {
  		cp.addChangeHandler(new ChangeHandler() {
  			public void onChange(ChangeEvent event) {
  				ClientPersistence wcp = (ClientPersistence) event.getSource();
  				if (wcp.getChangeEvent() == EventManager.USER_LOGGEDIN) {
  					drawLoggedIn();
  				} else if (wcp.getChangeEvent() == EventManager.USER_LOGGEDOUT) {
  					drawLoggedOut();
  				}
  			}				
  		});
		}
	}
	
	public void draw() {
		
		if (cp.getLoginStatus() == false) {
			drawLoggedOut();
			return;
		}
		
		drawLoggedIn();
	}

	private void drawLoggedOut() {
		//wLogin.setVisible(true);
		pContentLoggedIn.setVisible(false);
		pContentLoggedOut.setVisible(true);
		
		pContentLoggedOut.clear();
		pContentLoggedOut.add(new HTML("Try the demo login: username: demo_user and password: password"));
		pContentLoggedOut.add(new HTML("&nbsp;"));
	}
	
	private void drawLoggedIn() {
		//wLogin.setVisible(false);
		pContentLoggedIn.setVisible(true);
		pContentLoggedOut.setVisible(false);
		
		pContentLoggedIn.clear();
		pContentLoggedIn.add(new HTML("Good Job! You have successfully logged in."));
  }
	
}
