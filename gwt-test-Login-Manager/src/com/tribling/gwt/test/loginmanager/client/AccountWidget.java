package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AccountWidget extends Composite implements ClickListener{

	
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
	
	// change listeners for this widget
	private ChangeListenerCollection changeListeners;
	
	
	/**
	 * constructor
	 */
	public AccountWidget(String SessionID) {
		
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
		
		//This cause me major problems, b/c I forgot to call this ....
		LoginManagerProvider();
		
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
		//clear the previous error for new one?
		pDisplayError.clear();
		
		String DisplayError = null;
		boolean Flag = false;
		
		//prep for transport
		String sFirstName = FirstName.getText();
		String sLastName = LastName.getText();
		
		//get UserName
		String sUserName = UserName.getText();
		
		//check passwords match
		String sPas1 = Password1.getText();
		String sPas2 = Password2.getText();
		
		if (sFirstName.equals("")) {
			Flag = true;
			DisplayError = "No First Name";
		}
		
		if (sLastName.equals("")) {
			Flag = true;
			DisplayError = "No Last Name";
		}
		
		if (sUserName.equals("")) {
			Flag = true;
			DisplayError = "No User Name";
		}
		
		if (sPas1.equals("")) {
			Flag = true;
			DisplayError = "No Password";
		}
		
		if (sPas2.equals("")) {
			Flag = true;
			DisplayError = "No Password";
		}
		
		if (sPas2.equals("")) {
			Flag = true;
			DisplayError = "Passwords do not match";
		}
		
		if (Flag == true) {
			Window.alert("test");
			pDisplayError.add(new Label(DisplayError));
			return;
		}
		
		
		
		//init object that we are going to use to pass rpc data through
		Account account = new Account();
		
		//prep for transport
		
		//SET TIME STAMP~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		account.setSessionID(this.SessionID);
		account.setFirstName(sFirstName);
		account.setLastName(sLastName);
		account.setUserName(sUserName);
		account.setPassword(sPas1);


		
		//init rpc request - send the data
		saveAccount(account);
	}
	

	
	/**
	 * process the rpc response
	 * @param account
	 */
	private void processCallBack(Account account) {
		
		//get session id - set cookie
		this.SessionID = account.getSessionID();
		
		//fire change to set SessionID in session manager
		if (SessionID != null) {
			
			Window.alert("debug: fire change");
			
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
		}
		
		
		//get display error
		String DisplayError = account.getDisplayError();
		if (DisplayError != null) {
			pDisplayError.add(new Label(DisplayError));
		}
		
		//mark Saved
		pDisplayError.add(new Label("Saved"));
		
		//have to add logic from the begging of the cycle
		//pDisplayError.add(new Label("New Account Created"));
		
	}
	
	public String getSessionID() {
		return this.SessionID;
	}
	
	
	/**
	 * 
	 */
	public void onClick(Widget sender) {
		
		if (sender == Save) {
			this.saveData();
		}
		
		//if (changeListeners != null) {
			//changeListeners.fireChange(this);
		//}
	}
	
	
	private void drawLoading() {
		HorizontalPanel loading = new HorizontalPanel();
		loading.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		String sImage = GWT.getModuleBaseURL() + "loading2.gif";
	    Image image = new Image(sImage);
	    pLoadingStatus.setTitle("Talking to server.");
		pLoadingStatus.add(image);
	}
	
	private void clearLoading() {
		pLoadingStatus.clear();
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
    	callProvider = (LoginManagerServiceAsync) GWT.create(LoginManagerService.class);
        ServiceDefTarget target = (ServiceDefTarget) callProvider;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "LoginManagerService";
        target.setServiceEntryPoint(moduleRelativeURL);
    }
    
	
	/**
	 * Process the sign in
	 * 
	 */
    public void saveAccount(Account account) {
    	this.drawLoading();
    	
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			
			//ajax rpc fail
			public void onFailure(Throwable caught) {
				
				// TODO - add something here
				
			}

			//ajax rpc success
			public void onSuccess(Object result) {
				Account account = (Account) result; //cast the result into the object to use
				processCallBack(account);
				clearLoading();
			}
		};

		// execute the service and request for rpc method
		callProvider.saveAccount(account, callback);
    }

    
	public boolean getLoginStatus() {
		if (this.SessionID != null)	{
			return true;
		}
		return false;
	}
    

	
}
