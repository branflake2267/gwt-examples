package org.gonevertical.demo.client;

import org.gonevertical.demo.client.feedback.FeedbackWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoFeedback implements EntryPoint, ClickHandler {

	// something to activate the feedback popup widget
	private PushButton bFeedback = new PushButton("Send Feedback");
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bFeedback);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(new HTML("Test the feedback dialog box by clicking on the link."));
		vp.add(hp);

		//add to the screeen
		RootPanel.get("content").add(vp);

		Track.track("home");
		
		bFeedback.addClickHandler(this);
	}

  public void onClick(ClickEvent event) {
	  Widget sender = (Widget) event.getSource();
	  
	  if (sender == bFeedback) {
	  	Track.track("Feedback_click");
	  
	  	FeedbackWidget fb = new FeedbackWidget();
	  	fb.center();
		}
	  
  }
  
}
