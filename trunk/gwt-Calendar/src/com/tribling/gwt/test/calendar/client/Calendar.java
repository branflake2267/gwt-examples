package com.tribling.gwt.test.calendar.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Calendar implements EntryPoint, ChangeListener {

	// init the calendar
	private CalendarWidget calendar = new CalendarWidget();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// draw the calendar
		calendar.drawCalendarItems();
		
		// display the calendar to page
		RootPanel.get().add(calendar);
		
		// observe the clicks
		calendar.addChangeListener(this);
	}

	/**
	 * observe widgets
	 */
	public void onChange(Widget sender) {
		
		// if calendar fired a change, get a var from it
		if (calendar.getSelectedDay() > 0) {
			int SelectedDay = calendar.getSelectedDay();
			Window.alert("Day Selected: " + SelectedDay);
		}
		
	}
	
	
}
