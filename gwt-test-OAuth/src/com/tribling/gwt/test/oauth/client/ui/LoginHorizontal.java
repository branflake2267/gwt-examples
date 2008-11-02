package com.tribling.gwt.test.oauth.client.ui;

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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tribling.gwt.test.oauth.client.Global;
import com.tribling.gwt.test.oauth.client.LoadingWidget;

public class LoginHorizontal extends Composite implements ClickListener, KeyboardListener, ChangeListener, FocusListener {

	private ChangeListenerCollection changeListeners;
	
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
	private PushButton bLogin;
	
	// forgot password, ask for it
	private PushButton bForgot;
	
	// forgot password
	
	private Hyperlink hAccountSettings;
	private Hyperlink hAccountCreate;
	private Hyperlink hAccountLogout;
	
	private Hyperlink hForgotPassword = new Hyperlink("Forgot Password","account_ForgotPassword");
	private Hyperlink hAccountLogin = new Hyperlink("Login","account_Login");
	
	
	// true, when logged in, false, when not
	// this will allow to apply logic off this
	private boolean loginStatus = false;
	
	// input box labels
	private String inputLabel_ConsumerKey = "Email";
	private String inputLabel_consumerSecret = "Password";
	
	/**
	 * constructor
	 */
	public LoginHorizontal() {
	
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(wLoading);
		hp.add(pUi);
		
		initWidget(pWidget);
		
		// observers
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
		
	}
	
	public void draw() {
		
		//TODO - check for saved session cookie
		
		if (loginStatus == false) {
			drawLoginInputs();
		} 
		
	}
	
	private void drawLoginInputs() {
		
		//default
		tbConsumerSecret.setVisible(true);
		tbConsumerSecretPass.setVisible(false);
		
		// reset ui
		pWidget.clear();
		
		cbRemberMe.setText("Remember Me");
		bLogin = new PushButton("Login");
		
		hAccountCreate = new Hyperlink("Create Account", "account_Create");
		
		tbConsumerKey.setTitle(inputLabel_ConsumerKey);
		tbConsumerSecret.setTitle(inputLabel_consumerSecret);
		tbConsumerSecretPass.setTitle(inputLabel_consumerSecret);
		
		// hide loading by default
		hideLoading();
		
		// main login inputs
		HorizontalPanel ploginItems = new HorizontalPanel();
		ploginItems.setSpacing(4);
		ploginItems.add(tbConsumerKey);
		ploginItems.add(tbConsumerSecret);
		ploginItems.add(tbConsumerSecretPass);
		ploginItems.add(bLogin);
		
		
		// login options
		HorizontalPanel pOptions = new HorizontalPanel();
		pOptions.setSpacing(4);
		pOptions.add(cbRemberMe);
		pOptions.add(hForgotPassword);
		pOptions.add(hAccountCreate);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(ploginItems);
		vp.add(pOptions);
		
		// TODO - move this to a floating overlay
		vp.add(pError);

		pWidget.add(vp);
		
		drawInputLabel_key();
		drawInputLabel_secret();
		
		tbConsumerKey.addStyleName("login-Ui-InputConsumerKey");
		tbConsumerSecretPass.addStyleName("login-Ui-InputConsumerSecret");
		tbConsumerSecretPass.addStyleName("login-Ui-InputConsumerSecret");
	}
	
	private void drawForgotPassword() {
		
		// reset ui
		pWidget.clear();
		
		bForgot = new PushButton("Get Password");
		bForgot.setTitle("This will reset your password, and send you a email of the new password to login.");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(4);
		hp.add(tbConsumerKey);
		hp.add(bForgot);
		hp.add(hAccountLogin);
		
		pWidget.add(hp);
		
	}
	
	private void drawLoggedIn() {
		hAccountSettings = new Hyperlink("My Account","account");
		hAccountLogout = new Hyperlink("Logout", "account_Logout");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(hAccountSettings);
		hp.add(hAccountLogout);
		
	}
	
	private void drawError(String error) {
		pError.clear();
		pError.setVisible(true);
		
		error = Global.removeHtmlTags(error);
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
	
	private String getConsumerKey() {
		String consumerKey = tbConsumerKey.getText();
		consumerKey = Global.removeHtmlTags(consumerKey).trim();
		return consumerKey; 
	}
	
	private String getConsumerSecret() {
		String consumerSecret = tbConsumerSecretPass.getText();
		consumerSecret = Global.removeHtmlTags(consumerSecret).trim();
		return consumerSecret;
	}
	
	public void drawLoading() {
		wLoading.show();
	}
	
	public void hideLoading() {
		wLoading.hide();
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
		if (sender == tbConsumerKey) {
			checkInputLabel_key(true);
		} else if (sender == tbConsumerSecret) {
			changePasswordInput();
		} else if (sender == cbRemberMe) {
		} else if (sender == hForgotPassword) {
			drawForgotPassword();
		} else if (sender == hAccountLogin) {
			drawLoginInputs();
		}
	}

	public void onKeyDown(Widget sender, char keyCode, int modifiers) {
		if (sender == tbConsumerKey) {
		} else if (sender == tbConsumerSecret) {
		} else if (sender == cbRemberMe) {
		} else {
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
		} else if (sender == tbConsumerSecretPass) {
			checkInputLabel_secret();
		} else if (sender == cbRemberMe) {
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
