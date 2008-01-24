package com.tribling.gwt.test.loginmanager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author branflake2267
 *
 *
 * TODO green red box depending on critieria of input
 * TODO Display Error to methods, draw and clear
 */
public class AccountWidget extends Composite implements ClickListener{
	
	//rpc init Var 
	private LoginManagerServiceAsync callProvider;
	
	private VerticalPanel pAccountPanel = new VerticalPanel();
	private VerticalPanel pAccountpanelLoading = new VerticalPanel();

	private HorizontalPanel pAccountPanelTitle = new HorizontalPanel();

	private HorizontalPanel pDisplayNotify = new HorizontalPanel();
	
	private HorizontalPanel pSavingStatus = new HorizontalPanel();
	
	//Inputs
	private TextBox FirstName = new TextBox();
	private TextBox LastName = new TextBox();
	private HorizontalPanel hpUserInput = new HorizontalPanel();
	private TextBox UserName = new TextBox();
	private PasswordTextBox Password1 = new PasswordTextBox();
	private PasswordTextBox Password2 = new PasswordTextBox();
	
	private PushButton Save = new PushButton("Save");
		
	private String SessionID = null;
	
	// change listeners for this widget
	private ChangeListenerCollection changeListeners;
	
	
	/**
	 * widget maker constructor
	 */
	public AccountWidget() {
	
		pAccountPanelTitle.add(new Label("New Account"));
		
		//FirstName
		HorizontalPanel hpFN = new HorizontalPanel();
		Label lFN = new Label("First Name:");
		lFN.setStyleName("account-Fields");
		hpFN.add(lFN);
		hpFN.add(FirstName);
	
		//LastName
		HorizontalPanel hpLN = new HorizontalPanel();
		Label lLN = new Label("Last Name:");
		lLN.setStyleName("account-Fields");
		hpLN.add(lLN);
		hpLN.add(LastName);
		
		//UserName
		HorizontalPanel hpUserName = new HorizontalPanel();
		Label lUserName = new Label("User Name");
		lUserName.setStyleName("account-Fields");
		hpUserName.add(lUserName);
		hpUserInput.add(UserName); //can hpUserInput this to label
		hpUserName.add(hpUserInput); //changeable from input to label
		
		
		
		//Password 1
		HorizontalPanel hpP1 = new HorizontalPanel();
		Label lP1 = new Label("Password:");
		lP1.setStyleName("account-Fields");
		hpP1.add(lP1);
		hpP1.add(Password1);
		
		//Password 2
		HorizontalPanel hpP2 = new HorizontalPanel();
		Label lP2 = new Label("Confirm:");
		lP2.setStyleName("account-Fields");
		hpP2.add(lP2);
		hpP2.add(Password2);
		
		//Saving Anime
		HorizontalPanel hpSave = new HorizontalPanel();
		hpSave.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		hpSave.add(Save);
		hpSave.add(pSavingStatus);
		
		


		pAccountPanel.add(pAccountPanelTitle);
		pAccountPanel.add(pDisplayNotify);
		pAccountPanel.add(hpUserName);
		pAccountPanel.add(new HTML("&nbsp;"));
		pAccountPanel.add(hpFN);
		pAccountPanel.add(hpLN);
		pAccountPanel.add(new HTML("&nbsp;"));
		pAccountPanel.add(hpP1);
		pAccountPanel.add(hpP2);
		pAccountPanel.add(hpSave);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(pAccountPanel); //inputs panel
		vp.add(pAccountpanelLoading); //for loading icon
		
		//init the Account Widget
		initWidget(vp);
		
		Save.addClickListener(this);
		
		//rpc init
		LoginManagerProvider();
	}
	

	




	
	
	/**
	 * update inputs
	 * @param account
	 */
	public void updateAccountData(Account account) {
		
		pAccountPanelTitle.clear();
		pAccountPanelTitle.add(new Label("Edit Profile"));
		
		hpUserInput.clear(); //clear the username input or label user name
		
		String sUserName = account.getUserName();
		hpUserInput.add(new Label(sUserName));
		FirstName.setText(account.getFirstName());
		LastName.setText(account.getLastName());	
	}
	
	/**
	 * draw on loading account data
	 */
	private void drawLoadingAccountData() {
		
		HorizontalPanel loading = new HorizontalPanel();
		loading.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		String sImage = GWT.getModuleBaseURL() + "loading.gif";
	    Image image = new Image(sImage);
	    pAccountpanelLoading.add(image);
	    
		pAccountPanel.setVisible(false);
		pAccountpanelLoading.setVisible(true);
	}
	
