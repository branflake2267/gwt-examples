package com.tribling.gwt.test.calendar.client;


import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Make a day widget with clicklistener to put in the calendar days
 */
public class Day extends Composite {

	private ChangeListenerCollection changeListeners;
	
	// panels
	private VerticalPanel pWidget = new VerticalPanel();
	private HorizontalPanel pDayNumber = new HorizontalPanel();
	private FlowPanel pDayContent = new FlowPanel();
	
	// Vars
	private String[] labels = null;
	private int SelectedDay = 0;		

	
	/**
	 * constructor - init widget
	 */
	public Day() {

		pWidget.add(pDayNumber);
		pWidget.add(pDayContent);

		//init widget
		initWidget(pWidget);
	
		// Style
		pWidget.setStyleName("cal-pDayWidget");
		pDayNumber.setStyleName("cal-pDayNumber");
		pDayContent.setStyleName("cal-pDayContent");

		
	}

	/**
	 * set height of this widget
	 */
	public void setHeight(String s) {
		pWidget.setHeight(s);
	}
	
	/**
	 * draw labels
	 */
	private void drawLabels() {
		//pDayContent.clear();
		
		if (labels != null) {
			for (int i=0; i < labels.length; i++) {
				pDayContent.add(new HTML(labels[i].toString()));
			}
		}
	}
	
	/**
	 * add label to day
	 * @param sLabel
	 */
	public void drawLabel(String sLabel) {
		if (sLabel != null) {
			pDayContent.add(new HTML(sLabel));
		}
	}
	
	/**
	 * clear labels for day
	 */
	public void clearLabels() {
		pDayContent.clear();
	}
	
	/*
	 * get selected day
	 */
	public int getDaySelected() {
		return this.SelectedDay;
	}

	/**
	 * set the number(day) that represents this widget
	 */
	public void setDayNumber(int DayNumber) {
		this.SelectedDay = DayNumber;
		pDayNumber.add(new Label(Integer.toString(this.SelectedDay)));
	}

	/**
	 * set day selected
	 * @param DaySelected
	 */
	public void setDaySelected(int DaySelected) {
		this.SelectedDay = DaySelected;
	}
	
	/**
	 * set the labels that go in this widget
	 * @param labels
	 */
	public void setLabels(String[] labels) {
		this.labels = labels;
		drawLabels();
	}

}//end daywidget class
