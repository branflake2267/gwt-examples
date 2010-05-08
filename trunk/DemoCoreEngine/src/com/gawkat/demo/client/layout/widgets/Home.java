package com.gawkat.demo.client.layout.widgets;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Home extends Composite {
	
	private ClientPersistence cp;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	public Home(ClientPersistence cp) {
		this.cp = cp;
		
		initWidget(pWidget);
	}

	public void draw() {
	  
  }
	
}
