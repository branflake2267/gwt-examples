package com.gawkat.core.client.account;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.account.ui.LoginUi;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * resources on a remote server
 * 
 * @author branflake2267
 * 
 */
public class LoginWidget extends Composite implements ChangeHandler {

  private ClientPersistence cp = null;
  
  // rpc system
  public RpcCoreServiceAsync rpc;

  private VerticalPanel pWidget = new VerticalPanel();
  
  private int changeEvent = 0;
  
  // login inputs
  private LoginUi wloginUi = null;

  // errors
  private String errApKey = "No consumer key was set (for application/web site). debug: setAppConsumerKey()";

  // use this to verify signature
  private String consumerSecret = null;

  /**
   * login widget
   * 
   * @param cp
   */
  public LoginWidget(ClientPersistence cp) {
    this.cp  = cp;
  
    wloginUi = new LoginUi(cp);
    
    pWidget.add(wloginUi);
    
    initWidget(pWidget);

    
    // init rpc
    rpc = RpcCore.initRpc();

    // observe
    wloginUi.addChangeHandler(this);
   
  }

  /**
   * start the session, by having the application get token
   */
  public void initSession() {
    setAppConsumerKey();
  }
  
  /**
   * set User Interface style - this must be done second
   * 
   * @param uiType
   */
  public void setUi(int uiType) {
    wloginUi.setUi(uiType);
  }

  private void drawUi() {
    pWidget.add(wloginUi);
    wloginUi.draw();
  }

  /**
   * A. the start of application getting access:
   * set web site/application consumer key - determined by service provider A.
   * used to request request token -> grant access token?
   * 
   * @param consumerKey
   */
  public void setAppConsumerKey() {

    String consumerKey = cp.getAppConsumerKey();
    String consumerSecret = cp.getAppConsumerSecret();
    
    String url = getUrl();
    OAuthTokenData token = new OAuthTokenData();
    token.setConsumerKey(consumerKey);
    token.sign(url, consumerSecret);
    token.setRequest(OAuthTokenData.REQUEST_REQUEST_TOKEN);

    // ask the server now
    request_Request_Token(token);
  }
  
  /**
   * A. request request token ask for request token, grant access, or report
   * findings (error,other)
   * 
   * @param token
   */
  private void request_Request_Token(OAuthTokenData token) {
    requestToken(token);
  }

  /**
   * B. server replies back with
   */
  private void request_Request_Token_Response(OAuthTokenData token) {

    cp.setAccessToken(token);

    int result = token.getResult();
    switch (result) {
    case OAuthTokenData.SUCCESS:
      drawUi();
      break;
    case OAuthTokenData.ERROR:
      // TODO - make better notification
      Window.alert("ERROR: This application's access token did not match up.\n This application has not been granted access.");
      break;

    }
  }

  /**
   * TODO needs testing and finishing
   * 
   * this is after rpc and after login button
   * 
   * @param token
   */
  private void request_User_Access_Token_Response(OAuthTokenData token) {

    String url = getUrl();
    
    // verify signature
    boolean verify = token.verify(url, consumerSecret);
    if (verify == false) {
      wloginUi.drawError("Signature did not match. Transit Error.");
    }
    
    // deal with the errors
    int result = token.getResult();
    if (result > OAuthTokenData.SUCCESS) { // all greater than success to be shown
      wloginUi.drawError(token.getResultMessage());
      return;
    } 

    cp.setAccessToken(token);

    // show logged in
    wloginUi.setLoginStatus(true);

    // Notify change logged in
    fireChange(EventManager.LOGGEDIN);
  }

  /**
   * C. if B. passes, get users authorization
   */
  private void login() {

    // get url application is loaded on
    String url = getUrl();

    // get credentials from LoginUi
    String consumerKey = wloginUi.getConsumerKey();
    consumerSecret = wloginUi.getConsumerSecret();

    // take appAccessToken, and ask for a user access token
    // setup a request token for user
    OAuthTokenData tokenData = cp.getAccessToken();
    tokenData.setConsumerKey(consumerKey);
    tokenData.sign(url, consumerSecret);

    // rpc
    getUserAccessToken(tokenData);
  }

  private void logout() {
    wloginUi.setLoginStatus(false);
    cp.setAccessToken(null);
    consumerSecret = null;
    fireChange(EventManager.LOGGEDOUT);
  }

  private void forgotPassword() {
    Window.alert("forgot password in session manager");
  }
  
  private void displayProfile() {
    Window.alert("display profile in session manager");
  }
  
  /**
   * C.2 if C doesn't pass error check the credentials - ask agian - show the
   * errors in processing
   */
  private void getUsersAuthorization_Reponse() {

  }

  private void setSessionCookie() {
    // TODO - set the session as a cookie to remember to login agian
  }

  /**
   * get client's url - doesn't use port
   * @return
   */
  private String getUrl() {
    String url = GWT.getModuleBaseURL();
    url = url.replaceFirst(":[0-9]+", "");
    return url;
  }

  public int getChangeEvent() {
    return changeEvent;
  }
  
  private void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    int changeEvent = 0;
    if (sender == wloginUi) {
      changeEvent = wloginUi.getChangeEvent();
      if (changeEvent == EventManager.NEW_USER_CREATED) {
        // nothing to do here for now
      } else if (changeEvent == EventManager.LOGIN) {
        login();
      } else if (changeEvent == EventManager.LOGOUT) {
        logout();
      } else if (changeEvent == EventManager.FORGOT_PASSWORD) {
        forgotPassword();
      } else if (changeEvent == EventManager.PROFILE) {
        displayProfile();
      }
    }
    // notify client persistence
    cp.fireChange(changeEvent);
  }

  /**
   * A. Request request token (get consumer(web app) access)
   * get application token
   */
  private void requestToken(OAuthTokenData tokenData) {

    AsyncCallback<OAuthTokenData> callback = new AsyncCallback<OAuthTokenData>() {
      public void onFailure(Throwable ex) {
        RootPanel.get().add(new HTML(ex.toString()));
      }
      public void onSuccess(OAuthTokenData token) {
        request_Request_Token_Response(token);
      }
    };
    rpc.requestToken(tokenData, callback);
  }

  /**
   * get user access
   * 
   * @param tokenData
   */
  private void getUserAccessToken(OAuthTokenData tokenData) {

    AsyncCallback<OAuthTokenData> callback = new AsyncCallback<OAuthTokenData>() {
      public void onFailure(Throwable ex) {
        RootPanel.get().add(new HTML(ex.toString()));
      }
      public void onSuccess(OAuthTokenData token) {
        request_User_Access_Token_Response(token);
      }
    };
    rpc.getUserAccessToken(tokenData, callback);
  }

 

}
