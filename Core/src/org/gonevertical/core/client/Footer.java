package org.gonevertical.core.client;

import org.gonevertical.core.client.ui.feedback.FeedbackWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Footer extends Composite implements ClickHandler {
	
	private FlowPanel pWidget = new FlowPanel();
	
	private HTML hfeedback = new HTML("<a>Feedback</a>");
	
	public Footer() {
		
		initWidget(pWidget);
	}

	public void draw() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(hfeedback);
		
		pWidget.add(hp);
		
		hfeedback.addClickHandler(this);
		
	}

  public void onClick(ClickEvent event) {
  	Widget sender = (Widget) event.getSource();
  	
  	if (sender == hfeedback) {
  		drawFeedbackWidget();
  	}
  	
  }

	private void drawFeedbackWidget() {
		FeedbackWidget fp = new FeedbackWidget();
		fp.center();
  }
	
}
