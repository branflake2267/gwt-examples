package com.tribling.gwt.test.oauth.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tribling.gwt.test.oauth.client.Global;
import com.tribling.gwt.test.oauth.client.LoadingWidget;

public class LoginHorizontal extends Composite implements ClickListener, KeyboardListener {

	private ChangeListenerCollection changeListeners;
	
	// main widget div
	private FlowPanel pWidget = new FlowPanel();
	
	// used for a username
	private TextBox tbConsumerKey = new TextBox();
	
	// used for a password
	private TextBox tbConsumerSecret = new TextBox();
	
	// remember me option
	private CheckBox cbRemberMe = new CheckBox();
	
	// login
	private PushButton bLogin;
	

	
	// TODO - move this to a floating overlay below the input boxes
	private VerticalPanel pError = new VerticalPanel();
	
	// loading Notification
	private LoadingWidget wLoading = new LoadingWidget();
	
	// forgot password
	private Hyperlink hForgotPassword;
	private Hyperlink hAccountSettings;
	private Hyperlink hAccountCreate;
	private Hyperlink hAccountLogout;
	
	/**
	 * constructor
	 */
	public LoginHorizontal() {
	
		initWidget(pWidget);
		
		// observers
		tbConsumerKey.addClickListener(this);
		tbConsumerKey.addKeyboardListener(this);
		tbConsumerSecret.addClickListener(this);
		tbConsumerSecret.addKeyboardListener(this);
		cbRemberMe.addKeyboardListener(this);
		cbRemberMe.addClickListener(this);
		
		// style
		pWidget.setStyleName("login-LoginHorizontal");
		
		// defaults
		pError.setVisible(false);
		bLogin.setVisible(true);
	}
	
	public void drawLoginInputs() {
		
		// reset ui
		pWidget.clear();
		
		cbRemberMe.setText("Remember Me");
		bLogin = new PushButton("Login");
		
		hForgotPassword = new Hyperlink("Forgot password", "account_ForgotPassword");
		hAccountCreate = new Hyperlink("Create Account", "account_Create");
		
		// hide loading by default
		hideLoading();
		
		// main login inputs
		HorizontalPanel ploginItems = new HorizontalPanel();
		ploginItems.setSpacing(4);
		ploginItems.add(tbConsumerKey);
		ploginItems.add(tbConsumerSecret);
		ploginItems.add(bLogin);
		ploginItems.add(wLoading);
		
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
	}
	
	public void drawLoggedIn() {
		hAccountSettings = new Hyperlink("My Account","account");
		hAccountLogout = new Hyperlink("Logout", "account_Logout");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(hAccountSettings);
		hp.add(hAccountLogout);
		
	}
	
	public void drawError(String error) {
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
	
	public String getConsumerKey() {
		String consumerKey = tbConsumerKey.getText();
		consumerKey = Global.removeHtmlTags(consumerKey).trim();
		return consumerKey; 
	}
	
	public String getComsumerSecret() {
		String consumerSecret = tbConsumerSecret.getText();
		consumerSecret = Global.removeHtmlTags(consumerSecret).trim();
		return consumerSecret;
	}
	
	public void drawLoading() {
		
		bLogin.setVisible(false);
		tbConsumerKey.setEnabled(false);
		tbConsumerSecret.setEnabled(false);
		
	}
	
	public void hideLoading() {
		bLogin.setVisible(true);
		tbConsumerKey.setEnabled(true);
		tbConsumerSecret.setEnabled(true);
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
		} else if (sender == tbConsumerSecret) {
		} else if (sender == cbRemberMe) {
		} else {
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
	
}
