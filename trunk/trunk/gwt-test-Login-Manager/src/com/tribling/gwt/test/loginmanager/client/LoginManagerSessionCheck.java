package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginManagerSessionCheck {

	
	private LoginManagerServiceAsync callProvider;
	
	
	/**
	 * constructor
	 */
	public LoginManagerSessionCheck() {

		//get session cookie
		String SessionID = Cookies.getCookie("sid");

		//init the rpc
		this.LoginManagerProvider();
		
		//check it rpc call
		this.checkSessionIsStillLegal(SessionID);
	}
	
	
	/**
	 * init the rpc manager
	 *
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
	 * check if the current session id is still legal
	 */
    public void checkSessionIsStillLegal(String SessionID) {
    	
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			
			//ajax rpc fail
			public void onFailure(Throwable ex) {
				//change something in widgets panel noting failure of rpc
			}

			//ajax rpc success
			public void onSuccess(Object result) {
				SignInStatus sis = (SignInStatus) result; //cast the result into the object to use
				String theSessionID = sis.getSessionID(); //sid still exists in db table
				
				//need to login
				if (theSessionID == null) {
					
					//delete current session cookie
					Cookies.removeCookie("sid");
					
					//draw Login Manager widget so we can login
					drawLogin();
					
				} else { //logged in
					
					//logged in
					RootPanel.get().add(new Label("Logged In"));
					
				}
			}
		};

		// execute the service and request for rpc method
		callProvider.checkSessionIsStillLegal(SessionID, callback);
    }
    
    
    public void drawLogin() {
    
  	  //display the widget
  	  LoginPanelWidget lpw = new LoginPanelWidget();
  	  lpw.addChangeListener(new ChangeListener() {

  			public void onChange(Widget sender) {

  				//do anything u want here?
  				//Window.alert("Sign In Selected");
  				
  				//notify another widget, use widgets methods here to do stuff
  				//in widget's onclick method set a variable/object
  				//then use widgets method right here to get the variable/object
  				
  			}
  		});

  	  RootPanel.get().clear(); //clear the root - then display the login panel
  	  RootPanel.get().add(lpw);
    	
    }
    

}
