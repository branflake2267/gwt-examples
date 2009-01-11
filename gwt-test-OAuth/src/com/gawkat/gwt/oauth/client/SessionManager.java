package com.gawkat.gwt.oauth.client;

import com.gawkat.gwt.oauth.client.oauth.OAuthTokenData;
import com.gawkat.gwt.oauth.client.oauth.SessionData;
import com.gawkat.gwt.oauth.client.rpc.Rpc;
import com.gawkat.gwt.oauth.client.rpc.RpcServiceAsync;
import com.gawkat.gwt.oauth.client.ui.LoginUi;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;



/**
 * manages the users session, authentication/authorization to protected resources on a remote server
 * 
 * @author branflake2267
 *
 */
public class SessionManager extends Composite implements ChangeListener {

  // rpc system
	public RpcServiceAsync callRpcService;
	
	// observe events
	private ChangeListenerCollection changeListeners;
	private int changeEvent; 
	
	// store the granted access token and related info in here
	private SessionData session = null;
	
	// div tag that holds the login ui widget
	private String loginUiDiv;
	
	// TODO - move this to LoginUi, as the master of the User Input systems one could use, horizontal, vertical, separate forgot...
	// TODO - will do this later, as to the complication to code
	private LoginUi loginUi = new LoginUi();
	
	// errors
	private String errDiv = "No div tag exists for this widget. debug: setLoginUiDiv() <div id='"+loginUiDiv+"'></div>";
	private String errApKey = "No consumer key was set (for application/web site). debug: setAppConsumerKey()";
	
	// Once the consumer gets Access, save the token for use
	// This will apply on user login
	// this will apply with user creation
	private OAuthTokenData accessToken = null;
	
	/**
	 * constructor
	 */
	public SessionManager() {
	  
		// init rpc
		callRpcService = Rpc.initRpc();
		
		loginUi.addChangeListener(this);
	}
	
	/**
	 * !!!!THIS HAS TO BE DONE FIRST!!!!
	 * set the login div tag in html page
	 * 
	 * @param loginUiDiv
	 */
	public void setLoginUiDiv(String loginUiDiv, int uiType) {
		
		if (loginUiDiv == null) {
			System.out.println("loginUiDiv tag is null. debug: setLoginUiDiv()");
		}
		
		if (loginUiDiv.length() == 0) {
			System.out.println("Be sure to actually write in a tag id. debug: setLoginUiDiv()");
		}
		
		// This should notify when there is no div tag to stick the widget in. 
		// This is a common error that happens, when using another widget in your project or using it as a standalone
		// And is hard to debug when compiled and used outside the debugger shell.
		try {
			RootPanel.get(loginUiDiv).isAttached();
		} catch (Exception e) {
			System.out.println(errDiv);
			Window.alert(errDiv);
		}
		
		// what div is the user interface going to be stuck in
		this.loginUiDiv = loginUiDiv;
		
		// set the type of user interface with inputs
		loginUi.setUi(uiType);
		
	}
	
	public void drawUi() {
		try {
			RootPanel.get(loginUiDiv).isAttached();
		} catch (Exception e) {
			System.out.println(errDiv);
			Window.alert(errDiv);
		}
		RootPanel.get(loginUiDiv).add(loginUi);
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
	
	public SessionData getSession() {
		if (session == null) {
			// TODO - ask to login?
			return null;
		}
		
		return session;
	}
	
	/**
	 * set web site/application consumer key - determined by service provider
	 *  A. used to request request token -> grant access token?
	 * 
	 * @param consumerKey
	 */
	public void setAppConsumerKey(String consumerKey, String consumerSecret) {
		
		// TODO - check for saved session cookie
		
		// TODO - if session cookie, auto login
		
		// get the application base url only, b/c of rpc method, 
		// requests will happen on different ports, and with different servlet context
		String url = getUrl();
		OAuthTokenData token = new OAuthTokenData();
		token.setConsumerKey(consumerKey);
		token.sign(url, consumerSecret);
		token.setRequest(OAuthTokenData.REQUEST_REQUEST_TOKEN);
		
		// ask the server now
		request_Request_Token(token);
	}
	
	/**
	 * A. request request token
	 * ask for request token, grant access, or report findings (error,other)
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
	 * C. if B. passes, get users authorization
	 */
	private void getUsersAuthorization() {
	}
	
	/**
	 * C.2 if C doesn't pass error check the credentials
	 *   - ask agian
	 *   - show the errors in processing
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
	  //Window.alert("signing: url: " + url);
	  
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
      if (changeEvent == LoginUi.NEW_USER_CREATED) {
        // TODO - what to do
        Window.alert("Ready to move to new user settings");
        
        // TODO - set login ui to display logged in.
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

  

	
	
	
}
