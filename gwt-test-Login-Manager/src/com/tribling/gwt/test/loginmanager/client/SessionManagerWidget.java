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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author branflake2267
 *
 *
 * TODO init the widgets only if need be, and only init it once? -for now initing them on load
 */
public class SessionManagerWidget extends Composite implements ClickListener {

	//image bundle - future
	//private LoginManagerImageBundle ImageBundle = (LoginManagerImageBundle) GWT.create(LoginManagerImageBundle.class);

	//for rpc init Var 
	private LoginManagerServiceAsync callProvider;
	
	// change listeners for this widget
	private ChangeListenerCollection changeListeners;
	
	//unique user session confirmed with db
	private String SessionID = null;
	
	
	//Login controls and content panels
	private HorizontalPanel pLoginControls = new HorizontalPanel();
	private HorizontalPanel pUser = new HorizontalPanel();
	private HorizontalPanel pSignInStatus = new HorizontalPanel();
	private HorizontalPanel pAccountButtons = new HorizontalPanel();
	private HorizontalPanel pLoadingStatus = new HorizontalPanel();
	private HorizontalPanel pContent = new HorizontalPanel();
	
	//user name
	private String sUserName = null;
	
	//login panel
	private  LoginWidget pLoginWidget = new LoginWidget();
	
	//sign in button
	private PushButton bSignIn = new PushButton("Sign In");
	
	//sign out push button
	private PushButton bSignOut = new PushButton("Sign Out");
	
	//new account button
	private PushButton bNewAccount = new PushButton("New Account");
	private PushButton bMyAccount = new PushButton("My Account");

	//Login Status
	private boolean LoginStatus = false;
	
	
	//stop the auto popup of the loginpanelwidget
	private boolean bolAccountButtonPushed = false;
	
