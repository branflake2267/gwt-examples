package com.tribling.gwt.test.feedback.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Feedback implements EntryPoint, ClickListener {

	//hyper link for feedback box
	Hyperlink hFeedback;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		hFeedback = new Hyperlink("Feedback - Click me", "feedback");

		VerticalPanel vp = new VerticalPanel();
		vp.add(new HTML("Test the feedback dialog box by clicking on the link."));
		vp.add(hFeedback);

		//add to the screeen
		RootPanel.get("content").add(vp);

		hFeedback.addClickListener(this);
	}

	public void onClick(Widget sender) {

		if (sender == hFeedback) {
			FeedbackWidget fb = new FeedbackWidget();
			fb.setSessionID(""); // example - I use SessionID to pass around my session var for user
			fb.center();
		}

	}
}
