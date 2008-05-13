package com.tribling.gwt.test.richtexteditorpopup.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TextAreaWidget extends Composite implements ClickListener, ChangeListener {

	// Panels
	private VerticalPanel pWidget = new VerticalPanel(); 
	private VerticalPanel pLabel = new VerticalPanel();
	
	// rta input
	private RichTextArea input = new RichTextArea();
	
	// rta pop out editor
	private RTAEditorDialogPopup rtUp = new RTAEditorDialogPopup(); 
	
	
	//TODO - change to bundle?
    private Image expand = new Image(GWT.getModuleBaseURL() + "/images/expand.gif");
	
    
	/**
	 * constructor - init widget
	 */
	public TextAreaWidget() {
		
		expand.setTitle("Enlarge and edit");
		
		HorizontalPanel hpBottomButtons = new HorizontalPanel();
		hpBottomButtons.setSpacing(6);
		hpBottomButtons.add(input);
		hpBottomButtons.add(expand);
		
		// make widget
		pWidget.add(pLabel);
		pWidget.add(hpBottomButtons);
		
		// init widget
		initWidget(pWidget);
		
		// Style
		hpBottomButtons.setWidth("100%");
		pWidget.setWidth("100%");
		input.setWidth("100%");
		input.setHeight("70px");
			
		// observe
		expand.addClickListener(this);
		rtUp.addChangeListener(this);
	}
	
	/**
	 * set rta text
	 */
	private void setRTAText(String text) {
		input.setHTML(text);
	}
				
	/**
	 * observe
	 */
	public void onClick(Widget sender) {
		
		if (sender == expand) {
			rtUp.center();
			rtUp.setRTAText(input.getHTML());
			rtUp.center();
		}
	}

	/**
	 * observe
	 */
	public void onChange(Widget sender) {
		
		if (sender == rtUp) {
			String sel = rtUp.getSelected();
			
			if (sel == null) {
				//skip
			} else if (sel.equals("update")) {
				setRTAText(rtUp.getRTAText());
			}
			
			rtUp.hide();
			//rtUp.clear();
		}
		
		
	}

	
	
	
	
}