	//edit account
	private AccountWidget pAccountWidget = new AccountWidget();
	
	
	/**
	 * constructor
	 * 
	 * 
	 * init widget??
	 */
	public SessionManagerWidget() {

		//setup the widget
		pLoginControls.add(pUser);
		pLoginControls.add(pSignInStatus);
		pLoginControls.add(pAccountButtons);
		pLoginControls.add(pLoadingStatus); //track rpc status??

		//init widgets for use
		//pContent.add(pLoginWidget);
		//pContent.add(pAccountWidget);
		
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(pLoginControls);
		vp.add(pContent);
		
		
		//init the widget
		initWidget(vp);
		
		bMyAccount.addClickListener(this);
		bSignOut.addClickListener(this);
		bSignIn.addClickListener(this);
		bNewAccount.addClickListener(this);
		
		//set change listener on the loginwidget
		pLoginWidget.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				LoginStatus = pLoginWidget.getLoginStatus();
				String SessionID = pLoginWidget.getSessionID();
				boolean bolFinished = pLoginWidget.getFinished(); 
				
				if (bolFinished == true) {
					pContent.clear();
				} else {
					if (SessionID != null) {
						setSessionID(SessionID);
						processSignIn();
					}
				}
			}
		});
		
		//set a change listener for the account editing
		pAccountWidget.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {
				LoginStatus = pAccountWidget.getLoginStatus();
				String SessionID = pAccountWidget.getSessionID();
				boolean bolFinished = pAccountWidget.getFinished(); 
				
				if (bolFinished == true) {
					pContent.clear();
				} else {
				
					if (SessionID != null) {
						setSessionID(SessionID);
						processSignIn();
					}
				}
			}
		});
		
		//draw the default Sign in button to start with
		this.drawSignInButton();
				
		//draw new account button
		this.drawNewAccountButton();
		
		//check the current sessionid cookied?
		this.checkCurrentSession();
	}
	

    
    private void drawUser() {
    	pUser.add(new Label(this.sUserName));
    }
   
	
	
	private void drawNewAccountButton() {
		pAccountButtons.clear();
		pAccountButtons.add(bNewAccount);
	}
	
	private void drawMyAccountButton() {
		pAccountButtons.clear();
		pAccountButtons.add(bMyAccount);
	}
	
	
	private void drawSignInButton() {
		pSignInStatus.clear();
		pSignInStatus.add(bSignIn);	
	}
	
	
    private void drawSignOutButton() {
		pSignInStatus.clear();
    	pSignInStatus.add(bSignOut);
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
	
	
    public void clearLoginPanel() {
    	pContent.clear();
    }

	
    public void clearAccountWidget() {
    	pContent.clear();
    }
    
    
	
	private void checkCurrentSession() {
		
		//get current session cookie if there is one?
		String SessionID = Cookies.getCookie("sid");
		
		//init the rpc
		this.LoginManagerProvider();
		
		//Go right to login if no cookie
		if (SessionID != null) {
			this.checkSessionIsStillLegal(SessionID);
		} 
	}
	

    
    /**
     * Process the Session either by Cookie OR BY Login Manager
     * @param SessionID
     */
    public void processSignIn() {
    	
    	//need to login
		if (SessionID == null) { 
			
			//set a boolean var for other methods
			this.setLoginStatus(false);
			
			//delete  session cookie
			this.removeSessionCookies();

		//logged in
		} else { 
		
			//set the logged in status for other methods to use
			this.setLoginStatus(true);
			
			//Change Logged In Status
			this.drawSignOutButton();
			
			//change Account Button
			this.drawMyAccountButton();
		
			this.drawUser();
			
			//don't need the panel anymore
			//this.clearLoginPanel();
			
			//notify the orginator object that we have logged in (something changed here)
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
		}
    }

 
    public void processSignOut() {

    	//alert originator object, that were logging out
		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
    	
    	
    	//not logged in anymore
    	this.SessionID = null;
    	this.LoginStatus = false;
    	
    	//remove cookies
    	this.removeSessionCookies();
    	
    	//change to sign in
    	this.drawSignInButton();
    	
    	//change account button
    	this.drawNewAccountButton();
    	
    	
		//close my account if open
		pAccountWidget.processSignOut();
		pAccountWidget.removeFromParent();
		
    		
		//is the cookie getting deleted?? 
		//Window.alert("SID:"+ SessionID);
		//Window.alert(Cookies.getCookie("sid"));
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
    
    
	
    

	
    
	/**
	 * when clicked then process SignIn
	 */
	public void onClick(Widget sender) {

		//sign out button
		if (sender == bSignOut) {
			
			pContent.clear();
			this.processSignOut();
			
		
		//sign in button
		} else if (sender == bSignIn) {
			
			pContent.clear();
			pContent.add(pLoginWidget);
			
			
			
		//new account button
		} else if (sender == bNewAccount) {
			
			pContent.clear();
			pContent.add(pAccountWidget);
			
		
			
		} else if (sender == bMyAccount) {
			
			if (SessionID != null) { //string comparison ??
				pAccountWidget.loadAccountData(this.SessionID);
			}
			
			pContent.clear();
			pContent.add(pAccountWidget);
			
		
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
    	callProvider = (LoginManagerServiceAsync) GWT.create(LoginManagerService.class);
        ServiceDefTarget target = (ServiceDefTarget) callProvider;

        // Use a module-relative URLs to ensure that this client code can find
        // its way home, even when the URL changes (as might happen when you
        // deploy this as a webapp under an external servlet container).
        String moduleRelativeURL = GWT.getModuleBaseURL() + "LoginManagerService";
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
				//RootPanel.get().add(new HTML(caught.toString()));
				System.out.println("RPC ERROR CheckSessionStillLegal: " + caught.toString());
			     try {
				       throw caught;
				     } catch (IncompatibleRemoteServiceException e) {
				       // this client is not compatible with the server; cleanup and refresh the 
				       // browser
				    	//RootPanel.get().add(new HTML(e.toString()));
						System.out.println("1RPC ERROR: " + e.toString());
				     } catch (InvocationException e) {
				       // the call didn't complete cleanly
					    	//RootPanel.get().add(new HTML(e.toString()));
							System.out.println("2RPC ERROR: " + e.toString());
				     } catch (Throwable e) {
				       // last resort -- a very unexpected exception
					    	//RootPanel.get().add(new HTML(e.toString()));
							System.out.println("3RPC ERROR: " + e.toString());
				     }
			}
			//ajax rpc success
			public void onSuccess(Object result) {
				SignInStatus sis = (SignInStatus) result; //cast the result into the object to use
				
				setSessionID(sis.getSessionID());
				setUserName(sis.getUserName());
				processSignIn(); 
				
				//clear loading icon
				clearLoading();
			}
		};
		// execute the service and request for rpc method
		callProvider.checkSessionIsStillLegal(SessionID, callback);
    }
    
    
    public void setUserName(String sUserName) {
    	this.sUserName = sUserName;
    }
    
    
    
}
