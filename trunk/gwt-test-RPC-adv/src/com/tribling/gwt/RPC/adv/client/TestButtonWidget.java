package com.tribling.gwt.RPC.adv.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


public class TestButtonWidget extends Composite implements ClickListener {

	final public HorizontalPanel horz = new HorizontalPanel();
	private PushButton[] myBtns = new PushButton[0];
	
	private int iBtns;
	
	
	public TestButtonWidget() {	
	}
	
	
	
	public void onClick(Widget sender) {
		
		Window.alert("iBtns: "+ iBtns);
		
		Window.alert(sender.toString());
		
		for(int i = 0; i < iBtns; i++) {
		
			if (sender == myBtns[i]) {
				Window.alert("iBtns: "+ iBtns +" Index:" + i);
			}
			
		}
		
	}

	
	
	
	public void renderButtons() {
		
		PushButton[] myBtns = new PushButton[2];
		iBtns = 2;
		
		myBtns[0] = new PushButton("Yes", "Done", this);
		myBtns[1] = new PushButton("Yes2","Done2", this);
		
		horz.add(myBtns[0]);
		horz.add(myBtns[1]);
		
		RootPanel.get("testButtons").add(horz);
	}
	
	
}
