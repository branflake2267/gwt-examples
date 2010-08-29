package org.gonevertical.upload.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoUpload implements EntryPoint {

  public void onModuleLoad() {
	  
  	FileUploaderWidget fuw = new FileUploaderWidget();
  	
  	VerticalPanel vp = new VerticalPanel();
  	vp.setWidth("100%");
  	vp.add(fuw);
  	
  	vp.setCellHorizontalAlignment(fuw, VerticalPanel.ALIGN_CENTER);
  	
  	RootPanel.get("content").add(vp);
	  
  }
	
}
