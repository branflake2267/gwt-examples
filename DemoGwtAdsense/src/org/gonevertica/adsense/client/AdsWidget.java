package org.gonevertica.adsense.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class AdsWidget extends Composite {
	
	private VerticalPanel verticalPanel;
	private Frame frame;

	
	public AdsWidget() {
		verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(verticalPanel);
		verticalPanel.setWidth("100%");
		
		frame = new Frame();
		frame.setStyleName("adsframe");
		verticalPanel.add(frame);
		verticalPanel.setCellVerticalAlignment(frame, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setCellHorizontalAlignment(frame, HasHorizontalAlignment.ALIGN_CENTER);
		frame.setSize("800px", "110px");
		
		DOM.setElementProperty(frame.getElement(), "frameBorder", "0");
		DOM.setElementProperty(frame.getElement(), "scrolling", "0");
	}

  public void draw() {
    frame.setUrl("/top_ads.html");
  }
	

  public VerticalPanel getVerticalPanel() {
    return verticalPanel;
  }
  
  public Frame getFrame() {
    return frame;
  }
  
}
