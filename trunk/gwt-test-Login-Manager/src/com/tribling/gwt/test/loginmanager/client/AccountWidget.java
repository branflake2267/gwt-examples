package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AccountWidget extends Composite implements ClickListener{

	//rpc init Var 
	private LoginManagerServiceAsync callProvider;
	
	private VerticalPanel NewAccountPanel = new VerticalPanel();

	private Label TitleNew = new Label("Make A New Account");
	private Label TitleEdit = new Label("Edit Your Account");
	
	private HorizontalPanel pDisplayError = new HorizontalPanel();
	
	//Inputs
	private TextBox FirstName = new TextBox();
	private TextBox LastName = new TextBox();
	private PasswordTextBox Password1 = new PasswordTextBox();
	private PasswordTextBox Password2 = new PasswordTextBox();
	
	private PushButton Save = new PushButton("Save");
	
	//store data for rpc transport
	private Account account;
	
	/**
	 * constructor
	 */
	public AccountWidget() {
		
		HorizontalPanel hpFN = new HorizontalPanel();
		Label lFN = new Label("First Name:");
		lFN.setStyleName("account-Fields");
		hpFN.add(lFN);
		hpFN.add(FirstName);
	
		HorizontalPanel hpLN = new HorizontalPanel();
		Label lLN = new Label("Last Name:");
		lLN.setStyleName("account-Fields");
		hpLN.add(lLN);
		hpLN.add(LastName);
		
		HorizontalPanel hpP1 = new HorizontalPanel();
		Label lP1 = new Label("Password:");
		lP1.setStyleName("account-Fields");
		hpP1.add(lP1);
		hpP1.add(Password1);
		
		HorizontalPanel hpP2 = new HorizontalPanel();
		Label lP2 = new Label("Confirm:");
		lP2.setStyleName("account-Fields");
		hpP2.add(lP2);
		hpP2.add(Password2);
		
		HorizontalPanel hpSave = new HorizontalPanel();
		hpSave.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		hpSave.add(Save);
		Save.addClickListener(this);
		
		
		NewAccountPanel.add(TitleNew);
		NewAccountPanel.add(pDisplayError);
		NewAccountPanel.add(hpFN);
		NewAccountPanel.add(hpLN);
		NewAccountPanel.add(hpP1);
		NewAccountPanel.add(hpP2);
		NewAccountPanel.add(hpSave);
		
		//init the Account Widget
		initWidget(NewAccountPanel);
	}

	/**
	 * prep for transport of data
	 */
	private void saveData() {
		
		String DisplayError = null;
		boolean Flag = true;
		
		//prep for transport
		String sFirstName = FirstName.getText();
		String sLastName = LastName.getText();
		
		//check passwords match
		String sPas1 = Password1.getText();
		String sPas2 = Password2.getText();
		
		if (sFirstName == null) {
			Flag = false;
			DisplayError = "No First Name";
		}
		
		if (sLastName == null) {
			Flag = false;
			DisplayError = "No Last Name";
		}
		
		if (sPas1 == null) {
			Flag = false;
			DisplayError = "No Password";
		}
		
		if (sPas2 == null) {
			Flag = false;
			DisplayError = "No Password";
		}
		
		if (sPas2 != sPas2) {
			Flag = false;
			DisplayError = "Passwords do not match";
		}
		
		if (Flag == false) {
			pDisplayError.add(new Label(DisplayError));
			return;
		}
		
		//prep for transport
		account.FirstName = sFirstName;
		account.LastName = sLastName;
		account.Password = sPas1;
		
		
		//init rpc request - send the data
		SaveAccount();
	}
	

	
	/**
	 * process the rpc response
	 * @param account
	 */
	private void processCallBack(Account account) {
		
	}
	
	
	/**
	 * 
	 */
	public void onClick(Widget sender) {
		
		if (sender == Save) {
			this.saveData();
		}
		
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
    public void SaveAccount() {
		// service returns a result
		AsyncCallback callback_SaveAccount = new AsyncCallback() {
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
				Account account = (Account) result; //cast the result into the object to use
				processCallBack(account);
			}
		};

		// execute the service and request for rpc method
		callProvider.SaveAccount(account, callback_SaveAccount);
    }
    

	
}
