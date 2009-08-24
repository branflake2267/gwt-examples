package com.gawkat.core.client.ui;


import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.Global_String;
import com.gawkat.core.client.global.LoadingWidget;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class LoginUiHorizontal extends Composite implements ClickListener, KeyboardListener, ChangeListener, FocusListener, 
MouseOverHandler, MouseOutHandler {

	private ChangeListenerCollection changeListeners;
	private int changeEvent; 
	
	// main widget div
	private FocusPanel pWidget = new FocusPanel();
	
	// remember me, create ...
	private HorizontalPanel pOptions = new HorizontalPanel();
	
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
	
	private Hyperlink hAccountSettings = null;
	private Hyperlink hAccountCreate = null;
	private Hyperlink hAccountLogout = null;
	
	private Hyperlink hForgotPassword = new Hyperlink("Forgot Password", "login_ForgotPassword");
	private Hyperlink hAccountLogin = new Hyperlink("Login", "login_Login");
	
	
	// true, when logged in, false, when not
	// this will allow to apply logic off this
	private boolean loginStatus = false;
	
	// input box labels
	private String inputLabel_ConsumerKey = "Username";
	private String inputLabel_consumerSecret = "Password";
	
	private boolean hoverOnOff = false;
	
	/**
	 * constructor
	 */
	public LoginUiHorizontal() {
	
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(wLoading);
		hp.add(pUi);
		
		pWidget.add(hp);
		
		initWidget(pWidget);
		

		pWidget.addMouseOverHandler(this);
		pWidget.addMouseOutHandler(this);
		tbConsumerKey.addMouseOverHandler(this);
		tbConsumerKey.addMouseOutHandler(this);
		tbConsumerSecret.addMouseOverHandler(this);
		tbConsumerSecret.addMouseOutHandler(this);
		tbConsumerSecretPass.addMouseOverHandler(this);
		tbConsumerSecretPass.addMouseOutHandler(this);
		
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
		pWidget.setStyleName("core-login-ui");
		pError.setStyleName("core-Notification");
		
		// defaults
		pError.setVisible(false);
		
		hp.setCellVerticalAlignment(wLoading, VerticalPanel.ALIGN_MIDDLE);
		
		// debug
		//pWidget.addStyleName("test2");
	}
	
	public void draw() {
		
		if (loginStatus == false) {
		  tbConsumerKey.setText("");
		  tbConsumerSecretPass.setText("");
			drawLoginInputs();
		} else if (loginStatus == true) {
		  drawLoggedIn();
		}
		
	}
	
	public void setLoginStatus(boolean bol) {
		this.loginStatus = bol;
		draw();
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
		
		fireChange(EventManager.LOGIN);
	}
	
	private void drawLoginInputs() {
		
		//default
		tbConsumerSecret.setVisible(true);
		tbConsumerSecretPass.setVisible(false);
		
		// reset ui
		pUi.clear();
		
		cbRemberMe.setText("Remember Me");
		
		
		hAccountCreate = new Hyperlink("Create Account", "login_Create");
		
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
		
		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		vp.add(ploginItems);

		
		pUi.add(vp);
		
		drawInputLabel_key();
		drawInputLabel_secret();
		
		tbConsumerKey.addStyleName("core-login-ui-inputconsumerkey");
		tbConsumerSecret.addStyleName("core-login-ui-inputconsumersecret");
		tbConsumerSecretPass.addStyleName("core-login-ui-inputconsumersecret");
		pOptions.setCellHorizontalAlignment(hForgotPassword, HorizontalPanel.ALIGN_RIGHT);
		pOptions.setCellHorizontalAlignment(hAccountCreate, HorizontalPanel.ALIGN_RIGHT);
		pOptions.setCellVerticalAlignment(hForgotPassword, VerticalPanel.ALIGN_BOTTOM);
		pOptions.setCellVerticalAlignment(hAccountCreate, VerticalPanel.ALIGN_BOTTOM);
		pOptions.addStyleName("core-login-ui-inputoptions");
		pOptions.setWidth("100%");

    // login options
    drawOptions();
    drawError();
		
		
		//vp.addStyleName("test1");
		//pOptions.addStyleName("test2");
	}
	
	private void drawOptions() {
	  pOptions.clear();
    pOptions.add(cbRemberMe);
    pOptions.add(hForgotPassword);
    pOptions.add(hAccountCreate);
    pOptions.setVisible(false);
    RootPanel.get().add(pOptions);
    
    pOptions.addStyleName("core-login-ui-options");
	}
	
	private void drawError() {
	  pError.setVisible(false);
	  RootPanel.get().add(pError);
	}
	
	private void turnOnOptions(boolean b) {
	  int left = pWidget.getAbsoluteLeft();
	  int top = pWidget.getAbsoluteTop() + pWidget.getOffsetHeight();
	  int width = pWidget.getOffsetWidth();
	  pOptions.setWidth(""+width+"px");
	  RootPanel.get().setWidgetPosition(pOptions, left, top);
	  if (b == true) {
	    hoverOnOff = true;
	    pOptions.setVisible(true);
	  } else if (b == false) {
	   hoverOnOff = false;
	  }
	  hideOptionsSlowly();
	}
	
	private void hideOptionsSlowly() {
	  if (hoverOnOff == true) {
	    return;
	  }
	  Timer t = new Timer() {
      public void run() {
        if (hoverOnOff == false) {
          pOptions.setVisible(false);
        }
      }
    };
    t.schedule(4000);
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
		hForgotPassword.addStyleName("core-login-ui-inputoptions");
		hp.setCellVerticalAlignment(hAccountLogin, VerticalPanel.ALIGN_BOTTOM);
	}
	
	// TODO
	private void drawLoggedIn() {
		hideLoading();
		
    pUi.clear();
		
		hAccountSettings = new Hyperlink("My Account","account_Profile");
		hAccountLogout = new Hyperlink("Logout", "login_Logout");
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(hAccountSettings);
		hp.add(new HTML("&nbsp;&nbsp;"));
		hp.add(hAccountLogout);
		
		pUi.add(hp);
		
	}
	
	public void drawError(String error) {
	  
	  int left = pWidget.getAbsoluteLeft();
    int top = pWidget.getAbsoluteTop() + pWidget.getOffsetHeight();
    int width = pWidget.getOffsetWidth();
    pOptions.setWidth(""+width+"px");
    RootPanel.get().setWidgetPosition(pError, left, top);

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
	  t.schedule(4000);

	  // reset password
	  resetInputSecret();
	}
	
	private void resetInputSecret() {
	  tbConsumerSecretPass.setText("");
	  changePasswordInput();
	}
	
	private void drawInputLabel_key() {
		tbConsumerKey.setText(inputLabel_ConsumerKey);
		tbConsumerKey.addStyleName("core-login-ui-inputlabel");
	}
	
	private void drawInputLabel_secret() {
		tbConsumerSecret.setText(inputLabel_consumerSecret);
		tbConsumerSecret.addStyleName("core-login-ui-inputlabel");
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
		wLoading.hide();
		
		tbConsumerKey.setEnabled(true);
		tbConsumerSecretPass.setEnabled(false);
	}
	
	private void checkInputLabel_key(boolean click) {
		
		if (getConsumerKey().length() == 0 && click == false) {
			tbConsumerKey.setText(inputLabel_ConsumerKey);
			tbConsumerKey.addStyleName("core-login-ui-inputlabel");
			
		} else if (getConsumerKey().equals(inputLabel_ConsumerKey) == true) {
			tbConsumerKey.setText("");
			tbConsumerKey.removeStyleName("core-login-ui-inputlabel");
		}
	}
	
	private void changePasswordInput() {
	  tbConsumerSecretPass.setEnabled(true);
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
			fireChange(EventManager.LOGIN);
		
		} else if (sender == bForgot) {
			drawLoading();
			fireChange(EventManager.FORGOT_PASSWORD);
		
		} else if (sender == tbConsumerKey) {
			checkInputLabel_key(true);
		
		} else if (sender == tbConsumerSecret) {
			changePasswordInput();
		
		} else if (sender == cbRemberMe) {
		  // TODO - save a cookie
		
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

  public void onMouseOver(MouseOverEvent event) {
    
    Widget sender = (Widget) event.getSource();
    if (sender == pWidget) {
      turnOnOptions(true);
    }
    
  }

  public void onMouseOut(MouseOutEvent event) {
    
    Widget sender = (Widget) event.getSource();
    if (sender == pWidget) {
      turnOnOptions(false);
    }
    
  }
	
	
}
