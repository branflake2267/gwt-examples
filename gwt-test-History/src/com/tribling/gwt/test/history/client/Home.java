package com.tribling.gwt.test.history.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Home extends Composite {
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	private Hyperlink h1 = new Hyperlink("Goto Blog", "blog");
	private Hyperlink h2 = new Hyperlink("Goto Wiki", "wiki");
	
	/** 
	 * constructor - init widget
	 */
	public Home() {
		
		pWidget.add(new HTML("Home Page"));
		pWidget.add(h1);
		pWidget.add(h2);
		
		// init widget
		initWidget(pWidget);
		
		pWidget.setStyleName("home");
	}
}
