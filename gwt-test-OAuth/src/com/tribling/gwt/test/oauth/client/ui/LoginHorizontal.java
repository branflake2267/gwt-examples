package com.tribling.gwt.test.oauth.client.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

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
	private PushButton bLogin = new PushButton();
	
	// forgot password
	private Hyperlink hForgotPassword;
	
	/**
	 * constructor
	 */
	public LoginHorizontal() {
	
		cbRemberMe.setText("Remember Me");
		hForgotPassword = new Hyperlink("Forgot password", "login_forgotPassword");
		
		initWidget(pWidget);
		
		tbConsumerKey.addClickListener(this);
		tbConsumerKey.addKeyboardListener(this);
		tbConsumerSecret.addClickListener(this);
		tbConsumerSecret.addKeyboardListener(this);
		cbRemberMe.addKeyboardListener(this);
		cbRemberMe.addClickListener(this);
		
	}
	
	public void drawUi() {
		pWidget.clear();
		
		HorizontalPanel ploginItems = new HorizontalPanel();
		ploginItems.add(tbConsumerKey);
		ploginItems.add(tbConsumerSecret);
		ploginItems.add(bLogin);
		
		HorizontalPanel pOptions = new HorizontalPanel();
		pOptions.add(cbRemberMe);
		pOptions.add(hForgotPassword);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(ploginItems);
		vp.add(pOptions);
		
		pWidget.add(vp);
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
