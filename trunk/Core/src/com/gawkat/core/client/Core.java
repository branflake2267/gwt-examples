package com.gawkat.core.client;

import com.gawkat.core.client.admin.thingtype.ThingTypes;
import com.gawkat.core.client.tests.TestRpcCallWidget;
import com.gawkat.core.client.ui.LoginUi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Core implements EntryPoint {

  private String appConsumerKey = "demo_application";
  private String appConsumerSecret = "c1d0e06998305903ac76f589bbd6d4b61a670ba6"; //salt:password
  
  private String defaultAppState = "home";
  
  public void onModuleLoad() {
    
    LoginWidget loginWidget = new LoginWidget(appConsumerKey, appConsumerSecret);
    
    VerticalPanel pWidget = new VerticalPanel();
    pWidget.add(loginWidget);
    
    RootPanel.get().add(pWidget);
  
  }

  /**
   * for setup
   */
  private void testThingTypes() {
    ThingTypes w = new ThingTypes();
    w.draw();
    RootPanel.get().add(w);
  }

}
