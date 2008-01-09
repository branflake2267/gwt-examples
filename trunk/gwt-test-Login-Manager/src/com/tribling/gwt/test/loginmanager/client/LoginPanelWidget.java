package com.tribling.gwt.test.loginmanager.client;



import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class LoginPanelWidget extends Composite implements ClickListener {

	private LoginManagerServiceAsync callProvider;
	
	//Login Items to work with
	private Label SignInTxt = new Label("Sign into your account here.");
	private TextBox UserName = new TextBox();
	private PasswordTextBox Password = new PasswordTextBox();
	private CheckBox cbRememberMe = new CheckBox("Remember Me");
	private Button bSignIn = new Button("Sign In");

	
	//use this to display the errors
	private HorizontalPanel pDisplayError = new HorizontalPanel();
	private Label displayError = new Label();
	
	
	// change listeners for this widget
	private ChangeListenerCollection changeListeners;
	
	
	/**
	 * constructor widget setup
	 */
	public LoginPanelWidget() {
		
		//pDisplayError.setStyleName("LoginManager-DisplayError");
		pDisplayError.setStyleName("LoginManager-DisplayError");
		pDisplayError.add(displayError);
		//pDisplayError.add(new Label("display error here"));
		
		//add ClickListener
		bSignIn.addClickListener(this);
	
		//setup the widget's panel
		VerticalPanel WidgetPanel = new VerticalPanel();
		WidgetPanel.setStyleName("Login-Panel");
		
		WidgetPanel.add(SignInTxt);
		WidgetPanel.add(pDisplayError);
		WidgetPanel.add(UserName);
		WidgetPanel.add(Password);
		WidgetPanel.add(cbRememberMe);
		WidgetPanel.add(bSignIn);
	
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
	


	
	public void processCallBack(SignInStatus sis) {
				
		String SessionID = sis.getSessionID();
		String sDisplayError = sis.getDisplayError();
		
		//login failure notify
		if (sDisplayError != null) {
			pDisplayError.clear();
			pDisplayError.add(new Label(sDisplayError));
		}
		
		//Can log in, do what??
		if (SessionID != null) {
			
			//save cookie
			saveLoginCookie(SessionID);
			
			//goto where
		}
		
	}

	/**
	 * save cookie if remember me is checked
	 */
	public void saveLoginCookie(String SessionID) {
		if (cbRememberMe.isChecked()) {
		    final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks in this example.
		    Date expires = new Date(System.currentTimeMillis() + DURATION);
		    Cookies.setCookie("sid", SessionID, expires, null, "/", false);
		}
	}

	/**
	 * 
	 */
	public void lookupLoginCookie() {
		
		String SessionID = Cookies.getCookie("sid");
		
	    //if ( sessionID != null ) checkWithServerIfSessionIdIsStillLegal();
	    //else displayLoginBox();
		
	}

	
	/**
	 * when clicked then process SignIn
	 */
	public void onClick(Widget sender) {

		//process the sign in
		this.processSignIn();
		
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
	 * init the rpc manager
	 */
    public void LoginManagerProvider() {
        // Initialize the service.
    	callProvider = (LoginManagerServiceAsync) GWT.create(LoginManagerService.class);
        ServiceDefTarget target = (ServiceDefTarget) callProvider;

        // Use a module-relative URLs to ensure that this client code can find
        // its way home, even when the URL changes (as might happen when you
        // deploy this as a webapp under an external servlet container).
        String moduleRelativeURL = GWT.getModuleBaseURL() + "LoginManager";
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
			public void onFailure(Throwable ex) {
				//change something in widgets panel noting failure of rpc
				pDisplayError.clear();
				pDisplayError.add(new Label("Ajax/RPC Connection Error"));
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
