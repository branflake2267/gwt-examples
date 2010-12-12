package org.gonevertical.MultiFileUpload.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGAEMultiFileUpload implements EntryPoint {

  public void onModuleLoad() {

    DirUploaderWidget duw = new DirUploaderWidget();
    
    RootPanel.get().add(duw);
    
  }
 
}
