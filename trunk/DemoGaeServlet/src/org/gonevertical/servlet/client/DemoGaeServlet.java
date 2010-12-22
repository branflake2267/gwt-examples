package org.gonevertical.servlet.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGaeServlet implements EntryPoint {


  public void onModuleLoad() {
    
    HTML w = new HTML("GWT Loaded.<br/><br/>");
    
    RootPanel.get().add(w);
    
    // org.gonevertical.servlet.servlets.Index - puts in the value
    Element e = DOM.getElementById("host");
    String s = null;
    if (e != null) {
      s = e.getAttribute("value");
    }
    
    Window.alert("GWT says the host is: " + s);
  }
 
}
