package com.gawkat.demo.client.layout;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FooterWidget extends Composite {
 
	private ClientPersistence cp;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	public FooterWidget(ClientPersistence cp) {
		this.cp = cp;
		
		initWidget(pWidget);
	}
}