	/**
	 * clear loading account data
	 */
	private void clearLoadingAccountData() {
		pAccountPanel.setVisible(true);
		pAccountpanelLoading.setVisible(false);
	}

	
	/**
	 * prep for transport of data
	 */
	private void saveData() {

		//this.clearDisplayNotify(); //clear display notification
		
		String DisplayError = null;
		boolean Flag = false;
		String sUserName = null;
		
		//prep for transport
		String sFirstName = FirstName.getText();
		String sLastName = LastName.getText();
		String sPas1 = Password1.getText();
		String sPas2 = Password2.getText();
		
		// check user name if no session - new account
		if (SessionID == null) {
			sUserName = UserName.getText();
			
			if (sUserName.equals("")) {
				Flag = true;
				DisplayError = "No User Name";
			}
		}

		if (sFirstName.equals("")) {
			Flag = true;
			DisplayError = "No First Name";
		}

		if (sLastName.equals("")) {
			Flag = true;
			DisplayError = "No Last Name";
		}

		if (sPas1.equals(null) == false | sPas1.equals(null) == false) {
			if (sPas2.equals(sPas1) == false) {
				Flag = true;
				DisplayError = "Passwords do not match";
			}
		}
		
		//show error in panel if something isnt correct
		if (Flag == true) {
			this.drawDisplayNotify(DisplayError);
			return;
		}
		

		//init object that we are going to use to pass rpc data through
		Account account = new Account();
		
		//prep for transport
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
	private void processCallBackOnSaveAccount(Account account) {
		
		//IS ther an Error - get display error from RPC
		String DisplayError = account.getDisplayError();
		if (DisplayError != "") {
			this.drawDisplayNotify(DisplayError);
		}
		
		//set SessionID from RPC
		if (account.getSessionID().equals(null) == false) {
			this.SessionID = account.getSessionID();
		}
		
		
		//fire change to set SessionID in session manager
		if (SessionID != null) {
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}
			
			//show saved if there is a sessionid
			this.drawDisplayNotify("Saved");
			
			//new account?? show new account created
			
		}
	}
	
	
	public String getSessionID() {
		return this.SessionID;
	}
	
	public void drawDisplayNotify(String s) {
		pDisplayNotify.add(new Label(s));
	}

	public void clearDisplayNotify() {
		pDisplayNotify.clear();
	}
	
	private void drawSavingAnime() {
		HorizontalPanel loading = new HorizontalPanel();
		loading.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		String sImage = GWT.getModuleBaseURL() + "loading2.gif";
	    Image image = new Image(sImage);
	    pSavingStatus.setTitle("Talking to server.");
		pSavingStatus.add(image);
	}
	
	private void clearSavingAnime() {
		pSavingStatus.clear();
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
	 * on signout clear this values, incase this session comes back to this widget
	 */
	public void processSignOut() {
		this.SessionID = null;
		
		//change back to original
		pAccountPanelTitle.clear();
		pAccountPanelTitle.add(new Label("New Account"));
		hpUserInput.clear();
		hpUserInput.add(UserName);
		
		UserName.setText("");
		FirstName.setText("");
		LastName.setText("");
		Password1.setText("");
		Password2.setText("");
		this.clearDisplayNotify();
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
	 * Save Account Data
	 */
    public void saveAccount(Account account) {
    	this.drawSavingAnime(); //start loading icon
    	
		// service returns a result
		AsyncCallback callback = new AsyncCallback() {
			
			//ajax rpc fail
			public void onFailure(Throwable caught) {
				// TODO - add something here
			}
			//ajax rpc success
			public void onSuccess(Object result) {
				Account account = (Account) result; //cast the result into the object to use
				
				clearSavingAnime(); //end loading icon
				
				processCallBackOnSaveAccount(account);
				
			}
		};
		// execute the service and request for rpc method
		callProvider.saveAccount(account, callback);
    }

    
	/**
	 * Save Account Data
	 */
    public void getAccountData(String SessionID) {
    	
    	this.clearDisplayNotify();
    	this.drawLoadingAccountData();    	
    	
    	
		// service returns a result
		AsyncCallback callback_getAccount = new AsyncCallback() {
			
			//ajax rpc fail
			public void onFailure(Throwable caught) {
				// TODO - add something here
			}
			//ajax rpc success
			public void onSuccess(Object result) {
				Account account = (Account) result; //cast the result into the object to use
				
				//update inputs
				updateAccountData(account);
			
				//clear loading icon
				clearLoadingAccountData();
			}
		};
		// execute the service and request for rpc method
		callProvider.getAccount(SessionID, callback_getAccount);
    }
    
	public boolean getLoginStatus() {
		if (this.SessionID != null)	{
			return true;
		}
		return false;
	}
    
	public void loadAccountData(String SessionID) {
		this.SessionID = SessionID;
		
		if (SessionID.equals(null) == false) {
			this.getAccountData(this.SessionID);
		}
	}

	
}
