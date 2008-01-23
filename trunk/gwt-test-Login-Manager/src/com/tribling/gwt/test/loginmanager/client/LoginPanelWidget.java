package com.tribling.gwt.test.loginmanager.client;



import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class LoginPanelWidget extends Composite implements ClickListener {

	//rpc init Var 
	private LoginManagerServiceAsync callProvider;
	
	//widget main panel
	private final VerticalPanel WidgetPanel = new VerticalPanel();
	
	//Login Items to work with
	private Label SignInTxt = new Label("Account Sign In");
	private TextBox UserName = new TextBox();
	private PasswordTextBox Password = new PasswordTextBox();
	private CheckBox cbRememberMe = new CheckBox("Remember Me");
	private PushButton bSignIn = new PushButton("Sign In");
	private HorizontalPanel pLoadingStatus = new HorizontalPanel();

	private String SessionID = null;
	private boolean LoginStatus = false;
	
	//use this to display the errors
	private HorizontalPanel pDisplayError = new HorizontalPanel();
	private Label displayError = new Label();
	
	
	// change listeners for this widget
	private ChangeListenerCollection changeListeners;
	
	
	/**
	 * constructor widget setup
	 */
	public LoginPanelWidget() {
		
		pDisplayError.setStyleName("LoginPanelWidget-DisplayError");
		pDisplayError.add(displayError);
		//pDisplayError.add(new Label("display error here"));
		
		//add ClickListener
		//bSignIn.setStyleName("LoginPanelWidget-Button-SignIn");
		bSignIn.addClickListener(this);
		HorizontalPanel pSignIn = new HorizontalPanel();
		pSignIn.setStyleName("LoginPanelWidget-Button-Panel");
		pSignIn.add(bSignIn);
		pSignIn.add(pLoadingStatus);
		
		//username panel
		HorizontalPanel pUser = new HorizontalPanel();
		pUser.add(UserName);
		pUser.add(new Label("User Name"));
		
		//password panel
		HorizontalPanel pPassword = new HorizontalPanel();
		pPassword.add(Password);
		pPassword.add(new Label("Password"));
		

		
		//setup the widget's panel
		WidgetPanel.setStyleName("LoginPanelWidget");
		WidgetPanel.add(SignInTxt);
		WidgetPanel.add(pDisplayError);
		WidgetPanel.add(pUser);
		WidgetPanel.add(pPassword);
		WidgetPanel.add(cbRememberMe);
		WidgetPanel.add(pSignIn);
	
		initWidget(WidgetPanel);
		
		//init rpc service - get ready for button click
		LoginManagerProvider();
	}


	/**
	 * get username text
	 * @return
	 */
	public String getUserName() {
		return UserName.getText();
	}
	
	/**
	 * get password text
	 * @return
	 */
	public String getPassword() {
		return Password.getText();
	}
	
	/**
	 * clear widget panel when logged in
	 */
	public void clearWidgetPanel() {
		
		//do this in the session manager
		//WidgetPanel.clear();
	}

	
	public void processCallBack(SignInStatus sis) {
		
		this.clearLoading();
		
		//vars from rpc
		setSessionID(sis.getSessionID());
		String sDisplayError = sis.getDisplayError();
		
		
		//login failure notify
		if (sDisplayError != null) {
			pDisplayError.clear();
			pDisplayError.add(new Label(sDisplayError));
		}
		
		
		//Can log in, //save cookie
		if (getSessionID() != null) {
			this.saveLoginCookie();
			this.LoginStatus = true;
			
			//clear the panel for sign out and login popup not to duplicate itself
			clearWidgetPanel();
			
			//notify the session manager listener something has changed
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
		}

	}

	/**
	 * set SessionID
	 */
	private void setSessionID(String SessionID) {
		this.SessionID = SessionID;
	}
	
	/**
	 * get SessionID
	 * @return
	 */
	public String getSessionID() {
		return this.SessionID;
	}
	
	
	/**
	 * get cookied session id
	 */
	public String getCookieSessionID() {
		return Cookies.getCookie("sid");
	}
	

	
	/**
	 * save cookie if remember me is checked
	 */
	public void saveLoginCookie() {
		if (cbRememberMe.isChecked()) {
		    final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks in this example.
		    Date expires = new Date(System.currentTimeMillis() + DURATION);
		    Cookies.setCookie("sid", getSessionID(), expires, null, "/", false);
		}
	}



	/**
	 * set the login status for session manager notification
	 * @param LoginStatus
	 */
	public boolean getLoginStatus() {
		return this.LoginStatus;
	}
	
	public void drawLoading() {
		String sImage = GWT.getModuleBaseURL() + "loading2.gif";
	    Image image = new Image(sImage);
	    pLoadingStatus.setTitle("Checking your user account.");
		pLoadingStatus.add(image);
	}
	
	public void clearLoading() {
		pLoadingStatus.clear();
	}

	
	/**
	 * when clicked then process SignIn
	 */
	public void onClick(Widget sender) {

		//process the sign in
		if (sender == bSignIn) {
			this.drawLoading();
			this.processSignIn();
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
	
	
	
	/* ajax stuff below */
	
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
	 * Process the sign in
	 * 
	 */
    public void processSignIn() {
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			//ajax rpc fail
			public void onFailure(Throwable caught) {
				//change something in widgets panel noting failure of rpc
				pDisplayError.clear();
				pDisplayError.add(new Label("Ajax/RPC Connection Error"));
				RootPanel.get().add(new HTML("lpw caught: " + caught.toString()));
			     try {
				       throw caught;
				     } catch (IncompatibleRemoteServiceException e) {
				        // this client is not compatible with the server; cleanup and refresh the 
				        // browser
				    	RootPanel.get().add(new HTML("1RPC ERROR: " + e.toString()));
						System.out.println("1RPC ERROR: " + e.toString());
				     } catch (InvocationException e) {
				    	 // the call didn't complete cleanly
					     RootPanel.get().add(new HTML("2RPC ERROR: " + e.toString()));
						 System.out.println("2RPC ERROR: " + e.toString());
				     } catch (Throwable e) {
				    	 // last resort -- a very unexpected exception
				    	 RootPanel.get().add(new HTML("3RPC ERROR: " + e.toString()));
				    	 System.out.println("3RPC ERROR: " + e.toString());
				     }
			
			}

			//ajax rpc success
			public void onSuccess(Object result) {
				SignInStatus sis = (SignInStatus) result; //cast the result into the object to use
				processCallBack(sis);
			}
		};

		// execute the service and request for rpc method
		callProvider.processSignIn(getUserName(), getPassword(), callback);
    }
    

    

	
	
}//end class
