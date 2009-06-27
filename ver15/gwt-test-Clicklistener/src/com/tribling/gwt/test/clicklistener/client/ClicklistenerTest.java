package com.tribling.gwt.test.clicklistener.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ClicklistenerTest implements EntryPoint {

	
	
	/**
	* This is the entry point method.
	*/
	public void onModuleLoad() {
		
		//I use composite class to init this widget
		Combine combine = new Combine();
		
		RootPanel.get("content").add(new HTML("<a href=\"http://code.google.com/p/gwt-examples/\">Back to GWT-Examples</a>"));
		RootPanel.get("content").add(new HTML("&nbsp;"));
		
		
		//add it to the content div in ClicklistenerTest.html
		RootPanel.get("content").add(combine);
		
		
		
	}
	
	
}
