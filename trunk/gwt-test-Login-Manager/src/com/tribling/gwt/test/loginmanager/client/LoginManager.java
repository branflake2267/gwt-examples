package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LoginManager implements EntryPoint {

	
	private LoginManagerServiceAsync callProvider;
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {


		/*
		//Control the users session, UserID = Unique SessionID
		final SessionManagerWidget SessionManager = new SessionManagerWidget();
		SessionManager.addChangeListener(new ChangeListener() {

			public void onChange(Widget sender) {
				if (SessionManager.getLoginStatus() == true) {
					loadPanel(SessionManager.getSessionID());
				}

				if (SessionManager.getLoginStatus() == false) {
					//unloadWidgets();
				}
			}
		});
		RootPanel.get("LoginStatus").add(SessionManager);
		*/
		
		
		
		//AccountWidget2 Account = new AccountWidget2();
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
		
		//RootPanel.get().add(Account);
		
		
		processSignIn();
		
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
	 * Process the sign in
	 * 
	 */
    public void processSignIn() {
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			//ajax rpc fail
			public void onFailure(Throwable caught) {
				//change something in widgets panel noting failure of rpc
				//pDisplayError.clear();
				//pDisplayError.add(new Label("Ajax/RPC Connection Error"));
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
				//processCallBack(sis);
			}
		};

		// execute the service and request for rpc method
		callProvider.processSignIn(new String("test"), new String("test"), callback);
    }
    

	

	
	
	
	
	public void loadPanel(String SessionID) {
		
		RootPanel.get().add(new Label("Logged In: SessionID" + SessionID));
		
	}
	
	
}
