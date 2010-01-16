package com.gawkat.core.client.tests;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TestWidget extends Composite {

	private VerticalPanel pWidget = new VerticalPanel();
	
	public TestWidget() {
		
		pWidget.add(new HTML("this is my test widget"));
		
		initWidget(pWidget);
	}
	
}
