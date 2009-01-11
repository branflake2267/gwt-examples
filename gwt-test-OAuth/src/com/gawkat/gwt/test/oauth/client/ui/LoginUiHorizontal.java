package com.gawkat.gwt.test.oauth.client.ui;

import com.gawkat.gwt.test.oauth.client.LoadingWidget;
import com.gawkat.gwt.test.oauth.client.global.Global_String;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginUiHorizontal extends Composite implements ClickListener, KeyboardListener, ChangeListener, FocusListener {

	private ChangeListenerCollection changeListeners;
	private int changeEvent; 
	
	// main widget div
	private FlowPanel pWidget = new FlowPanel();
	
	// used for a username
	private TextBox tbConsumerKey = new TextBox();
	
	// used for a password
	private TextBox tbConsumerSecret = new TextBox();
	private PasswordTextBox tbConsumerSecretPass = new PasswordTextBox();
		
	// remember me option
	private CheckBox cbRemberMe = new CheckBox();
	
	// TODO - move this to a floating overlay below the input boxes
	private VerticalPanel pError = new VerticalPanel();
	
	// loading Notification
	private LoadingWidget wLoading = new LoadingWidget();
	
	// put the user interface items here
	private	FlowPanel pUi = new FlowPanel();
	
	// login button
	private PushButton bLogin = new PushButton("Login");
	
	// forgot password, ask for it
	private PushButton bForgot = new PushButton("Get Password");
	
	private Hyperlink hAccountSettings;
	private Hyperlink hAccountCreate;
	private Hyperlink hAccountLogout;
	
	private Hyperlink hForgotPassword = new Hyperlink("Forgot Password","account_ForgotPassword");
	private Hyperlink hAccountLogin = new Hyperlink("Login","account_Login");
	
	
	// true, when logged in, false, when not
	// this will allow to apply logic off this
	private boolean loginStatus = false;
	
	// input box labels
	private String inputLabel_ConsumerKey = "Username";
	private String inputLabel_consumerSecret = "Password";
	
	/**
	 * constructor
	 */
	public LoginUiHorizontal() {
	
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(wLoading);
		hp.add(pUi);
		
		pWidget.add(hp);
		
		initWidget(pWidget);
		
		// observers
		bLogin.addClickListener(this);
		bForgot.addClickListener(this);
		
		tbConsumerKey.addClickListener(this);
		tbConsumerKey.addChangeListener(this);
		tbConsumerKey.addKeyboardListener(this);
		tbConsumerKey.addFocusListener(this);
		
		tbConsumerSecret.addClickListener(this);
		tbConsumerSecret.addChangeListener(this);
		tbConsumerSecret.addKeyboardListener(this);
		tbConsumerSecret.addFocusListener(this);

		tbConsumerSecretPass.addClickListener(this);
		tbConsumerSecretPass.addChangeListener(this);
		tbConsumerSecretPass.addKeyboardListener(this);
		tbConsumerSecretPass.addFocusListener(this);
		
		cbRemberMe.addKeyboardListener(this);
		cbRemberMe.addClickListener(this);
		
		hAccountLogin.addClickListener(this);
		hForgotPassword.addClickListener(this);
		
		// style
		pWidget.setStyleName("login-Ui");
		
		// defaults
		pError.setVisible(false);
		
		hp.setCellVerticalAlignment(wLoading, VerticalPanel.ALIGN_MIDDLE);
		
		// debug
		//pWidget.addStyleName("test2");

		
	}
	
	public void draw() {
		
		if (loginStatus == false) {
			drawLoginInputs();
		} 
		
		// TODO - what will happen if this was called, and one was already logged in
		
	}
	
	public void setLoginStatus(boolean bol) {
		this.loginStatus = bol;
		
		// TODO - redraw?
	}
	
	/**
	 * auto put a name and password in, and submit for testing/debugging
	 * 
	 * !!!! Remove me later - for testing
	 * 
	 */
	public void autoLogin(String key, String password) {
		drawLoading();
		tbConsumerKey.setText(key);
		tbConsumerSecret.setVisible(false);
		tbConsumerSecretPass.setVisible(true);
		tbConsumerSecretPass.setText(password);
		
		fireChange(LoginUi.LOGIN);
	}
	
	private void drawLoginInputs() {
		
		//default
		tbConsumerSecret.setVisible(true);
		tbConsumerSecretPass.setVisible(false);
		
		// reset ui
		pUi.clear();
		
		cbRemberMe.setText("Remember Me");
		
		
		hAccountCreate = new Hyperlink("Create Account", "account_Create");
		
		tbConsumerKey.setTitle(inputLabel_ConsumerKey);
		tbConsumerSecret.setTitle(inputLabel_consumerSecret);
		tbConsumerSecretPass.setTitle(inputLabel_consumerSecret);
		
		// hide loading by default
		hideLoading();
		
		// main login inputs
		HorizontalPanel ploginItems = new HorizontalPanel();
		ploginItems.setWidth("100%");
		ploginItems.add(tbConsumerKey);
		ploginItems.add(tbConsumerSecret);
		ploginItems.add(tbConsumerSecretPass);
		ploginItems.add(bLogin);
		
		
		// login options
		HorizontalPanel pOptions = new HorizontalPanel();
		//pOptions.setSpacing(4);
		pOptions.add(cbRemberMe);
		pOptions.add(hForgotPassword);
		pOptions.add(hAccountCreate);
		
		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.add(ploginItems);
		vp.add(pOptions);
		
		// TODO - move this to a floating overlay
		vp.add(pError);

		pUi.add(vp);
		
		drawInputLabel_key();
		drawInputLabel_secret();
		
		tbConsumerKey.addStyleName("login-Ui-InputConsumerKey");
		tbConsumerSecret.addStyleName("login-Ui-InputConsumerSecret");
		tbConsumerSecretPass.addStyleName("login-Ui-InputConsumerSecret");
		pOptions.setCellHorizontalAlignment(hForgotPassword, HorizontalPanel.ALIGN_RIGHT);
		pOptions.setCellHorizontalAlignment(hAccountCreate, HorizontalPanel.ALIGN_RIGHT);
		pOptions.setCellVerticalAlignment(hForgotPassword, VerticalPanel.ALIGN_BOTTOM);
		pOptions.setCellVerticalAlignment(hAccountCreate, VerticalPanel.ALIGN_BOTTOM);
		pOptions.addStyleName("login-Ui-InputOptions");
		pOptions.setWidth("100%");

		
		//vp.addStyleName("test1");
		//pOptions.addStyleName("test2");
	}
	
	private void drawForgotPassword() {
		
		// reset ui
		pUi.clear();
		
		bForgot.setTitle("This will reset your password, and send you a email of the new password to login.");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(4);
		hp.add(tbConsumerKey);
		hp.add(bForgot);
		hp.add(hAccountLogin);
		
		pUi.add(hp);
		
		// style
		hForgotPassword.addStyleName("login-Ui-InputOptions");
		hp.setCellVerticalAlignment(hAccountLogin, VerticalPanel.ALIGN_BOTTOM);
	}
	
	// TODO
	private void drawLoggedIn() {
		hideLoading();
		
		hAccountSettings = new Hyperlink("My Account","account");
		hAccountLogout = new Hyperlink("Logout", "account_Logout");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(hAccountSettings);
		hp.add(hAccountLogout);
		
	}
	
	// TODO
	private void drawError(String error) {
		hideLoading();
		
		pError.clear();
		pError.setVisible(true);
		
		error = Global_String.removeHtmlTags(error);
		HTML htmlError = new HTML(error);
		
		pError.add(htmlError);
		
		// hide error shortly after fire
		Timer t = new Timer() {
            public void run() {
            	pError.setVisible(false);
            }
	    };
	    t.schedule(3000);

	}
	
	private void drawInputLabel_key() {
		tbConsumerKey.setText(inputLabel_ConsumerKey);
		tbConsumerKey.addStyleName("login-Ui-InputLabel");
	}
	
	private void drawInputLabel_secret() {
		tbConsumerSecret.setText(inputLabel_consumerSecret);
		tbConsumerSecret.addStyleName("login-Ui-InputLabel");
	}
	
	protected String getConsumerKey() {
		String consumerKey = tbConsumerKey.getText();
		consumerKey = Global_String.removeHtmlTags(consumerKey).trim();
		return consumerKey; 
	}
	
	protected String getConsumerSecret() {
		String consumerSecret = tbConsumerSecretPass.getText();
		consumerSecret = Global_String.removeHtmlTags(consumerSecret).trim();
		return consumerSecret;
	}
	
	private void drawLoading() {
		wLoading.show();
		
		tbConsumerKey.setEnabled(false);
		tbConsumerSecretPass.setEnabled(false);
	}
	
	private void hideLoading() {
		//wLoading.hide();
		
		tbConsumerKey.setEnabled(true);
		tbConsumerSecretPass.setEnabled(false);
	}
	
	private void checkInputLabel_key(boolean click) {
		
		if (getConsumerKey().length() == 0 && click == false) {
			tbConsumerKey.setText(inputLabel_ConsumerKey);
			tbConsumerKey.addStyleName("login-Ui-InputLabel");
			
		} else if (getConsumerKey().equals(inputLabel_ConsumerKey) == true) {
			tbConsumerKey.setText("");
			tbConsumerKey.removeStyleName("login-Ui-InputLabel");
		}
	}
	
	private void changePasswordInput() {
		tbConsumerSecret.setVisible(false);
		tbConsumerSecretPass.setVisible(true);
		tbConsumerSecretPass.setFocus(true);
	
	}
	
	private void checkInputLabel_secret() {
		if (getConsumerSecret().length() == 0 ) {
			tbConsumerSecret.setVisible(true);
			tbConsumerSecretPass.setVisible(false);
		} 
	}
	
	public int getChangeEvent() {
		return changeEvent;
	}
	
	private void fireChange(int changeEvent) {
		this.changeEvent = changeEvent;
		
		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}
	
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null)
			changeListeners = new ChangeListenerCollection();
		changeListeners.add(listener);
	}
	
	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null)
			changeListeners.remove(listener);
	}

	public void onClick(Widget sender) {
	  
		if (sender == bLogin) {
			drawLoading();
			fireChange(LoginUi.LOGIN);
		
		} else if (sender == bForgot) {
			drawLoading();
			fireChange(LoginUi.FORGOT_PASSWORD);
		
		} else if (sender == tbConsumerKey) {
			checkInputLabel_key(true);
		
		} else if (sender == tbConsumerSecret) {
			changePasswordInput();
		
		} else if (sender == cbRemberMe) {
		  // TODO
		
		} else if (sender == hForgotPassword) {
			drawForgotPassword();
		
		} else if (sender == hAccountLogin) {
			drawLoginInputs();
		}
		
	}

	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
		if (sender == tbConsumerKey) {
		  // TODO
		} else if (sender == tbConsumerSecret) {
		  // TODO
		} else if (sender == cbRemberMe) {
		  // TODO
		} else {
		  // TODO
		}
	}

	public void onKeyPress(Widget sender, char keyCode, int modifiers) {
		if (sender == tbConsumerKey) {
		} else if (sender == tbConsumerSecret) {
		} else if (sender == cbRemberMe) {
		} else {
		}
	}

	public void onKeyUp(Widget sender, char keyCode, int modifiers) {
		if (sender == tbConsumerKey) {
		} else if (sender == tbConsumerSecret) {	
		} else if (sender == cbRemberMe) {
		} else {
		}
	}

	public void onChange(Widget sender) {
		if (sender == tbConsumerKey) {
			checkInputLabel_key(false);
		} else if (sender == tbConsumerSecret) {
		  // TODO
		} else if (sender == tbConsumerSecretPass) {
			checkInputLabel_secret();
		} else if (sender == cbRemberMe) {
		  // TODO
		}
	}

	public void onFocus(Widget sender) {
	}

	public void onLostFocus(Widget sender) {
		if (sender == tbConsumerKey) {
			checkInputLabel_key(false);
		} else if (sender == tbConsumerSecret) {
		}  else if (sender == tbConsumerSecretPass) {
			checkInputLabel_secret();
		} else if (sender == cbRemberMe) {
		}
	}
	
	
}
