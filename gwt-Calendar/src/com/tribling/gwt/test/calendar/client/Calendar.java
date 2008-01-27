package com.tribling.gwt.test.calendar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Calendar implements EntryPoint {

	private CalendarWidget calendar = new CalendarWidget();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// observe the clicks
		calendar.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {

				int SelectedDay = 0;
				if (calendar.getSelectedDay() > 0) {
					SelectedDay = calendar.getSelectedDay();

					Window.alert("Day Selected: " + SelectedDay);
				}

			}
		});

		RootPanel.get().add(calendar);
	}
}
