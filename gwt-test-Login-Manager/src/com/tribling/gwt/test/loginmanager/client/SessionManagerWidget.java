package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;

import com.google.gwt.user.client.ui.Widget;


public class SessionManagerWidget extends Composite implements ClickListener {

	//image bundle
	//private LoginManagerImageBundle ImageBundle = (LoginManagerImageBundle) GWT.create(LoginManagerImageBundle.class);

	//for rpc init Var 
	private LoginManagerServiceAsync callProvider;
	
	// change listeners for this widget
	private ChangeListenerCollection changeListeners;
	
	//unique user session confirmed with db
	private String SessionID;
	
	
	//Login Status Panel
	private HorizontalPanel pLoginStatusPanel = new HorizontalPanel();
	private HorizontalPanel pSignInStatus = new HorizontalPanel();
	private HorizontalPanel pAccount = new HorizontalPanel();
	private HorizontalPanel pLoadingStatus = new HorizontalPanel();
	
	//login panel
	private  LoginPanelWidget lpw = new LoginPanelWidget();
	
	//sign in button
	private PushButton bSignIn = new PushButton("Sign In");
	
	//sign out push button
	private PushButton bSignOut = new PushButton("Sign Out");
	
	//new account button
	private PushButton bNewAccount = new PushButton("New Account");
	
	private PushButton bMyAccount = new PushButton("My Account");

	//Login Status
	private boolean LoginStatus;
	

	
	/**
	 * constructor
	 * 
	 * 
	 * init widget??
	 */
	public SessionManagerWidget() {

		//setup the widget for control
		pLoginStatusPanel.add(pSignInStatus);
		pLoginStatusPanel.add(pAccount);
		pLoginStatusPanel.add(pLoadingStatus); //track rpc status??

		//init the widget
		initWidget(pLoginStatusPanel);
		
		

		
		//draw the default Sign in button to start with
		this.drawSignInButton();
				
		//draw new account button
		this.drawNewAccountButton();
		
		//check the current sessionid cookied?
		this.checkCurrentSession();
	}
	
	private void checkCurrentSession() {
		
		//get current session cookie if there is one?
		String SessionID = Cookies.getCookie("sid");
		
		//init the rpc
		this.LoginManagerProvider();
		
		//Go right to login if no cookie
		if (SessionID != null) {
			this.checkSessionIsStillLegal(SessionID);
		} else {
			drawLoginPanel(); //auto load login panel if no session
		}
	}
	
	
	private void drawNewAccountButton() {
		pAccount.clear();
		bNewAccount.addClickListener(this);
		pAccount.add(bNewAccount);
	}
	
	private void drawMyAccountButton() {
		pAccount.clear();
		bMyAccount.addClickListener(this);
		pAccount.add(bMyAccount);
	}
	
	
	private void drawSignInButton() {
		pSignInStatus.clear();
		bSignIn.addClickListener(this);
		pSignInStatus.add(bSignIn);	
	}
	
	
    private void drawSignOutButton() {
		pSignInStatus.clear();
    	bSignOut.addClickListener(this);
    	pSignInStatus.add(bSignOut);
    }
	
    
    /**
     * Process the Session either by Cookie OR BY Login Manager
     * @param SessionID
     */
    public void processSignIn(String SessionID, boolean bolFromCookie) {

    	//set the session id for using in other  methods
    	this.setSessionID(SessionID);
    	
    	//need to login
		if (SessionID == null) { 
			
			//set a boolean var for other methods
			this.setLoginStatus(false);
			
			//delete  session cookie
			this.removeSessionCookies();
			
			//don't redraw panel if this is activated by panel widget
			if (bolFromCookie == true) {
				//draw Login Manager widget so we can login
				this.drawLoginPanel();
			}
		
		//logged in
		} else { 
		
			//set the logged in status for other methods to use
			this.setLoginStatus(true);
			
			//Change Logged In Status
			this.drawSignOutButton();
			
			//change Account Button
			this.drawMyAccountButton();
		
			//don't need the panel anymore
			this.clearLoginPanel();
			
			//notify the orginator object that we have logged in (something changed here)
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
		}
    }

 
    public void processSignOut() {
    	
    	//not logged in anymore
    	this.SessionID = null;
    	this.LoginStatus = false;
    	
    	//remove cookies
    	this.removeSessionCookies();
    	
    	//change to sign in
    	this.drawSignInButton();
    	
    	//change account button
    	this.drawNewAccountButton();
    	
		//draw Login Manager widget so we can login
		this.drawLoginPanel();
    	
;
		
    	//is the cookie getting deleted?? 	
    }

    
    /**
     * draw login panel - to sign in
     */
    public void drawLoginPanel() {//display the LoginManager widget - for loggin in
		
    	//start with clean slate
		lpw.addChangeListener(new ChangeListener() {
				public void onChange(Widget sender) {
					boolean LoginStatus = lpw.getLoginStatus();
					String SessionID = lpw.getSessionID();
					
					if (SessionID != null) {
						processSignIn(SessionID, false);
					}
				}
			});
		//move this?
		RootPanel.get().add(lpw);
    }
    
