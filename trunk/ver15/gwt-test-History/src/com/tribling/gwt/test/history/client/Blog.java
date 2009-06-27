package com.tribling.gwt.test.history.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Blog extends Composite {

	private VerticalPanel pWidget = new VerticalPanel();
	
	private Hyperlink hWiki = new Hyperlink("Goto Wiki", "wiki");
	
	/** 
	 * constructor - init widget
	 */
	public Blog() {
		
		pWidget.add(new HTML("Blog Opened"));
		pWidget.add(hWiki);
		
		// init widget
		initWidget(pWidget);
		
		pWidget.setStyleName("blog");
	}
	
}
