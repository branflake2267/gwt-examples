package com.tribling.gwt.test.objectreference.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ObjectReference implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

	  Inputs inputs = new Inputs();
	  inputs.drawForm();
	  
  }
}
