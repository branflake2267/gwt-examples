package org.gonevertical.share.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGwtShare implements EntryPoint {

  public void onModuleLoad() {
    
    Share s = new Share();
    
    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    vp.add(s);
    vp.setCellHorizontalAlignment(s, HorizontalPanel.ALIGN_CENTER);
    
    RootPanel.get().add(vp);
    
  }
  
}
