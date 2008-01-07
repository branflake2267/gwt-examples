package com.tribling.gwt.test.clicklistener.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PanelReloadWidget extends Composite implements ClickListener {

	// my buttons to listen to
	final private Button changeColor = new Button("Change Color");
	final private Button changeText = new Button("Change Text");

	// defines the content area
	final private HorizontalPanel content = new HorizontalPanel();

	// the id I want to pass around
	private String myID; // used with text button
	private String Color; // used with color button

	// change listeners for this widget
	private ChangeListenerCollection changeListeners;

	/**
	 * constructor - setup this widget
	 */
	public PanelReloadWidget() {

		// Observe to the buttons
		changeColor.addClickListener(this);
		changeText.addClickListener(this);

		// menu top
		HorizontalPanel menu = new HorizontalPanel();
		menu.setStyleName("prw-Menu");
		menu.add(new Label("PanelReloadWidget: Menu Area: "));
		menu.add(changeColor);
		menu.add(changeText);

		// content panel
		content.setStyleName("prw-Content");
		content.add(new Label("PanelReloadWidget: Content Area: "));

		// main widget panel
		VerticalPanel Widgetpanel = new VerticalPanel();
		Widgetpanel.add(menu);
		Widgetpanel.add(content);

		// init the widget - has to be done before methods used on it
		initWidget(Widgetpanel);
	}

	/**
	 * set ID so I can pass it around
	 */
	private void setMyID() {
		this.myID = "10";
	}

	/**
	 * Get this ID
	 * 
	 * @return
	 */
	public String getMyID() {
		return this.myID;
	}

	/**
	 * set color
	 */
	public void setColor() {
		this.Color = "red";
	}

	/**
	 * get color
	 */
	public String getColor() {
		return this.Color;
	}

	/**
	 * change the color of the bacground of the content element
	 * 
	 * @param Color
	 */
	public void changePanelColor(String Color) {

		// Window.alert("changing bg color" + Color);

		DOM.setStyleAttribute(content.getElement(), "background", Color);
	}

	/**
	 * when clicked then process the ID
	 */
	public void onClick(Widget sender) {

		// class method
		if (sender == changeColor) {
			this.setColor();
		} else if (sender == changeText) {
			this.setMyID();
		}

		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}

	/**
	 * use this to listen to the widget
	 * 
	 * @param listener
	 */
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null)
			changeListeners = new ChangeListenerCollection();
		changeListeners.add(listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null)
			changeListeners.remove(listener);
	}

}
