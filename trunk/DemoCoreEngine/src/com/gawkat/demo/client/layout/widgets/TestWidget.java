package com.gawkat.demo.client.layout.widgets;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.account.ui.LoginUi;
import org.gonevertical.core.client.account.ui.LoginWidget;

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
		
	}
	
	public void draw() {
		
		if (cp.getLoginStatus() == false) {
			LoginWidget wLogin = LoginWidget.getWidgetStandAlone(cp, LoginUi.LOGIN_VERTICAL);
			pWidget.add(wLogin);
			return;
		}
		
		
	}

}
