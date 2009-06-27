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

/**
 *  Making a panel that changes color on click of a button
 *  
 *  using composite to make this a widget in it self
 *  
 * @author branflake2267
 *
 */
public class ColorPanelWidget extends Composite implements ClickListener {

	// my buttons to listen to
	private Button bChangeColor = new Button("Change Color");

	// observe/listener this widget from another widget
	private ChangeListenerCollection changeListeners;

	// defines the content area
	private HorizontalPanel pContent = new HorizontalPanel();

	// main widget panel
	VerticalPanel pMain = new VerticalPanel();

	// Var to remember
	private String randomColor = null;

	
	
	/**
	 * constructor - setup this widget
	 */
	public ColorPanelWidget() {

		// make a menu of buttons
		HorizontalPanel menu = new HorizontalPanel();
		menu.setStyleName("cpw-Menu");
		menu.add(new Label("(ColorPanelWidget) Keep Clicking"));
		menu.add(bChangeColor);

		// main widget panel 
		pMain.add(menu);
		pMain.add(pContent);

		// init the widget - has to be done before methods used on it
		initWidget(pMain);

		// content panel - for color
		pContent.setStyleName("cpw-Content");

		// Observe the button
		bChangeColor.addClickListener(this);
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

	/**
	 * get random color from this widget
	 * @return
	 */
	public String getColor() {
		return this.randomColor;
	}

	/**
	 * generate a random hex
	 * @return
	 */
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
	 * Observers - when clicked then process the ID
	 * 
	 * Used to fire a change listener
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

	
	/**
	 * Add change listener to watch for changes
	 * 
	 * @param listener
	 */
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null)
			changeListeners = new ChangeListenerCollection();
		changeListeners.add(listener);
	}
	
	/**
	 * Remove Change Listener
	 * @param listener
	 */
	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null)
			changeListeners.remove(listener);
	}

}
