package com.tribling.gwt.test.richtexteditorpopup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RichTextEditorPopUp implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// testing rta input
		TextAreaWidget taInput = new TextAreaWidget();
		
		// label
		Label lFirst = new Label("Rich Text Editor, dialogbox popup editor has bug");
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(lFirst);
		vp.add(taInput);
		vp.setWidth("450px");
		
		RootPanel.get().add(vp);
		
	}
}
