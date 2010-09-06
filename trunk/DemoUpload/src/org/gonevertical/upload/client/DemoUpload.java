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
  	
  	HTML h = new HTML("<a href=\"/mapreduce/status\">See the Map Reduce Demo</a> - only run bible.csv, in root of this project. (actual mapping has been disabled online)");
  	
  	VerticalPanel vp = new VerticalPanel();
  	vp.setWidth("100%");
  	vp.add(fuw);
  	vp.add(new HTML("&nbsp;"));
  	vp.add(h);
  	vp.add(new HTML("&nbsp;"));
  	vp.add(blobs);
  	
  	vp.setCellHorizontalAlignment(fuw, VerticalPanel.ALIGN_CENTER);
  	vp.setCellHorizontalAlignment(blobs, VerticalPanel.ALIGN_CENTER);
  	vp.setCellHorizontalAlignment(h, VerticalPanel.ALIGN_CENTER);
  	
  	RootPanel.get("content").add(vp);
	  
  	blobs.draw();
  }
	
}
