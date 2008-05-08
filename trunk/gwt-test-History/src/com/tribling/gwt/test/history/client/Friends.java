package com.tribling.gwt.test.history.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Friends extends Composite {

	private VerticalPanel pWidget = new VerticalPanel();
	
	/** 
	 * constructor - init widget
	 */
	public Friends() {
		
		pWidget.add(new HTML("Friends Opened"));
		
		// init widget
		initWidget(pWidget);
		
		pWidget.setStyleName("friends");
	}
	
	/**
	 * set friends ID
	 * @param 
	 */
	public void setID(int id) {
				
		switch (id) {	
		case 1:
			pWidget.add(new Label("id 1 selected : Brandon"));
			break;
		case 2:
			pWidget.add(new Label("id 2 selected : Joel"));
			break;

		}
	}
	
	/**
	 * set another param
	 * 
	 * @param s
	 */
	public void setAnotherParam(String s) {
		pWidget.add(new Label("Another Param: " + s));
	}
}
