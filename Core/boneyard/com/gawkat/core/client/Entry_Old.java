package com.gawkat.core.client;

import com.gawkat.core.client.global.SessionManager;
import com.gawkat.core.client.oauth.Sha1;
import com.gawkat.core.client.tests.TestRpcCallWidget;
import com.gawkat.core.client.ui.LoginUi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Entry_Old implements EntryPoint, ClickListener, ChangeListener {

	// web site consumer key
	private String appConsumerKey = "Gawkat.com_oAuthApp_01";
	
	 // hash - hmac_sha1 - key="salt", data="password"
	private String appConsumerSecret = "c1d0e06998305903ac76f589bbd6d4b61a670ba6";
	
	// this manages the users priviledges to protected resources
	private SessionManager sessionManager;

	// test button to auto login
	private PushButton bTestLogin = new PushButton("Test Login");
		
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// protective resource management
		// watch the applications session
		// watch the users session
		initSessionManager();
	
		
		/* observe a test button */
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(bTestLogin);
		
		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.add(hp);
		vp.add(new HTML("&nbsp;"));
		vp.add(new HTML("Under development - showing tests below."));
		RootPanel.get("Login_TestButton").add(vp);
		
		// Style
		vp.setCellHorizontalAlignment(hp, HorizontalPanel.ALIGN_CENTER);
		
		// Observe
		bTestLogin.addClickListener(this);
    /* observe a test button */
		
		// *****************************************************
		// TODO remove later - debug - test and debug stuff
		testStuff();
		
		// test
		try {
			Sha1 test = new Sha1();
			boolean b = test.test_Sha1();
			RootPanel.get().add(new HTML("sha1 test result: " + b));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * manage the session and acess to protected resources 
	 * for the web site and user using the web site
	 */
	private void initSessionManager() {
		//sessionManager = new SessionManager();
		sessionManager.setAppConsumerKey(appConsumerKey, appConsumerSecret); 
		// TODO - set this into another widget?
		//sessionManager.setUi("LoginUI", LoginUi.LOGIN_HORIZONTAL);
	}
	
	/**
	 * auto login
	 * 
	 * TODO - remove later
	 */
	private void autoLogin() {

		String email = "test@gonevertical.com";
		String password = "test*7";
		sessionManager.autoLogin(email, password);
		
		// observe session manager
		//sessionManager.addChangeListener(this);
	}
	
	/**
	 * test the stuff needed for this setup
	 */
	public void testStuff() {
		testRpcSetup();
		
	}

	/**
	 * test out rpc
	 */
	private void testRpcSetup() {
		TestRpcCallWidget testRpc = new TestRpcCallWidget();
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
