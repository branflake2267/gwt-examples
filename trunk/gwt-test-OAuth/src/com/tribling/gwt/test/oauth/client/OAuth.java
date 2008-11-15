package com.tribling.gwt.test.oauth.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.rebind.keygen.MD5KeyGenerator;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tribling.gwt.test.oauth.client.tests.TestRpcCall;
import com.tribling.gwt.test.oauth.client.ui.LoginUi;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OAuth implements EntryPoint, ClickListener, ChangeListener {

	// web site consumer key
	private String consumerKeyForSite = "Gawkat.com_oAuthApp_01";
	
	// this manages the users priviledges to protected resources
	private SessionManager sessionManager;

	// test button to auto login
	private PushButton bTestLogin = new PushButton("Test Login");
	

	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		initSessionManager();
		
		// test and debug stuff
		testStuff();
		
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bTestLogin);
		
		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.add(hp);
		vp.add(new HTML("&nbsp;"));
		vp.add(new HTML("Under development - showing tests below."));
		RootPanel.get("Login_TestButton").add(vp);
		
		vp.setCellHorizontalAlignment(hp, HorizontalPanel.ALIGN_CENTER);
		
		
		
		bTestLogin.addClickListener(this);
		
		
		
		MD5KeyGenerator m = new MD5KeyGenerator();
	}
	
	/**
	 * manage the session and acess to protected resources 
	 * for the web site and user using the web site
	 */
	private void initSessionManager() {
		sessionManager = new SessionManager();
		sessionManager.setAppConsumerKey(consumerKeyForSite); 
		sessionManager.setLoginUiDiv("LoginUI", LoginUi.LOGIN_HORIZONTAL);
		sessionManager.drawUi();
	}
	

	private void autoLogin() {

		String email = "test@gonevertical.com";
		String password = "test*7";
		sessionManager.autoLogin(email, password);
		
		sessionManager.addChangeListener(this);
	}
	
	/**
	 * test the stuff needed for this setup
	 */
	public void testStuff() {
		testRpcSetup();
		testSha1JsSetup();
	}

	/**
	 * call the native javascript
	 */
	private void testSha1JsSetup() {
		boolean works = sha1Test();
		RootPanel.get().add(new HTML("sha1.js file works?: " + works));
	}
	
	/**
	 * test out the included javascript
	 * run the native js method
	 */
	private native boolean sha1Test() /*-{
		return $wnd.sha1_vm_test();
	}-*/;
	
	/**
	 * test out rpc
	 */
	private void testRpcSetup() {
		TestRpcCall testRpc = new TestRpcCall();
		RootPanel.get().add(testRpc);
	}



	// observe
	public void onClick(Widget sender) {
		if (sender == bTestLogin) {
			autoLogin();
		}
		
	}


	public void onChange(Widget sender) {
		
		if (sender == sessionManager) {
			
		}
		
	}
	
}
