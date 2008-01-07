package com.tribling.gwt.test.clicklistener.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class BasicClick extends MyApplication {

	/**
	 * constructor
	 */
	public BasicClick() {

	}

	/**
	 * basic clicklistener method - listing to button
	 */
	public void basicClickApp() {

		final Button button = new Button("Click me");
		final Label label = new Label();

		button.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				if (label.getText().equals(""))
					label.setText("Hello World!");
				else
					label.setText("");
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(button);
		hp.add(label);

		RootPanel.get().add(hp);

	}// end basicClickApp

}
