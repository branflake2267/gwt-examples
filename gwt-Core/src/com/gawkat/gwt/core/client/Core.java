package com.gawkat.gwt.core.client;

import java.util.HashMap;

import com.gawkat.gwt.oauth.client.SessionManager;
import com.gawkat.gwt.oauth.client.global.QueryString;
import com.gawkat.gwt.oauth.client.global.QueryStringData;
import com.gawkat.gwt.oauth.client.ui.LoginUi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Core implements EntryPoint, HistoryListener {

  // web site consumer key
  private String appConsumerKey = "Gawkat.com_oAuthApp_02";
  
   // hash - hmac_sha1 - key="salt", data="password"
  private String appConsumerSecret = "c1d0e06998305903ac76f589bbd6d4b61a670ba6";
  
  // this manages the users priviledges to protected resources
  private SessionManager sessionManager = null;
  
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
  }
  
  private void accountsManagement(QueryStringData qsd) {
    
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
      
    } else if (historyToken.contains("account_")) {
      accountsManagement(qsd);
    }
    
  }
  
  
}
