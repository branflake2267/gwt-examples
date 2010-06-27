package org.gonevertical.core.client.ui.login;

import java.util.Date;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class LoginWidget_Abs extends Composite implements ChangeHandler {
	
	protected ClientPersistence cp = null;

	// rpc system
	protected RpcCoreServiceAsync rpc;

	// login inputs
	protected LoginUi wloginUi = null;

	// errors
	protected String errApKey = "No consumer key was set " +
		"(for application/web site). debug: setAppConsumerKey()";

	// use this to verify signature
	protected String consumerSecret = null;
	
	public LoginWidget_Abs(ClientPersistence cp) {
		this.cp = cp;
		
		wloginUi = new LoginUi(cp);
		
		if (cp == null) {
			System.err.println("LoginWidget.LoginWidget() " +
			"Error, you didn't set the clientpersistence object into Login Widget. This need to be done.");
		}
		
		// init rpc
		rpc = RpcCore.initRpc();

		// observe global events
		if (cp != null) {
			cp.addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					ClientPersistence cp = (ClientPersistence) event.getSource();
					int changeEvent = cp.getChangeEvent();
					if (cp.getChangeEvent() == EventManager.NEW_USER_CREATED || // when new user is created auto login
							cp.getChangeEvent() == EventManager.LOGINBUTTONCLICKED) { // when the login button is clicked try logging in
						login();
						
					} else if (changeEvent == EventManager.LOGOUT) {
						logout();

					} else if (changeEvent == EventManager.FORGOT_PASSWORD) {
						forgotPassword();

					} else if (changeEvent == EventManager.PROFILE) {
						displayProfile();
						
					} else if (changeEvent == EventManager.USER_LOGGEDIN) {
						// nothing to do
					}
				}
			});
		}
		
		
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

		if (token == null) {
			return;
		}

		cp.setAccessToken(token);

		int result = token.getResult();
		switch (result) {
		case OAuthTokenData.SUCCESS:
			// this means the application is loaded, so lets draw the login inputs
			if (cp.getApplicationLoadedStatus() == false) { // only fire once during session!!!!!
				cp.fireChange(EventManager.APPLICATION_LOADED);
			}
			break;
		case OAuthTokenData.ERROR:
			// TODO - make better notification
			Window.alert("ERROR: This application's access token " +
				"did not match up.\n This application has not been granted access.");
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
		cp.fireChange(EventManager.USER_LOGGEDIN);

		setSessionCookie();
	}

	/**
	 * C. if B. passes, get users authorization
	 */
	protected void login() {

		// get url application is loaded on
		String url = getUrl();

		// get credentials from LoginUi
		String consumerKey = wloginUi.getConsumerKey();
		consumerSecret = wloginUi.getConsumerSecret();
		
		// just in case, if this happens agian - was setting to many handlers on cp
		if (consumerKey.equals(cp.getInputLabel_ConsumerKey()) == true) {
			// has to do when the login widget is redraw, the cp observation is reset multiple times 
			return;
		}

		if (consumerKey.trim().length() == 0 && consumerSecret.trim().length() == 0) {
			wloginUi.drawError("Please enter a username and password");
			return;
		}

		if (consumerKey.trim().length() == 0) {
			wloginUi.drawError("Please enter a username");
			return;
		}

		if (consumerSecret.trim().length() == 0) {
			wloginUi.drawError("Please enter a password");
			return;
		}

		// take appAccessToken, and ask for a user access token
		// setup a request token for user
		OAuthTokenData tokenData = cp.getAccessToken();
		if (tokenData == null) {
			System.err.println("LoginWidget.login(): Error getting access Token.");
			return;
		}
		tokenData.setConsumerKey(consumerKey);
		tokenData.sign(url, consumerSecret);
		
		String debug = tokenData.getDebugSigning();
		RootPanel.get().add(new HTML("LoginWidget: debugSigning " + debug));
		
		// rpc
		getUserAccessToken(tokenData);
	}

	private void logout() {
		cp.setAccessToken(null);
		consumerSecret = null;
		initSession();
		cp.fireChange(EventManager.USER_LOGGEDOUT);
		
		// reset the event
		cp.fireChange(0);
	}

	private void forgotPassword() {
		Window.alert("forgot password in session manager");
	}

	private void displayProfile() {
		Window.alert("display profile in session manager");
	}

	private void setSessionCookie() {
		String at = "";
		String as = "";
		if (wloginUi.getRememberMe() == true) {
			at = cp.getAccessToken().getAccessToken_key();
			as = cp.getAccessToken().getAccessToken_secret();
		} 

		Date expires = new Date();
		long nowLong = expires.getTime();
		nowLong = nowLong + (1000 * 60 * 60 * 24 * 7); //seven days
		expires.setTime(nowLong);

		String name = "accessToken";
		String value = at;
		Cookies.setCookie(name, value, expires);

		name = "AccessSecret";
		value = as;
		Cookies.setCookie(name, value, expires);
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

	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();
		
		
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
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
			public void onSuccess(OAuthTokenData token) {
				request_User_Access_Token_Response(token);
			}
		};
		rpc.getUserAccessToken(tokenData, callback);
	}


}
