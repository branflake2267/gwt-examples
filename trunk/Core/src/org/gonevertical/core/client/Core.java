package org.gonevertical.core.client;

import org.gonevertical.core.client.account.AccountWidget;
import org.gonevertical.core.client.account.thingtype.ThingTypes;
import org.gonevertical.core.client.account.ui.LoginUi;
import org.gonevertical.core.client.account.ui.LoginWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Core implements EntryPoint, ChangeHandler, ValueChangeHandler<String> {

  private ClientPersistence cp = new ClientPersistence();
  
  private LoginWidget wLogin = new LoginWidget(cp);
  
  private AccountWidget wAccount = new AccountWidget(cp);
  
  public static final String LINK_HOME = "account_Home";
  
  /**
   * load on entry
   */
  public void onModuleLoad() {
  	
  	History.addValueChangeHandler(this);
     	
    wLogin.initSession();
    wLogin.setUi(LoginUi.LOGIN_HORIZONTAL);
    
    Footer wFooter = new Footer();
    wFooter.draw();
    
    VerticalPanel pWidget = new VerticalPanel();
    pWidget.add(wLogin);
    pWidget.add(wAccount);
    pWidget.add(wFooter);
    
    pWidget.setWidth("800px");
    pWidget.setCellHorizontalAlignment(wLogin, HorizontalPanel.ALIGN_RIGHT);
    
    
    // align content center
    VerticalPanel pcenter = new VerticalPanel();
    pcenter.add(pWidget);
    pcenter.setWidth("100%");
    pcenter.setCellHorizontalAlignment(pWidget, HorizontalPanel.ALIGN_CENTER);
    
    
    
    RootPanel.get().add(pcenter);

    wLogin.addChangeHandler(this);
    
    wAccount.draw();
    
    initHistory();
    
    // Debug
    //pWidget.setStyleName("test1"); 
    //wLogin.addStyleName("test2");
    //wAccount.addStyleName("test3");
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

  public void onValueChange(ValueChangeEvent<String> event) {
  	
  	String historyToken = History.getToken();
  	Track.track(historyToken);
  	
  }

}
