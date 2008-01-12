package com.tribling.gwt.test.objectreference.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class Inputs {

	private final VerticalPanel vp = new VerticalPanel();
	private Widget[] objects;
	
	/**
	 * constructor
	 */
	public Inputs() {
		
		
		Widget[] objects = new Widget[4];
		
		TextArea ta = new TextArea();
		objects[0] = ta;
		
		TextBox tb = new TextBox();
		objects[1] = tb;
		
		TextBox tb2 = new TextBox();
		objects[2] = tb2;
		
		PushButton save = new PushButton("Save");
		objects[3] = save;
		
		
		this.setObjects(objects);
	}
	
	/**
	 * Make form of objects 
	 */
	public void drawForm() {
	
		vp.add(new Label("test"));
		
		RootPanel.get().add(vp);
		
		this.drawObjects();
	}
	
	public void setObjects(Widget[] objects) {
		this.objects = objects;
	}
	
	
	public void drawObjects() {
		
		for (int i=0; i < this.objects.length; i++) {
			vp.add(this.objects[i]);
		}
		
	}
	
	
}
