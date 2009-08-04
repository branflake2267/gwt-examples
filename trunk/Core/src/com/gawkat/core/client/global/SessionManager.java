package com.gawkat.core.client.global;

import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.gawkat.core.client.ui.LoginUi;
import com.google.gwt.core.client.GWT;
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
public class SessionManager extends Composite implements ChangeListener {

  // rpc system
  public RpcCoreServiceAsync callRpcService;

  private VerticalPanel ploginHolder = null;
  
  // observe events
  private ChangeListenerCollection changeListeners = null;
  private int changeEvent = 0;
  

  // TODO - move this to LoginUi, as the master of the User Input systems one
  // could use, horizontal, vertical, separate forgot...
  // TODO - will do this later, as to the complication to code
  private LoginUi loginUi = null;

  // errors
  private String errApKey = "No consumer key was set (for application/web site). debug: setAppConsumerKey()";

  // Once the consumer gets Access, save the token for use
  // This will apply on user login
  // this will apply with user creation
  private OAuthTokenData accessToken = null;

  // use this to verify signature
  private String consumerSecret = null;
  
  /**
   * constructor
   */
  public SessionManager(VerticalPanel loginHolder) {
    this.ploginHolder = loginHolder;

    // init the login ui
    loginUi = new LoginUi();
    
    // init rpc
    callRpcService = RpcCore.initRpc();

    // observe
    loginUi.addChangeListener(this);
  }

  /**
   * set User Interface style - this must be done second
   * 
   * @param uiType
   */
  public void setUi(int uiType) {
    loginUi.setUi(uiType);
  }

  public void drawUi() {
    ploginHolder.add(loginUi);
    loginUi.draw(accessToken);
  }

  /**
   * use this for testing/debugging
   * 
   * TODO !!! remove after testing - remove later
   * 
   * @param email
   * @param password
   */
  public void autoLogin(String email, String password) {
    loginUi.autoLogin(email, password);
  }

  /**
   * set web site/application consumer key - determined by service provider A.
   * used to request request token -> grant access token?
   * 
   * @param consumerKey
   */
  public void setAppConsumerKey(String consumerKey, String consumerSecret) {

    // TODO - session cookie choice?

    // TODO - check for saved session cookie?

    // TODO - if session cookie, auto login?

    // get the application base url only, b/c of rpc method,
    // requests will happen on different ports, and with different servlet
    // context
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

    this.accessToken = token;

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
      loginUi.drawError("Signature did not match. Transit Error.");
    }
    
    // deal with the errors
    int result = token.getResult();
    if (result > OAuthTokenData.SUCCESS) { // all greater than success to be shown
      loginUi.drawError(token.getResultMessage());
      return;
    } 

    this.accessToken = token;

    // show logged in
    loginUi.setLoginStatus(true);

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
    String consumerKey = loginUi.getConsumerKey();
    consumerSecret = loginUi.getConsumerSecret();

    // take appAccessToken, and ask for a user access token
    // setup a request token for user
    OAuthTokenData tokenData = this.accessToken;
    tokenData.setConsumerKey(consumerKey);
    tokenData.sign(url, consumerSecret);

    // rpc
    getUserAccessToken(tokenData);
  }

  /**
   * 
   * TODO
   * 
   */
  private void logout() {

    loginUi.setLoginStatus(false);
    
    accessToken = null;
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
   * get client's url
   * 
   * TODO - add ./folder?
   * 
   * @return
   */
  private String getUrl() {

    String url = GWT.getModuleBaseURL();

    // TODO - work around get rid of port
    url = url.replaceFirst(":[0-9]+", "");
    // Window.alert("signing: url: " + url);

    return url;
  }

  public int getChangeEvent() {
    return changeEvent;
  }

  private void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    if (changeListeners != null) {
      changeListeners.fireChange(this);
    }
  }

  public void addChangeListener(ChangeListener listener) {
    if (changeListeners == null)
      changeListeners = new ChangeListenerCollection();
    changeListeners.add(listener);
  }

  public void removeChangeListener(ChangeListener listener) {
    if (changeListeners != null)
      changeListeners.remove(listener);
  }

  public void onChange(Widget sender) {

    int changeEvent = 0;
    if (sender == loginUi) {
      changeEvent = loginUi.getChangeEvent();
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

  }

  /**
   * A. Request request token (get consumer(web app) access)
   */
  private void requestToken(OAuthTokenData tokenData) {

    // TODO Show loading

    AsyncCallback<OAuthTokenData> callback = new AsyncCallback<OAuthTokenData>() {
      // on failure
      public void onFailure(Throwable ex) {
        // TODO - use an specialized re-try connection interface for this
        RootPanel.get().add(new HTML(ex.toString()));
      }

      // on success
      public void onSuccess(OAuthTokenData token) {
        request_Request_Token_Response(token);

        // TODO hide loading
      }
    };

    // execute rpc and wait for its response
    callRpcService.requestToken(tokenData, callback);
  }

  /**
   * login rpc
   * 
   * @param tokenData
   */
  private void getUserAccessToken(OAuthTokenData tokenData) {

    // TODO Show loading

    AsyncCallback<OAuthTokenData> callback = new AsyncCallback<OAuthTokenData>() {
      // on failure
      public void onFailure(Throwable ex) {
        // TODO - use an specialized re-try connection interface for this
        RootPanel.get().add(new HTML(ex.toString()));
      }

      // on success
      public void onSuccess(OAuthTokenData token) {

        request_User_Access_Token_Response(token);

        // TODO hide loading
      }
    };

    // execute rpc and wait for its response
    callRpcService.getUserAccessToken(tokenData, callback);
  }

}
