package com.tribling.gwt.test.mysqlconn.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * make a loading img appear
 * 
 * @author branflake2267
 *
 */
public class LoadingWidget extends Composite {

	private HorizontalPanel pWidget = new HorizontalPanel();
	private HTML html = new HTML();
	
	/**
	 * init widget
	 */
	public LoadingWidget() {
		
		//TODO - move to other loading method
		String sImage = GWT.getModuleBaseURL() + "/images/loading2.gif";
	    Image image = new Image(sImage);
		
	    pWidget.add(new HTML("&nbsp;"));
	    pWidget.add(image);
	    pWidget.add(new HTML("&nbsp;"));
	    pWidget.add(html);

	    initWidget(pWidget);
	    
	    pWidget.setStyleName("loadingImage");
	    
	    hide();
	}
	
	/**
	 * hide
	 */
	public void hide() {
		pWidget.setVisible(false);
	}
	
	/**
	 * show
	 */
	public void show() {
		pWidget.setVisible(true);
	}
	
	/**
	 * show with text
	 * @param s
	 */
	public void show(String s) {
		show();
		setHTML(s);
	}
	
	/**
	 * set text for loading
	 * @param s
	 */
	public void setHTML(String s) {
		html.setVisible(true);
		html.setHTML(s);
	}

	/**
	 *  hide timed
	 */
	public void hideTimed() {
		pWidget.setVisible(true);
		
		Timer t = new Timer() {
			public void run() {
				pWidget.setVisible(false);
			}
		};
		t.schedule(3000);
	}

}
