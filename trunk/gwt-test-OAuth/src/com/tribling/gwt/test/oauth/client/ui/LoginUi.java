package com.tribling.gwt.test.oauth.client.ui;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tribling.gwt.test.oauth.client.account.CreateUserAccount;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;

/**
 * This will be the hook into the types of UIs that one could possibly use
 * 
 * TODO - method for deciding which UI to use, horizontal inputs, vertical inputs, separate forgot system...
 * TODO - this may need to set another loginui in the entry method, complicated at this point, do later
 * TODO - observe the UI
 * TODO - common interface to UIs
 * 
 * @author branflake2267
 *
 */
public class LoginUi extends Composite implements ChangeListener, HistoryListener {

	private ChangeListenerCollection changeListeners;
	private int changeEvent; 
	
	// main panel
	private FlowPanel pWidget = new FlowPanel();
	
	// possible ui types (widget)
	private LoginUiHorizontal loginUiH = null;
	private LoginUiVertical loginUiV = null;
	
	// which ui type was choosen to display
	private int uiType = 0;
	// ui types
  public static int LOGIN_HORIZONTAL = 1;
  public static int LOGIN_VERTICAL = 2;
	
  // observe/listen for these events
  public static int LOGIN = 1;
  public static int FORGOT_PASSWORD = 2;
  
  /**
   * constructor - init composite widget
   */
	public LoginUi() {
		
		initWidget(pWidget);
		
		History.addHistoryListener(this);
		
		// if refreshed, and url event
		if (History.getToken().equals("account_Create")) {
		  drawCreateAccount();
		}
	}
	
	/**
	 * what type of ui are we going to use?
	 * [LOGIN_HORIZONTAL, LOGIN_VERTICAL]
	 * 
	 * @param uiType
	 */
	public void setUi(int uiType) {
		this.uiType = uiType;
		
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH = new LoginUiHorizontal();
			pWidget.add(loginUiH);
		} else if (uiType == LOGIN_VERTICAL) {
			loginUiV = new LoginUiVertical();
			pWidget.add(loginUiV);
		}
		
		// observe the accounts/session ui for changes
		setObserver();
	}
	
	public void setObserver() {
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH.addChangeListener(this);
		} else if (uiType == LOGIN_VERTICAL) {
		  // TODO
		}
		
	}
	
	/**
	 * draw widget
	 */
	public void draw() {
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH.draw();
		} else if (uiType == LOGIN_VERTICAL) {
		  // TODO
		}
	}
	
	/**
	 * TODO remove after testing - used for testing
	 * 
	 * @param email
	 * @param password
	 */
	public void autoLogin(String email, String password) {
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH.autoLogin(email, password);
		} else if (uiType == LOGIN_VERTICAL) {
		  // TODO
		}
	}
	
	public void setLoginStatus(boolean bol) {
		// TODO - do something like this, - also send along alias
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH.setLoginStatus(bol);
		} else if (uiType == LOGIN_VERTICAL) {
		  // TODO
		}
	}
	
	public String getConsumerKey() {
		
		String s = null;
		if (uiType == LOGIN_HORIZONTAL) {
			s = loginUiH.getConsumerKey();
		} else if (uiType == LOGIN_VERTICAL) {
		}
		
		return s;
	}
	
	public String getConsumerSecret() {
		
		String s = null;
		if (uiType == LOGIN_HORIZONTAL) {
			s = loginUiH.getConsumerSecret();
		} else if (uiType == LOGIN_VERTICAL) {
		}
		
		return s;
	}

	public void eraseCredentials() {
		// TODO - after login, erase the credentials in the login widget
	}
	
	private void drawCreateAccount() {
	  CreateUserAccount createUserAccount = new CreateUserAccount();
	  createUserAccount.setAnimationEnabled(true);
	  createUserAccount.center();
	}
	
	public void onChange(Widget sender) {
		
		if (uiType == LOGIN_HORIZONTAL) {
			if (sender == loginUiH) {
				fireChange(loginUiH.getChangeEvent());
			}
		} else if (uiType == LOGIN_VERTICAL) {
			if (sender == loginUiV) {
				// TODO 
			}
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

 
  public void onHistoryChanged(String historyToken) {
   
    if (historyToken.equals("account_Create")) {
      drawCreateAccount();
      
    } else if (historyToken.equals("account_ForgotPassword")) {
      Window.alert("fogot");
      
    }
    
  }


	
}
