package org.gonevertica.adsense.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGwtAdsense implements EntryPoint {


  public void onModuleLoad() {
    Layout layout = new Layout();
    
    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    vp.add(layout);
    vp.setCellHorizontalAlignment(layout, HorizontalPanel.ALIGN_CENTER);
    
    RootPanel.get().add(vp);
    
  }
  
}
