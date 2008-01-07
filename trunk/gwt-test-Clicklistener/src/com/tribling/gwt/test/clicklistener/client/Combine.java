package com.tribling.gwt.test.clicklistener.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Combine implements ClickListener {

	String sAlert = null;

	/**
	 * constructor
	 */
	public Combine() {
	}

	/**
	 * draw stuff on screen to work with
	 */
	public void draw() {

		// Panel Reload Widget
		final PanelReloadWidget prw = new PanelReloadWidget();

		// panel reload widget clicklistener
		prw.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {

				String Id = prw.getMyID();
				String color = prw.getColor();

				if (Id != null) {
					sAlert = Id;
				} else if (color != null) {
					sAlert = color;
					prw.changePanelColor(color);
				}

				Window.alert("Selected: " + sAlert);
			}
		});

		// add the panel reload widget to the text panel id
		RootPanel.get("testPanel").add(prw);

	}

	public void onClick(Widget sender) {
	}

}
