package com.tribling.gwt.test.feedback.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * make a loading img appear
 * @author branflake2267
 *
 */
public class LoadingWidget extends Composite {

	private VerticalPanel pLoadingWidget = new VerticalPanel();
	
	public LoadingWidget() {
		
		//TODO - move to other loading method
		
		String sImage = GWT.getModuleBaseURL() + "/images/loading2.gif";
	    Image image = new Image(sImage);
		
	    pLoadingWidget.add(image);

	    initWidget(pLoadingWidget);
	    
	    hideLoading();
	}
	
	public void hideLoading() {
		pLoadingWidget.setVisible(false);
	}
	
	public void showLoading() {
		pLoadingWidget.setVisible(true);
	}
	
}
