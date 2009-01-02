package com.tribling.gwt.test.oauth.client.account;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tribling.gwt.test.oauth.client.oauth.OAuthTokenData;
import com.tribling.gwt.test.oauth.client.oauth.Sha1;
import com.tribling.gwt.test.oauth.client.rpc.Rpc;
import com.tribling.gwt.test.oauth.client.rpc.RpcServiceAsync;

public class CreateUserAccount extends DialogBox implements ClickListener, KeyboardListener {

  // rpc system
  private RpcServiceAsync callRpcService;
  
  // main widget div
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pNotification = new VerticalPanel();
  
  // inputs
  // username (email)
  private TextBox tbU1 = new TextBox();
  private TextBox tbU2 = new TextBox();
  
  // password
  private TextBox tbP1 = new TextBox();
  private TextBox tbP2 = new TextBox();
  
  private PushButton bCreateAccount = new PushButton("Create Account");
  private PushButton bClose = new PushButton("Close");
  
  // terms of use container
  private TextArea taTerms = new TextArea();
  
  private CheckBox cbAccept = new CheckBox("Accept terms of use and privacy agreement?");
  
  // consumer access Token
  // tells me that the web application has access to the system
  // will be used to send with UserData
  private OAuthTokenData accessToken = null;
   
  /**
   * constructor - init widget
   */
  public CreateUserAccount() {
    
    setAnimationEnabled(true);
    
    setTitle("Create New Account");
    
    setWidget(pWidget);
    
    draw();
    
    bCreateAccount.addClickListener(this);
    bClose.addClickListener(this);
    
    // init rpc
    callRpcService = Rpc.initRpc();
    
    // Style
    pNotification.setWidth("100%");
    pNotification.setStyleName("CreateUserAccount-Notification");
  }
  
  /**
   * draw the inputs and items that make up the create user widget
   */
  public void draw() {
    
    tbU1.setWidth("300px");
    tbU2.setWidth("300px");
    tbP1.setWidth("300px");
    tbP2.setWidth("300px");
    taTerms.setWidth("100%");
    
    taTerms.setEnabled(false);
    
    String t = "Create New Account";
    
    setText(t);
    HTML title = new HTML(t);

    Label lu1 = new Label("Username");
    Label lu2 = new Label("Verify Username");
    
    Label lp1 = new Label("Password");
    Label lp2 = new Label("Verify Password");
    
    HorizontalPanel hpBottom = new HorizontalPanel();
    hpBottom.add(bClose);
    hpBottom.add(new HTML("&nbsp;&nbsp;"));
    hpBottom.add(bCreateAccount);
    
    // size of input table
    int r = 5;
    int c = 2;
    
    Grid grid = new Grid(r, c);
    
    // username
    grid.setWidget(0, 0, lu1);
    grid.setWidget(0, 1, tbU1);
    grid.setWidget(1, 0, lu2);
    grid.setWidget(1, 1, tbU2);
    
    // spacer
    grid.setWidget(2, 0, new HTML("&nbsp;"));
    grid.setWidget(2, 1, new HTML("&nbsp;"));
    
    // password
    grid.setWidget(3, 0, lp1);
    grid.setWidget(3, 1, tbP1);
    grid.setWidget(4, 0, lp2);
    grid.setWidget(4, 1, tbP2);
    
    pWidget.add(title);
    pWidget.add(grid);
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(new HTML("Terms Of Use"));
    pWidget.add(taTerms); 
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(cbAccept);
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(hpBottom);
  }
  
  public void setAccessToken(OAuthTokenData accessToken) {
    this.accessToken = accessToken;
  }
  
  private void notify(UserData userData) {
    
    String err = "";
    switch (userData.error) {
    case UserData.SYSTEM_ERROR:
      err = "System error occurred. Contact the administrator.";
      break;
    case UserData.USER_EXISTS:
      err = "This user name exists already. Please choose another.";
      break;
    case UserData.USERNAME_DONTMATCH:
      err = "Usernames don't match.";
      break;
    case UserData.PASSWORD_DONTMATCH:
      err = "Passwords don't match.";
      break;
    }
    
    if (err.length() > 0) {
      drawNotification(err);
    }
  }
  
