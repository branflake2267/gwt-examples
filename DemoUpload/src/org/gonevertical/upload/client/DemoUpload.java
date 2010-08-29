package org.gonevertical.upload.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoUpload implements EntryPoint {

  public void onModuleLoad() {
	  
  	FileUploaderWidget fuw = new FileUploaderWidget();
  	
  	Blobs blobs = new Blobs();
  	
  	
  	VerticalPanel vp = new VerticalPanel();
  	vp.setWidth("100%");
  	vp.add(fuw);
  	vp.add(new HTML("&nbsp;"));
  	vp.add(blobs);
  	
  	vp.setCellHorizontalAlignment(fuw, VerticalPanel.ALIGN_CENTER);
  	
  	RootPanel.get("content").add(vp);
	  
  	blobs.draw();
  }
	
}
