package com.gawkat.core.client;

import com.gawkat.core.client.account.LoginWidget;
import com.gawkat.core.client.account.ui.LoginUi;
import com.gawkat.core.client.admin.thingtype.ThingTypes;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.client.tests.TestRpcCallWidget;
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

  private ClientPersistence cp = new ClientPersistence();
  
  private LoginWidget wLogin = new LoginWidget(cp);
   
  /**
   * load on entry
   */
  public void onModuleLoad() {
    
    wLogin.initSession();
    wLogin.setUi(LoginUi.LOGIN_HORIZONTAL);
    
    VerticalPanel pWidget = new VerticalPanel();
    pWidget.add(wLogin);
    
    pWidget.setWidth("600px");
    pWidget.setCellHorizontalAlignment(wLogin, HorizontalPanel.ALIGN_RIGHT);
    
    RootPanel.get().add(pWidget);

    wLogin.addChangeHandler(this);
  }
  
  private void processLoginEvent() {
    
    int event = wLogin.getChangeEvent();
    if (event == EventManager.LOGGEDIN) {
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
