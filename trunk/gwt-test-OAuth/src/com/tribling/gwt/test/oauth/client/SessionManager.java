package com.tribling.gwt.test.oauth.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.tribling.gwt.test.oauth.client.oauth.SessionData;
import com.tribling.gwt.test.oauth.client.ui.LoginHorizontal;

/**
 * manages the login system 
 * 
 * @author branflake2267
 *
 */
public class SessionManager {

	// store the granted access token and related info in here
	private SessionData session = null;
	
	// div tag that holds the login ui widget
	private String loginUiDiv;
	
	/**
	 * constructor
	 */
	public SessionManager() {
	}
	
	/**
	 * !!!!THIS HAS TO BE DONE FIRST!!!!
	 * 
	 * @param loginUiDiv
	 */
	public void setLoginUiDiv(String loginUiDiv) {
		
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
			String err = "No div tag exists for this widget. debug: setLoginUiDiv() <div id='"+loginUiDiv+"'></div>";
			System.out.println(err);
			Window.alert(err);
		}
		
		this.loginUiDiv = loginUiDiv;
		drawLoginUi();
	}
	
	public SessionData getSession() {
		if (session == null) {
			// TODO - move to login?
			return null;
		}
		
		return session;
	}
	
	private void drawLoginUi() {
		LoginHorizontal loginUi = new LoginHorizontal();
		RootPanel.get(loginUiDiv).add(loginUi);
		loginUi.draw();
	}
	
	private void drawForgotPasswordUi() {
		// TODO center popup, with background grey
	}
	
	/**
	 * A. request request token
	 */
	private void request_Request_Token() {
		
	}
	
	/**
	 * B. server replies back with
	 */
	private void request_Request_Token_Response() {
		
	}
	
	/**
	 * C. if B. passes, get users authorization
	 */
	private void getUsersAuthorization() {
		
		drawLoginUi();
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
	
}
