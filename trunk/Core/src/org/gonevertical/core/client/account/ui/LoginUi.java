package org.gonevertical.core.client.account.ui;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.oauth.Sha1;

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
	private LoginUiInputs loginUi = null;
	
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
	protected void setUi(int uiType) {
		this.uiType = uiType;
		
		loginUi = new LoginUiInputs(cp, uiType);
		pWidget.add(loginUi);

		// observe the accounts/session ui for changes
		setObserver();
	}
	
	protected boolean getRememberMe() {
	  boolean b = loginUi.getRememberMe();
	  return b;
	}
	
	/**
	 * observe login ui that was choose
	 */
	protected void setObserver() {
		loginUi.addChangeHandler(this);
	}
	
	/**
	 * draw widget
	 */
	protected void draw() {
		loginUi.draw();
	}
	
	protected void drawError(String error) {
	  loginUi.drawError(error);
	}
	
	/**
	 * set login status
	 * 
	 * TODO - do something like this, - also send along alias
	 * 
	 * @param bol
	 */
	protected void setLoginStatus(boolean bol) {
		loginUi.setLoginStatus(bol);
	}
	
	protected String getConsumerKey() {
		String s = loginUi.getConsumerKey();
		return s;
	}
	
	protected String getConsumerSecret() {
		String s = loginUi.getConsumerSecret();
		
		// create digest of password, before sending it to the server
		Sha1 sha = new Sha1();
		String hash = sha.b64_sha1(s);
		
    return hash;
	}

  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    int changeEvent = 0;
    
    if (sender == loginUi) {
      changeEvent = loginUi.getChangeEvent();
    }
      
    // this is just the middle man to the user interfaces
    if (changeEvent > 0) {
      fireChange(changeEvent);
    }
    
  }
	
  protected int getChangeEvent() {
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
