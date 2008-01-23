package com.tribling.gwt.test.loginmanager.client;



import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AccountWidget2 extends Composite implements ClickListener{


	
	
	
	//rpc init Var 
	private LoginManagerServiceAsync callProvider;
	
	
	private PushButton Save = new PushButton("Save");

	
	
	/**
	 * constructor
	 */
	public AccountWidget2() {
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(Save);
		initWidget(Save);
		
		Save.addClickListener(this);
		
		SaveAccount();
	}



	



	
	
	/**
	 * 
	 */
	public void onClick(Widget sender) {
		
		SaveAccount();
		
	}

	

	
	
	

	
	
	/**
	 * Init the RPC provider
	 */
    public void LoginManagerProvider() {
    	callProvider = (LoginManagerServiceAsync) GWT.create(LoginManagerService.class);
        ServiceDefTarget target = (ServiceDefTarget) callProvider;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "LoginManagerService";
        target.setServiceEntryPoint(moduleRelativeURL);
    }
    
	
	/**
	 * Process the sign in
	 * 
	 */
    public void SaveAccount() {
    	
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			
			//ajax rpc fail
			public void onFailure(Throwable caught) {
			}

			//ajax rpc success
			public void onSuccess(Object result) {
				//SignInStatus sis = (SignInStatus) result; //cast the result into the object to use
				//processCallBack(sis);
			}
		};

		// execute the service and request for rpc method
		callProvider.processSignIn("test", "test2", callback);
		
    }

    

    

	
}