    public void clearLoginPanel() {
    	lpw.removeFromParent();
    }
    
    public void setSessionID(String SessionID) {
    	this.SessionID = SessionID;
    }
    
    public String getSessionID() {
    	return this.SessionID;
    }
    
    
    public void removeSessionCookies() {
    	Cookies.setCookie("sid", "");
    	Cookies.removeCookie("sid");
    }
    
    
    public void setLoginStatus(boolean LoginStatus) {
    	this.LoginStatus = LoginStatus;
    }
    
    public boolean getLoginStatus() {
    	return this.LoginStatus;
    }
    
    
	private void drawLoading() {
		HorizontalPanel loading = new HorizontalPanel();
		loading.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		String sImage = GWT.getModuleBaseURL() + "loading2.gif";
	    Image image = new Image(sImage);
	    pLoadingStatus.setTitle("Checking your previous session is still good.");
		pLoadingStatus.add(image);
	}
	
	private void clearLoading() {
		pLoadingStatus.clear();
	}
	

	public void drawAccount() {
		
		//this doesn't work if it happens before rpc call back - need to set a var so rpc comes back it wont load
		this.clearLoginPanel();
		
		final AccountWidget Account = new AccountWidget(SessionID);
		/*
		Account.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				boolean LoginStatus = Account.getLoginStatus();
				String SessionID = Account.getSessionID();
				
				if (SessionID != null) {
					processSignIn(SessionID, false);
				}
			}
		});
		*/
		
		RootPanel.get().add(Account);
		
	}
	
    
    
    
    
	/**
	 * when clicked then process SignIn
	 */
	public void onClick(Widget sender) {

		if (sender == bSignOut) {
			this.processSignOut();
		} else if (sender == bSignIn) {
			this.drawLoginPanel();
		} else if (sender == bNewAccount) {
			this.drawAccount();
		} else if (sender == bMyAccount) {
			
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
        // Initialize the service.
    	callProvider = (LoginManagerServiceAsync) GWT.create(LoginManagerService.class);
        ServiceDefTarget target = (ServiceDefTarget) callProvider;

        // Use a module-relative URLs to ensure that this client code can find
        // its way home, even when the URL changes (as might happen when you
        // deploy this as a webapp under an external servlet container).
        String moduleRelativeURL = GWT.getModuleBaseURL() + "LoginManagerService";
        //String moduleRelativeURL = "/LoginManagerService";
        target.setServiceEntryPoint(moduleRelativeURL);
    }
    
	
	/**
	 * check if the current session id is still legal
	 */
    public void checkSessionIsStillLegal(String SessionID) {
    	
		//draw loading
		this.drawLoading();
    	
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
				processSignIn(sis.getSessionID(), true); //process the SessionID
				
				//clear loading icon
				clearLoading();
			}
		};
		// execute the service and request for rpc method
		callProvider.checkSessionIsStillLegal(SessionID, callback);
    }
    
    
    
    
    
}
