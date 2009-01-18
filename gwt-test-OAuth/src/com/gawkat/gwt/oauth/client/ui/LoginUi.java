package com.gawkat.gwt.oauth.client.ui;

import com.gawkat.gwt.oauth.client.EventManager;
import com.gawkat.gwt.oauth.client.account.CreateUserAccount;
import com.gawkat.gwt.oauth.client.oauth.OAuthTokenData;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This will be the hook into the types of UIs that one could possibly use
 * this is just the middle man for the user interfaces
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

  // observe changeEvents
	private ChangeListenerCollection changeListeners = null;
	private int changeEvent = 0; 
	
	// main panel
	private FlowPanel pWidget = new FlowPanel();
	
	// possible ui types (widget)
	private LoginUiHorizontal loginUiH = null;
	private LoginUiVertical loginUiV = null;
	
	// which ui type was choosen to display
	private int uiType = 0;
  
	// ui types
  final public static int LOGIN_HORIZONTAL = 1;
  final public static int LOGIN_VERTICAL = 2;
	
  
  // consumer accessToken
  // used for account login
  // used for account creation
  private OAuthTokenData accessToken = null;
  
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
	
	/**
	 * observe login ui that was choose
	 */
	public void setObserver() {
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH.addChangeListener(this);
		} else if (uiType == LOGIN_VERTICAL) {
		  loginUiV.addChangeListener(this);
		}
	}
	
	/**
	 * draw widget
	 */
	public void draw(OAuthTokenData accessToken) {
	  
	  // consumer accessToken
	  this.accessToken = accessToken;
	  
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH.draw();
		} else if (uiType == LOGIN_VERTICAL) {
		  loginUiV.draw();
		}
	}
	
	/**
	 * TODO remove after testing - used for testing
	 * 
	 * @param key
	 * @param password
	 */
	public void autoLogin(String key, String password) {
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH.autoLogin(key, password);
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
		  // TODO
		}	
		return s;
	}
	
	public String getConsumerSecret() {
		String s = null;
		if (uiType == LOGIN_HORIZONTAL) {
			s = loginUiH.getConsumerSecret();
		} else if (uiType == LOGIN_VERTICAL) {
		  // TODO
		}
		return s;
	}

	public void eraseCredentials() {
		// TODO - after login, erase the credentials in the login widget
	  // Leave no traces behind to sniff
	}
	
	private void drawCreateAccount() {
	  CreateUserAccount createUserAccount = new CreateUserAccount();
	  createUserAccount.setAnimationEnabled(true);
	  createUserAccount.setAccessToken(accessToken);
	  createUserAccount.center();
	  
	  // observe for account creation
	  createUserAccount.addChangeListener(new ChangeListener() {
      public void onChange(Widget sender) {
        CreateUserAccount cua = (CreateUserAccount) sender;
        int changeEvent = cua.getChangeEvent();
        if (changeEvent == EventManager.NEW_USER_CREATED) {
          
          // change status to logged in
          // TODO need an option to verify latter
          setLoginStatus(true);
          
          fireChange(EventManager.NEW_USER_CREATED);
        }
      }
    }
	  );
	}
	
	public void onChange(Widget sender) {
	  
		int changeEvent = 0;
		if (uiType == LOGIN_HORIZONTAL) {
		  
			if (sender == loginUiH) {
				changeEvent = loginUiH.getChangeEvent();
			}
			
		} else if (uiType == LOGIN_VERTICAL) {
		  
			if (sender == loginUiV) {
				changeEvent = loginUiV.getChangeEvent();
			}
			
		}
		
		// this is just the middle man to the user interfaces
		if (changeEvent > 0) {
		  fireChange(changeEvent);
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
      // TODO
      Window.alert("fogot");
      
    }
    
  }


	
}
