package org.gonevertical.servlet.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGaeServlet implements EntryPoint {


  public void onModuleLoad() {
    
    HTML w = new HTML("GWT Loaded.<br/><br/>");
    
    RootPanel.get().add(w);
    
  }
 
}
