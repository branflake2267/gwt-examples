package com.gawkat.core.client.account.ui;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.oauth.Sha1;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Login inputs
 * 
 * @author branflake2267
 *
 */
public class LoginUi extends Composite implements ChangeHandler {

  private ClientPersistence cp = null;
  
  // ui types
  public static final int LOGIN_HORIZONTAL = 1;
  public static final int LOGIN_VERTICAL = 2;
  
  // which ui type was choosen to display
  private int uiType = LOGIN_HORIZONTAL;
  
	private int changeEvent = 0; 
	
	private FlowPanel pWidget = new FlowPanel();
	
	// possible ui types (widget)
	private LoginUiHorizontal loginUiH = null;
	private LoginUiVertical loginUiV = null;
	
  /**
   * constructor - init composite widget
   */
	public LoginUi(ClientPersistence cp) {
	  this.cp = cp;
		
		initWidget(pWidget);
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
			loginUiH = new LoginUiHorizontal(cp);
			pWidget.add(loginUiH);
		} else if (uiType == LOGIN_VERTICAL) {
			loginUiV = new LoginUiVertical(cp);
			pWidget.add(loginUiV);
		}
		
		// observe the accounts/session ui for changes
		setObserver();
	}
	
	public boolean getRememberMe() {
	  boolean b = false;
	  if (uiType == LOGIN_HORIZONTAL) {
	    b = loginUiH.getRememberMe();
    } else if (uiType == LOGIN_VERTICAL) {
      // TODO
    }
	  return b;
	}
	
	/**
	 * observe login ui that was choose
	 */
	public void setObserver() {
		if (uiType == LOGIN_HORIZONTAL) {
			loginUiH.addChangeHandler(this);
		} else if (uiType == LOGIN_VERTICAL) {
		  //loginUiV.addChangeHandler(this);
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
		  loginUiV.draw();
		}
	}
	
	public void drawError(String error) {
	  if (uiType == LOGIN_HORIZONTAL) {
	    loginUiH.drawError(error);
	  } else if (uiType == LOGIN_VERTICAL) {
	    // TODO
	  }

	}
	
	/**
	 * set login status
	 * 
	 * TODO - do something like this, - also send along alias
	 * 
	 * @param bol
	 */
	public void setLoginStatus(boolean bol) {
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
		
		// create digest of password, before sending it to the server
		Sha1 sha = new Sha1();
		String hash = sha.b64_sha1(s);
		
    return hash;
	}

  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
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
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }


	
}
