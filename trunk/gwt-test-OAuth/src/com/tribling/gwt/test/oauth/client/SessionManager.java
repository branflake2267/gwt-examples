package com.tribling.gwt.test.oauth.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.tribling.gwt.test.oauth.client.oauth.SessionData;
import com.tribling.gwt.test.oauth.client.ui.LoginUi;



/**
 * manages the users session, authentication/authorization to protected resources on a remote server
 * 
 * @author branflake2267
 *
 */
public class SessionManager extends Composite {

	private ChangeListenerCollection changeListeners;
	private int changeEvent; 
	
	// store the granted access token and related info in here
	private SessionData session = null;
	
	// div tag that holds the login ui widget
	private String loginUiDiv;
	
	// TODO - move this to LoginUi, as the master of the User Input systems one could use, horizontal, vertical, separate forgot...
	// TODO - will do this later, as to the complication to code
	private LoginUi loginUi = new LoginUi();
		
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
		
		
		// TODO - check for saved session cookie
		
		// TODO - if session cookie, auto login
	}
	
	/**
	 * use this for testing/debugging
	 * 
	 * !!! remove after testing - remove later
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
	
	private void drawLoginUi() {
		RootPanel.get(loginUiDiv).add(loginUi);
		loginUi.draw();
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
	
	private void setObserver() {
		
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
}
