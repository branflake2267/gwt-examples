package org.gonevertical.core.client.ui;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.LoadingWidget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

@Deprecated
public class zold_LoginUiVertical extends Composite {

	private ClientPersistence cp;
	
  private int changeEvent;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  //remember me, create ...
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
	private PushButton bLogin = new PushButton("SignIn");
	
	// forgot password, ask for it
	private PushButton bForgot = new PushButton("Get Password");
	
	private Hyperlink hAccountProfile = new Hyperlink("My Account", "account_Profile");
	private Hyperlink hAccountCreate = new Hyperlink("Create Account", "account_Create");
	private Hyperlink hAccountLogout = new Hyperlink("SignOut", "account_Logout");
	
  // lets not change the url for these
	private HTML hForgotPassword = new HTML("<a>Forgot Password</a>");
	private HTML hAccountLogin = new HTML("<a>SignIn</a>");
	
	
	// true, when logged in, false, when not
	// this will allow to apply logic off this
	private boolean loginStatus = false;
	
	// input box labels
	private String inputLabel_ConsumerKey = "";
	private String inputLabel_consumerSecret = "Password";
	
	private boolean hoverOnOff = false;
  
  /**
   * constructor - init widget
   */
  public zold_LoginUiVertical(ClientPersistence cp) {
    this.cp = cp;
    initWidget(pWidget);
    
  }
  
  public void draw() {
  }
  
	public boolean getRememberMe() {
	  // TODO
	  return false;
  }
	
	public void drawError(String error) {
	  // TODO Auto-generated method stub
	  
  }
	
	public void setLoginStatus(boolean bol) {
	  // TODO Auto-generated method stub
	  
  }
	
	public String getConsumerKey() {
	  // TODO Auto-generated method stub
	  return null;
  }

	public String getConsumerSecret() {
	  // TODO Auto-generated method stub
	  return null;
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
