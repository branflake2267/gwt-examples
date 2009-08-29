package com.gawkat.core.client.global;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DeleteDialog extends DialogBox implements ClickHandler {

	// Buttons
	private PushButton bYes = new PushButton("Yes");
	private PushButton bNo = new PushButton("No");
	
	private int changeEvent = 0;
	
	/**
	 * constructor - init widget
	 */
	public DeleteDialog() {
		
		setText("Are you sure you want to delete?");
		
		HorizontalPanel hpButtons = new HorizontalPanel();
		hpButtons.setSpacing(20);
		hpButtons.add(bYes);
		hpButtons.add(bNo);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(hpButtons);
		
		// init Widget
		setWidget(vp);
		
		// Observe
		bYes.addClickHandler(this);
		bNo.addClickHandler(this);
		
		// Style
		vp.setWidth("100%");
		vp.setCellHorizontalAlignment(hpButtons, HorizontalPanel.ALIGN_CENTER);
	}
	
	/**
	 * set Text to something different than delete
	 * @param s
	 */
	public void changeText(String s) {
		setText(s);
	}
	
  public void onClick(ClickEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == bYes) {
      fireChange(EventManager.DELETE_YES);
    } else if (sender == bNo) {
      fireChange(EventManager.DELETE_NO);
    }
    this.hide();
  }

  public int getChangeEvent() {
    return changeEvent;
  }
  
  private void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

}
