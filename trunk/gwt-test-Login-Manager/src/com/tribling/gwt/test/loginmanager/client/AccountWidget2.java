package com.tribling.gwt.test.loginmanager.client;



import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AccountWidget2 extends Composite implements ClickListener{

	
	//GET TIMESTAMP
	
	
	
	//rpc init Var 
	private LoginManagerServiceAsync callProvider;
	
	private VerticalPanel NewAccountPanel = new VerticalPanel();

	private Label TitleNew = new Label("Make A New Account");
	private Label TitleEdit = new Label("Edit Your Account");
	
	private HorizontalPanel pDisplayError = new HorizontalPanel();
	
	private HorizontalPanel pLoadingStatus = new HorizontalPanel();
	
	//Inputs
	private TextBox FirstName = new TextBox();
	private TextBox LastName = new TextBox();
	private TextBox UserName = new TextBox();
	private PasswordTextBox Password1 = new PasswordTextBox();
	private PasswordTextBox Password2 = new PasswordTextBox();
	
	private PushButton Save = new PushButton("Save");
		
	private String SessionID;
	

	
	
	/**
	 * constructor
	 */
	public AccountWidget2() {
		
		this.SessionID = SessionID;
		
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
		
		HorizontalPanel hpUN = new HorizontalPanel();
		Label lUserName = new Label("User Name");
		lUserName.setStyleName("account-Fields");
		hpUN.add(lUserName);
		hpUN.add(UserName);
		
		
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
		hpSave.add(pLoadingStatus);
		
		
		//New User or Editing Account via SessionID
		if (SessionID != null) {
			NewAccountPanel.add(TitleNew);
		} else {
			NewAccountPanel.add(TitleEdit);
		}
		
		
		NewAccountPanel.add(pDisplayError);
		NewAccountPanel.add(hpFN);
		NewAccountPanel.add(hpLN);
		NewAccountPanel.add(hpUN);
		NewAccountPanel.add(hpP1);
		NewAccountPanel.add(hpP2);
		NewAccountPanel.add(hpSave);
		
		//init the Account Widget
		initWidget(NewAccountPanel);
		
		Save.addClickListener(this);
		
	}

	/**
	 * prep for transport of data
	 */
	private void saveData() {
		
		//init rpc request - send the data
		SaveAccount();
	}
	

	



	
	
	/**
	 * 
	 */
	public void onClick(Widget sender) {
		
		if (sender == Save) {
			this.saveData();
		}

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
    	//this.drawLoading();
    	
		// service returns a result
		AsyncCallback callback_SaveAccount = new AsyncCallback() {
			
			//ajax rpc fail
			public void onFailure(Throwable caught) {
			}

			//ajax rpc success
			public void onSuccess(Object result) {
			}
		};

		// execute the service and request for rpc method
		try {
			Account account = new Account();
			account.setSessionID("12");
			callProvider.saveAccount(account, callback_SaveAccount);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
    }

    

    

	
}
