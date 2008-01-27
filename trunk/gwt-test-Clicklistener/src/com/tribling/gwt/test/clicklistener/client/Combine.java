package com.tribling.gwt.test.clicklistener.client;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Combine implements ClickListener {

	private HorizontalPanel pColorTrack = new HorizontalPanel();

	// panels work with
	private HorizontalPanel pMenu = new HorizontalPanel();
	// panel widget
	private PanelWidget pw = new PanelWidget();

	/**
	 * constructor
	 */
	public Combine() {

		pMenu.add(new HTML("Track:&nbsp;"));

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(pMenu);
		hp.add(pColorTrack);

		RootPanel.get().add(hp);
	}

	/**
	 * draw stuff on screen to work with
	 */
	public void draw() {

		// this observes events in panel widget
		pw.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				String color = pw.getColor();

				// Window.alert(color);

				pColorTrack.clear();
				pColorTrack.add(new Label(color));
			}
		});

		// add the panel widget to the page
	}

	// not used yet
	public void onClick(Widget sender) {
	}

}
