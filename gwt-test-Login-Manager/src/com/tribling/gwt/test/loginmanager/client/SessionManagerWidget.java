package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class SessionManagerWidget extends Composite implements ClickListener {

	//rpc init Var 
	private LoginManagerServiceAsync callProvider;
	
	// change listeners for this widget
	private ChangeListenerCollection changeListeners;
	
	private String SessionID;
	
	//sign out push button
	private PushButton bSignOut = new PushButton("SignOut");
	
	//sign in button
	
	//Login Status
	private boolean LoginStatus;
	
	
	/**
	 * constructor
	 * 
	 * 
	 * init widget??
	 */
	public SessionManagerWidget() {

		//init widiget?????
		
		//get session cookie
		String SessionID = Cookies.getCookie("sid");

		//init the rpc
		this.LoginManagerProvider();
		
		//check it rpc call
		if (SessionID != null) {
			this.checkSessionIsStillLegal(SessionID);
		} else {
			drawLoginPanel();
		}
		
		
	}
	
	

	
    
    /**
     * Process the Session either by Cookie OR BY Login Manager
     * @param SessionID
     */
    public void processSession(String SessionID, boolean bolFromCookie) {
    	
    	//set the session id for using in methods
    	this.setSessionID(SessionID);
    	
		if (SessionID == null) { //need to login
			
			//set a boolean var for other methods
			this.setLoginStatus(false);
			
			//delete  session cookie
			this.removeSessionCookies();
			
			
			//don't redraw panel if this is activated by panel widget
			if (bolFromCookie == true) {
				//draw Login Manager widget so we can login
				this.drawLoginPanel();
			}


			
		} else { //logged in
		
			//set the logged in status for other methods to use
			this.setLoginStatus(true);
			
			//Change Logged In Status
			this.changeLoginStatusDisplay();
		
			//notify the orginator objecy that something has changed so it can change
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
			
		}
    }
    
    
    /**
     * draw login panel - to sign in
     */
    public void drawLoginPanel() {//display the LoginManager widget - for loggin in
		  
		final LoginPanelWidget lpw = new LoginPanelWidget();
		lpw.addChangeListener(new ChangeListener() {
				public void onChange(Widget sender) {
					boolean LoginStatus = lpw.getLoginStatus();
					String SessionID = lpw.getSessionID();
					
					if (SessionID != null) {
						processSession(SessionID, false);
					}
				}
			});
		RootPanel.get("LoginPanel").add(lpw);
    }
    
    
    
    /**
     * clear login panel - signed in
     */
    public void clearLoginPanelDisplay() {
    	RootPanel.get("LoginPanel").clear();
    }
    
        
    
    private void changeLoginStatusDisplay() {
    	
    	if (this.LoginStatus == true) {
    	
			//make ready for new stuff
			this.clearLoginPanelDisplay();
    		
	    	//Sign Out Button
	    	bSignOut.addClickListener(this);
	    	
	    	//Sign Out Button Panel
	    	HorizontalPanel pSignOut = new HorizontalPanel();
	    	pSignOut.setStyleName("Session-Button-Logout");
	    	pSignOut.add(bSignOut);
	    	
	    	//Logged In Status
	    	HorizontalPanel ls = new HorizontalPanel();
	    	ls.add(new Label("Logged In"));
	    	ls.add(pSignOut);
	    	ls.add(new Label("Debug Session: " + this.SessionID));
	    	
	    	RootPanel.get("LoginStatus").add(ls);
    	}
    }

    public void clearLoginStatusDisplay() {
    	RootPanel.get("LoginStatus").clear();
    }
    
 
    public void processSignOut() {
    	
    	//not logged in anymore
    	this.SessionID = null;
    	this.LoginStatus = false;
    	
    	
    	//remove cookies
    	this.removeSessionCookies();
    	
		//draw Login Manager widget so we can login
		this.drawLoginPanel();
		
		//clear login status
		this.clearLoginStatusDisplay();
		
    }

    
    
    public void setSessionID(String SessionID) {
    	this.SessionID = SessionID;
    }
    
    public String getSessionID() {
    	return this.SessionID;
    }
    
    
    public void removeSessionCookies() {
    	Cookies.removeCookie("sid");
    }
    
    
    public void setLoginStatus(boolean LoginStatus) {
    	this.LoginStatus = LoginStatus;
    }
    
    public boolean getLoginStatus() {
    	return this.LoginStatus;
    }
    
    
    
    
    
	/**
	 * when clicked then process SignIn
	 */
	public void onClick(Widget sender) {

		if (sender == bSignOut) {
			this.processSignOut();
		}
		
		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}
    
	/**
	 * use this to listen/observe to the widget
	 * 
	 * @param listener
	 */
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null)
			changeListeners = new ChangeListenerCollection();
		changeListeners.add(listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null)
			changeListeners.remove(listener);
	}

	
	
	
	
	
	
	/**
	 * Init the RPC provider
	 */
    public void LoginManagerProvider() {
    	
    	//debug
    	//Window.alert("debug rpc path: " + GWT.getModuleBaseURL() + "LoginManagerService");
    	
        // Initialize the service.
    	callProvider = (LoginManagerServiceAsync) GWT.create(LoginManagerService.class);
        ServiceDefTarget target = (ServiceDefTarget) callProvider;

        // Use a module-relative URLs to ensure that this client code can find
        // its way home, even when the URL changes (as might happen when you
        // deploy this as a webapp under an external servlet container).
        String moduleRelativeURL = GWT.getModuleBaseURL() + "LoginManagerService";
        //String moduleRelativeURL = "/LoginManagerService";
        target.setServiceEntryPoint(moduleRelativeURL);
        
        
    	//debug
        RootPanel.get().add(new Label("moduleRelativeURL: " + moduleRelativeURL));
    	
    }
    
	
	/**
	 * check if the current session id is still legal
	 */
    public void checkSessionIsStillLegal(String SessionID) {
    	
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			
			//ajax rpc fail
			public void onFailure(Throwable caught) {
				//change something in widgets panel noting failure of rpc
				RootPanel.get().add(new HTML(caught.toString()));
				System.out.println("RPC ERROR CheckSessionStillLegal: " + caught.toString());
			     try {
				       throw caught;
				     } catch (IncompatibleRemoteServiceException e) {
				       // this client is not compatible with the server; cleanup and refresh the 
				       // browser
				    	RootPanel.get().add(new HTML(e.toString()));
						System.out.println("1RPC ERROR: " + e.toString());
				     } catch (InvocationException e) {
				       // the call didn't complete cleanly
					    	RootPanel.get().add(new HTML(e.toString()));
							System.out.println("2RPC ERROR: " + e.toString());
				     } catch (Throwable e) {
				       // last resort -- a very unexpected exception
					    	RootPanel.get().add(new HTML(e.toString()));
							System.out.println("3RPC ERROR: " + e.toString());
				     }
			}

			//ajax rpc success
			public void onSuccess(Object result) {
				SignInStatus sis = (SignInStatus) result; //cast the result into the object to use
				processSession(sis.getSessionID(), true); //process the SessionID
			}
		};
		// execute the service and request for rpc method
		callProvider.checkSessionIsStillLegal(SessionID, callback);
    	
    }
    
    
    
    
    
}
