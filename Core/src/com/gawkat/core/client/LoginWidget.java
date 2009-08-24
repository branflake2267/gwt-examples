package com.gawkat.core.client;

import java.util.HashMap;

import com.gawkat.core.client.account.AccountManagementNavigation;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.QueryString;
import com.gawkat.core.client.global.QueryStringData;
import com.gawkat.core.client.global.SessionManager;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.client.ui.LoginUi;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginWidget extends Composite implements HistoryListener, ChangeListener {

  private VerticalPanel pWidget = new VerticalPanel();
  
  // this manages the users privileges to protected resources
  private SessionManager sessionManager = null;
  
  // application credentials
  private String appConsumerKey = null;
  private String appConsumerSecret = null;
  
  private int changeEvent = 0;
  
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
   * get accessToken after login
   * 
   * @return
   */
  public OAuthTokenData getAccessToken() {
    if (sessionManager == null) {
      return null;
    }
    return sessionManager.getAccessToken();
  }
  
  /**
   * manage the session and access to protected resources 
   * for the web site and user using the web site
   */
  private void initSessionManager() {
   sessionManager = new SessionManager(pWidget);
   sessionManager.setUi(LoginUi.LOGIN_HORIZONTAL);
   sessionManager.setAppConsumerKey(appConsumerKey, appConsumerSecret); 
   
   // observe for login events ...
   sessionManager.addChangeListener(this);
  }
  
  private void logout() {
    this.changeEvent = EventManager.LOGGEDOUT;
    // return to application state as being logged in, and not the user
    sessionManager.setAppConsumerKey(appConsumerKey, appConsumerSecret);
    fireChange();
  }
  
  private void login() {
    this.changeEvent = EventManager.LOGGEDIN;
    fireChange();
  }
  
  public int getEvent() {
    return this.changeEvent;
  }
  
  private void fireChange() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }
  
  /**
   * TODO observe the url for changes
   */
  public void onHistoryChanged(String historyToken) {
    
    // this allows us to get the parameters in the historyToken domain.tld/page.html#anchor?params
    QueryString parse = new QueryString();
    QueryStringData qsd = parse.getQueryStringData();
    historyToken = qsd.getHistoryToken();
    HashMap<String, String> params = qsd.getParameters();
    
    // TODO - change app state?
    
  }
  
  /**
   * observer
   */
  public void onChange(Widget sender) {
    if (sender == sessionManager) {
      int changeEvent = sessionManager.getChangeEvent();
      if (changeEvent == EventManager.LOGGEDIN) {
        login();
      } else if (changeEvent == EventManager.LOGGEDOUT) {
        logout();
      }
    }
  }
  
}
