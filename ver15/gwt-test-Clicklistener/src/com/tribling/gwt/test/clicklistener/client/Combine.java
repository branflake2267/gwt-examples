package com.tribling.gwt.test.clicklistener.client;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Combine  extends Composite implements ClickListener, ChangeListener {



	// panel work with
	private HorizontalPanel pMenu = new HorizontalPanel();
	
	// place to change text of changed color
	private HorizontalPanel pChangedColor = new HorizontalPanel();
	
	// color panel widget
	private ColorPanelWidget wColorPanel = new ColorPanelWidget();

	private VerticalPanel pHelp = new VerticalPanel();
	
	/**
	 * constructor - init this widget
	 */
	public Combine() {

		pMenu.add(new Label("(Combine Widget) Color:"));
		pMenu.add(pChangedColor);

		//setup the widget panels
		VerticalPanel wCombine = new VerticalPanel();
		wCombine.setSpacing(10);
		wCombine.add(pMenu);
		wCombine.add(wColorPanel);
		wCombine.add(pHelp);

		//init widget (using the composite class)
		initWidget(wCombine);
		
		wCombine.addStyleName("combineWidget");
		
		// this observes events in panel widget
		wColorPanel.addChangeListener(this);
		
		setHelp();
	}

	/**
	 * set some help text in the main widget
	 */
	private void setHelp() {
		
		pHelp.add(new HTML("The Combine widget observes changes in the ColorPanelWidget"));
	}

	
	// observer of click events in this widget
	public void onClick(Widget sender) {
	}

	
	// observer for change events using changelistener
	public void onChange(Widget sender) {
		
		//if the sender (sender is a widget) is wColorPanel
		if (sender == wColorPanel) {
			String color = wColorPanel.getColor();
			
			pChangedColor.clear();
			pChangedColor.add(new Label(color));
		}
		
	}

}
