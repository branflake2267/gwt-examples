package com.gawkat.demo.client.layout.widgets;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.account.ui.LoginUi;
import org.gonevertical.core.client.account.ui.LoginWidget;
import org.gonevertical.core.client.global.EventManager;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TestWidget extends Composite {
	
	private ClientPersistence cp;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	public TestWidget(ClientPersistence cp) {
		this.cp = cp;
		
		initWidget(pWidget);
		
		// observe login events
		cp.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				ClientPersistence wcp = (ClientPersistence) event.getSource();
				if (wcp.getChangeEvent() == EventManager.LOGGEDIN) {
					drawWidget();
				} else if (wcp.getChangeEvent() == EventManager.LOGGEDOUT) {
					pWidget.clear();
					drawLogin();
				}
			}				
		});	
	}
	
	public void draw() {
		
		if (cp.getLoginStatus() == false) {
			drawLogin();
			return;
		}
		
		drawWidget();
	}

	private void drawLogin() {
		LoginWidget wLogin = LoginWidget.getInstance(cp, LoginUi.LOGIN_VERTICAL);
		pWidget.add(wLogin);
	}
	
	private void drawWidget() {
		pWidget.clear();
		pWidget.add(new HTML("Good Job! You have successfully logged in."));
  }
	
}
