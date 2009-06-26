package com.gawkat.gwt.navigation.client;

import java.util.HashMap;

import com.gawkat.gwt.system.client.EventManager;
import com.gawkat.gwt.system.client.SessionManager;
import com.gawkat.gwt.system.client.account.AccountManagementNavigation;
import com.gawkat.gwt.system.client.global.QueryString;
import com.gawkat.gwt.system.client.global.QueryStringData;
import com.gawkat.gwt.system.client.ui.LoginUi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Navigation implements EntryPoint, HistoryListener, ChangeListener {

 
  // web site consumer key
  // hash - hmac_sha1 - key="salt", data="password"
  private String appConsumerKey = "demo_application";
  private String appConsumerSecret = "c1d0e06998305903ac76f589bbd6d4b61a670ba6"; //salt:password
  
  // this manages the users privileges to protected resources
  private SessionManager sessionManager = null;
  
  // have ready the accounts management after init
  private AccountManagementNavigation accountManagement = null;
  
  // go to this when there is no historyToken. All navigation will use historyToken/anchors
  private String defaultHomePage = "home";
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

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
   sessionManager = new SessionManager();
   sessionManager.setAppConsumerKey(appConsumerKey, appConsumerSecret); 
   sessionManager.setLoginUiDiv("LoginUI", LoginUi.LOGIN_HORIZONTAL);
   
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
      History.newItem(defaultHomePage);
      
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
