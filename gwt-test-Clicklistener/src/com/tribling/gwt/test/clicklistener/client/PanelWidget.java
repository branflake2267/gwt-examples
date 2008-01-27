package com.tribling.gwt.test.clicklistener.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PanelWidget extends Composite implements ClickListener {

	// my buttons to listen to
	private Button bChangeColor = new Button("Change Color");

	// observe/listener this widget from another widget
	private ChangeListenerCollection changeListeners;

	// defines the content area
	private HorizontalPanel pContent = new HorizontalPanel();

	// main widget panel
	VerticalPanel pMain = new VerticalPanel();

	private String randomColor = null;

	/**
	 * constructor - setup this widget
	 */
	public PanelWidget() {

		// make a menu of buttons
		HorizontalPanel menu = new HorizontalPanel();
		menu.setStyleName("prw-Menu");
		menu.add(new Label("Buttons to listen to: "));
		menu.add(bChangeColor);

		// content panel - for color
		pContent.setStyleName("prw-Content");

		// main widget panel
		pMain.add(menu);
		pMain.add(pContent);

		// init the widget - has to be done before methods used on it
		initWidget(pMain);

		// Observe to the button
		bChangeColor.addClickListener(this);
	}

	/**
	 * use this to observe this widget from another widget
	 * 
	 * @param listener
	 */
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null)
			changeListeners = new ChangeListenerCollection();
		changeListeners.add(listener);
	}

	/**
	 * change the color of the background of the content element
	 * 
	 * @param Color
	 */
	public void changePanelColor() {
		String randomColor = getRandomColor();
		DOM.setStyleAttribute(pContent.getElement(), "background", randomColor);

		pContent.clear();
		pContent.add(new Label("Color: " + this.randomColor));
	}

	public String getColor() {
		return this.randomColor;
	}

	private String getRandomColor() {

		String hex1 = getRandomHex();
		String hex2 = getRandomHex();
		String hex3 = getRandomHex();
		String hex4 = getRandomHex();
		String hex5 = getRandomHex();
		String hex6 = getRandomHex();

		String color = "#" + hex1 + hex2 + hex3 + hex4 + hex5 + hex6;

		this.randomColor = color;

		return color;
	}

	/**
	 * get random hex
	 * 
	 * @return int
	 */
	private String getRandomHex() {
		String[] hex = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "A", "B", "C", "D", "E", "F" };
		int randomNum = Random.nextInt(hex.length);
		String sHex = hex[randomNum];
		return sHex;
	}

	/**
	 * when clicked then process the ID
	 */
	public void onClick(Widget sender) {

		if (sender == bChangeColor) { // if the change color button was
										
			this.changePanelColor();

		}

		// fire a change listener, which another widget can listen/observe ALL
		// clicks in this entire widget
		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}

	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null)
			changeListeners.remove(listener);
	}

}
