package org.gonevertical.MultiFileUpload.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGAEMultiFileUpload implements EntryPoint {

  public void onModuleLoad() {

    DirUploader duw = new DirUploader();
   
    VerticalPanel vp = new VerticalPanel();
    vp.add(duw);
    
    RootPanel.get().add(vp);
    
    vp.setWidth("100%");
    vp.setCellHorizontalAlignment(duw, HorizontalPanel.ALIGN_CENTER);
   
    vp.setStyleName("test1");
  }
 
}
