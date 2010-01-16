package com.gawkat.core.client;

import com.gawkat.core.client.account.AccountWidget;
import com.gawkat.core.client.account.thingtype.ThingTypes;
import com.gawkat.core.client.account.ui.LoginUi;
import com.gawkat.core.client.account.ui.LoginWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Core implements EntryPoint, ChangeHandler {

  private ClientPersistence cp = new ClientPersistence();
  
  private LoginWidget wLogin = new LoginWidget(cp);
  
  private AccountWidget wAccount = new AccountWidget(cp);
  
  /**
   * load on entry
   */
  public void onModuleLoad() {
    
    wLogin.initSession();
    wLogin.setUi(LoginUi.LOGIN_HORIZONTAL);
    
    VerticalPanel pWidget = new VerticalPanel();
    pWidget.add(wLogin);
    pWidget.add(wAccount);
    
    pWidget.setWidth("800px");
    pWidget.setCellHorizontalAlignment(wLogin, HorizontalPanel.ALIGN_RIGHT);
    
    RootPanel.get().add(pWidget);

    wLogin.addChangeHandler(this);
    
    wAccount.draw();
    
    pWidget.setStyleName("test2");
    
    initHistory();
  }
  
  private void initHistory() {
    
    String historyToken = History.getToken();
    
    if (historyToken.length() == 0) {
      History.newItem("account_Things");
    }
    
  }
  
 

  /**
   * for setup
   */
  private void testThingTypes() {
    ThingTypes w = new ThingTypes(cp);
    w.draw();
    RootPanel.get().add(w);
  }

  public void onChange(ChangeEvent event) {
    
  
    
  }

}
