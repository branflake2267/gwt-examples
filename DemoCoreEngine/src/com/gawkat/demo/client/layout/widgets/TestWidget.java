package com.gawkat.demo.client.layout.widgets;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TestWidget extends Composite {
	
	private ClientPersistence cp;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	public TestWidget(ClientPersistence cp) {
		this.cp = cp;
		
		initWidget(pWidget);
		
	}
	
	public void draw() {
		
		if (cp.getLoginStatus() == false) {
			return;
		}
		
		
	}

}
