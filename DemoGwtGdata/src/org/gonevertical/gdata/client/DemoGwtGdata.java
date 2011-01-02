package org.gonevertical.gdata.client;

import org.gonevertical.gdata.client.layout.Home;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGwtGdata implements EntryPoint {

  public void onModuleLoad() {

    Home home = new Home();
    home.setWidth("600px");
    
    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    vp.add(home);
    vp.setCellHorizontalAlignment(home, HorizontalPanel.ALIGN_CENTER);
    
    RootPanel.get().add(vp);
   
    
  }
  
}
