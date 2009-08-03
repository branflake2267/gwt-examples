package com.gawkat.core.client;

import java.util.HashMap;

import com.gawkat.core.client.account.AccountManagementNavigation;
import com.gawkat.core.client.global.QueryString;
import com.gawkat.core.client.global.QueryStringData;
import com.gawkat.core.client.ui.LoginUi;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginWidget extends Composite implements HistoryListener, ChangeListener {

  private VerticalPanel pWidget = new VerticalPanel();
  
  // this manages the users privileges to protected resources
  private SessionManager sessionManager = null;
  
  // have ready the accounts management after init
  private AccountManagementNavigation accountManagement = null;
  
  // go to this when there is no historyToken. All navigation will use historyToken/anchors
  // TODO move this to home?
  private String defaultAppState = "home";

  // application credentials
  private String appConsumerKey = null;
  private String appConsumerSecret = null;
  
  /**
   * constructor 
   * 
   * @param appConsumerKey
   * @param appConsumerSecret
   */
  public LoginWidget(String appConsumerKey, String appConsumerSecret) {
    this.appConsumerKey = appConsumerKey;
    this.appConsumerSecret = appConsumerSecret;
    
    initWidget(pWidget);
    
    // observe the url for changes
    History.addHistoryListener(this);
    
    // session management for the application
    initSessionManager();
  }
  
  /**
   * manage the session and acess to protected resources 
   * for the web site and user using the web site
   */
  private void initSessionManager() {
   sessionManager = new SessionManager(pWidget);
   sessionManager.setUi(LoginUi.LOGIN_HORIZONTAL);
   sessionManager.setAppConsumerKey(appConsumerKey, appConsumerSecret); 
   
   // observe for login events ...
   sessionManager.addChangeListener(this);
  }
  
  private void drawAccountsManagement(QueryStringData qsd) {
    if (accountManagement == null) {
      accountManagement = new AccountManagementNavigation();
    }
    accountManagement.setQueryStringData(qsd);
  }


  /**
   * observe the url for changes
   */
  public void onHistoryChanged(String historyToken) {
    
    // this allows us to get the parameters in the historyToken domain.tld/page.html#anchor?params
    QueryString parse = new QueryString();
    QueryStringData qsd = parse.getQueryStringData();
    historyToken = qsd.getHistoryToken();
    HashMap<String, String> params = qsd.getParameters();
    
    // url navigation - with parameters
    if (historyToken.length() == 0) {
      History.newItem(defaultAppState);
      
    } else if (historyToken.matches("account_.*")) { // draw account mangement with anything that has account_ in the anchor
      drawAccountsManagement(qsd);
    
      
    } else if (historyToken.matches("other_.*")) {
      // TODO application management
    }
    
  }

 
  /**
   * observer
   */
  public void onChange(Widget sender) {
 
    if (sender == sessionManager) {
      int changeEvent = sessionManager.getChangeEvent();
      if (changeEvent == EventManager.LOGGEDIN) {
        
        // TODO - after login, do something
        
      } else if (changeEvent == EventManager.LOGGEDOUT) {
        sessionManager.setAppConsumerKey(appConsumerKey, appConsumerSecret); // reset the session entirely
        History.newItem("home"); // reset app 
      }
    }
    
  }
}
