package com.gawkat.core.client;

import com.gawkat.core.client.admin.thingtype.ThingTypes;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.client.tests.TestRpcCallWidget;
import com.gawkat.core.client.ui.LoginUi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Core implements EntryPoint, ChangeHandler {

  private LoginWidget wLogin = null;
  
  // application credentials
  private String appConsumerKey = "demo_application";
  private String appConsumerSecret = "c1d0e06998305903ac76f589bbd6d4b61a670ba6"; //salt:password
  
  // link into the accessToken instead of pulling it out
  private OAuthTokenData accessToken = null;
  
  /**
   * load on entry
   */
  public void onModuleLoad() {
    
    // init the login widget
    wLogin = new LoginWidget(appConsumerKey, appConsumerSecret);
    
    VerticalPanel pWidget = new VerticalPanel();
    pWidget.add(wLogin);
    
    pWidget.setWidth("600px");
    pWidget.setCellHorizontalAlignment(wLogin, HorizontalPanel.ALIGN_RIGHT);
    
    RootPanel.get().add(pWidget);

    wLogin.addChangeHandler(this);
  }
  
  private void processLoginEvent() {
    
    int event = wLogin.getEvent();
    if (event == EventManager.LOGGEDIN) {
      accessToken = wLogin.getAccessToken(); 
      //Window.alert("test log in");
    } else if (event == EventManager.LOGGEDOUT) {
      // TODO what to do?
    }
    
  }

  /**
   * for setup
   */
  private void testThingTypes() {
    ThingTypes w = new ThingTypes();
    w.draw();
    RootPanel.get().add(w);
  }

  public void onChange(ChangeEvent event) {
    
    Widget sender = (Widget) event.getSource();
    if (sender == wLogin) {
      processLoginEvent();
    }
    
  }

}