  /**
   * draw a notification
   * 
   * @param s
   */
  public void drawNotification(String s) {
    pNotification.clear();
    pNotification.setVisible(true);
    
    HTML html = new HTML(s);
    html.addStyleName("Notification");
    html.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
    pNotification.add(html);
    
    Timer t = new Timer() {
      public void run() {
        pNotification.setVisible(false);
      }
    };
    t.schedule(5000);
  }
  
  private boolean doesKeysMatch() {
  
    String u1 = tbU1.getText().trim();
    String u2 = tbU2.getText().trim();
    
    // TODO - no spaces in middle?
    
    boolean pass = false;
    if (u1.equals(u2)) {
      pass = true;
    }
    
    return pass;
    
  }
  
  private boolean doesPasswordMatch() {
    
    String p1 = tbP1.getText().trim();
    String p2 = tbP2.getText().trim();
    
    // TODO - no spaces in middle?
    
    boolean pass = false;
    if (p1.equals(p2)) {
      pass = true;
    }
    
    return pass;
  }
  
  /**
   * check if the user exist in the system already?
   */
  private void doesConsumerExistAlready() {
    
    UserData userData = new UserData();
    userData.accessToken = accessToken;
    userData.consumerKey = getKeyHash();
    userData.consumerSecret = getPasswordHash();
    userData.sign();
    
    isUserExistRpc(userData);
  }
  
  private void processKeyExist(UserData userData) {
    
    if (userData.error > 0) {
      notify(userData);
      return;
    }
    
    // create account if there is no duplication
    processCreate();
  }
  
  private String getKeyHash() {
    String key = tbP1.getText().trim();
    Sha1 sha = new Sha1();
    String hash = sha.b64_sha1(key);
    return hash;
  }
  
  private String getKey() {
    String key = tbP1.getText().trim();
    return key;
  }
  
  private String getPasswordHash() {
    String password = tbP1.getText().trim();
    Sha1 sha = new Sha1();
    String hash = sha.b64_sha1(password);
    return hash;
  }
  
  /**
   * process the account creation after all the things are met
   */
  private void processCreate() {
    
    // do keys match
    boolean keysMatch = doesKeysMatch();
    
    // do passwords match
    boolean passwordsMatch = doesPasswordMatch();
    
    if (keysMatch == false && passwordsMatch == false) {
      return;
    }

    // prepare for transport
    UserData userData = new UserData();
    userData.accessToken = accessToken;
    userData.consumerKey = getKey(); // could be - getKeyHash();;
    userData.consumerSecret = getPasswordHash();
    userData.sign();
    
    createAccountRpc(userData);
  }
  
  private void processAccountCreation(UserData userData) {
    
    // close this
    
    // change to logged in, set the consumerToken
    
  }
  
  public void onClick(Widget sender) {
    
    if (sender == bCreateAccount) {
      doesConsumerExistAlready();
      
    } else if (sender == bClose) {
      this.hide();
      // lets reset the historyToken, in-case user decides to click on create account agian
      History.newItem("account_Login");
    }
    
  }
  
  public void onKeyDown(Widget sender, char keyCode, int modifiers) {
  }

  public void onKeyPress(Widget sender, char keyCode, int modifiers) {
  }

  public void onKeyUp(Widget sender, char keyCode, int modifiers) {
  }
  
  /**
   * check to see if the user already exists in the database
   * 
   * @param userData
   */
  private void isUserExistRpc(UserData userData) {
    
    AsyncCallback<UserData> callback = new AsyncCallback<UserData>() {
      public void onFailure(Throwable ex) {
        // TODO 
        RootPanel.get().add(new HTML(ex.toString()));
      }
      public void onSuccess(UserData userData) {
        processKeyExist(userData);
        // TODO hide loading
      }
    };
    callRpcService.isUserNameExist(userData, callback);
  }

  /**
   * create user account
   * 
   * @param userData
   */
  private void createAccountRpc(UserData userData) {
    
    AsyncCallback<UserData> callback = new AsyncCallback<UserData>() {
      public void onFailure(Throwable ex) {
        // TODO 
        RootPanel.get().add(new HTML(ex.toString()));
      }
      public void onSuccess(UserData userData) {
        processAccountCreation(userData);
        // TODO hide loading
      }
    };
    callRpcService.createUser(userData, callback);
  }
  

  
  
}
