package com.gawkat.demo.client.layout;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LogoWidget extends Composite {
	
	private ClientPersistence cp;
	
	private VerticalPanel pWidget = new VerticalPanel();
	
	public LogoWidget(ClientPersistence cp) {
		this.cp = cp;
		
		initWidget(pWidget);
		
		drawLogo();
	}
	
	private void drawLogo() {
		String sImage = "../images/logo.png";
    Image image = new Image(sImage);
    pWidget.add(image);
   
    pWidget.setCellHorizontalAlignment(image, HorizontalPanel.ALIGN_CENTER);
	}
	
}
