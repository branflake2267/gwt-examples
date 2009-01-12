package com.gawkat.gwt.core.client;

import com.gawkat.gwt.oauth.client.SessionManager;
import com.gawkat.gwt.oauth.client.ui.LoginUi;
import com.google.gwt.core.client.EntryPoint;
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
public class Core implements EntryPoint {

  //web site consumer key
  private String appConsumerKey = "Gawkat.com_oAuthApp_02";
  
   // hash - hmac_sha1 - key="salt", data="password"
  private String appConsumerSecret = "c1d0e06998305903ac76f589bbd6d4b61a670ba6";
  
  // this manages the users priviledges to protected resources
  private SessionManager sessionManager;
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

   //Window.alert("test");
    
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
}
