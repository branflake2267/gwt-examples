package com.tribling.gwt.test.history.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Wiki extends Composite {

	private VerticalPanel pWidget = new VerticalPanel();
	
	/** 
	 * constructor - init widget
	 */
	public Wiki() {
		
		pWidget.add(new HTML("Wiki Opened"));
		
		// init widget
		initWidget(pWidget);
	
		
		pWidget.setStyleName("wiki");
	}
	
}
