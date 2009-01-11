package com.tribling.gwt.test.oauth.client.account;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
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

public class CreateUserAccount extends DialogBox implements ClickListener, KeyboardListener, FocusListener, ChangeListener {

  // rpc system
  private RpcServiceAsync callRpcService = null;
  
  // main widget div
  private VerticalPanel pWidget = new VerticalPanel();
  
  // general notification of error after rpc, maybe combine into key/secret error too
  private VerticalPanel pNotification = new VerticalPanel();
  
  // when notification is needed for key error
  private FlowPanel pKeyError = new FlowPanel();
 
  // key container
  private VerticalPanel pKey = new VerticalPanel();
  
  // when notification is needed for secret error
  private FlowPanel pSecretError = new FlowPanel();
  
  // secret container
  private VerticalPanel pSecret = new VerticalPanel();
  
  // key character count
  private FlowPanel pKeyCount1 = new FlowPanel();
  private FlowPanel pKeyCount2 = new FlowPanel();
  
  // secret character count
  private FlowPanel pSecretCount1 = new FlowPanel();
  private FlowPanel pSecretCount2 = new FlowPanel();
  
  // inputs
  // Key (username)
  private TextBox tbK1 = new TextBox();
  private TextBox tbK2 = new TextBox();
  
  // Secret (password)
  private TextBox tbS1 = new TextBox();
  private TextBox tbS2 = new TextBox();
  
  // buttons
  private PushButton bCreateAccount = new PushButton("Create Account");
  private PushButton bClose = new PushButton("Close");
  
  
  // accept panel
  private VerticalPanel pAccept = new VerticalPanel();
  
  // accept error notification
  private FlowPanel pAcceptError = new FlowPanel();
  
  // accept privacy agreement and terms of use
  private CheckBox cbAccept = new CheckBox("Accept terms of use and privacy agreement?");
  
  // terms of use container
  private TextArea taTerms = new TextArea();
  
  
  // consumer access Token
  // tells me that the web application has access to the system
  // will be used to send with UserData
  private OAuthTokenData accessToken = null;
   
  // minumum lengths for consumerKey and Secret
  final private int consumerKey_Len = 6;
  final private int consumerSecret_Len = 6;
  
  // after all checks are processed, then we can create a user
  private boolean canCreateUser = false;
  
  /**
   * constructor - init widget
   */
  public CreateUserAccount() {
    
    // make it cool
    setAnimationEnabled(true);
    
    // set the title of the top of the window
    setText("Create New Account");
    
    // init widget
    setWidget(pWidget);
    
    // draw widget
    draw();
    
    // observe
    bCreateAccount.addClickListener(this);
    bCreateAccount.addFocusListener(this);
    bClose.addClickListener(this);
    tbK1.addChangeListener(this);
    tbK2.addChangeListener(this);
    tbS1.addChangeListener(this);
    tbS2.addChangeListener(this);
    tbK1.addKeyboardListener(this);
    tbK2.addKeyboardListener(this);
    tbS1.addKeyboardListener(this);
    tbS2.addKeyboardListener(this);
      
    // init rpc
    callRpcService = Rpc.initRpc();
    
    // style
    pWidget.setStyleName("CreateUserAccount");
    pNotification.setWidth("100%");
    pNotification.setStyleName("CreateUserAccount-Notification");
    pSecretError.addStyleName("CreateUserAccount-Error");
    pKeyError.addStyleName("CreateUserAccount-Error");
    pAcceptError.addStyleName("CreateUserAccount-Error");
  }
  
  /**
   * draw the inputs and items that make up the create user widget
   */
  public void draw() {
    
    tbK1.setWidth("300px");
    tbK2.setWidth("300px");
    tbS1.setWidth("300px");
    tbS2.setWidth("300px");
    taTerms.setWidth("100%");
    
    taTerms.setEnabled(false);
    
    Label lk1 = new Label("Username");
    Label lk2 = new Label("Verify Username");
    
    Label ls1 = new Label("Password");
    Label ls2 = new Label("Verify Password");
    
    pKeyCount1.add(new HTML("0"));
    pKeyCount2.add(new HTML("0"));
    pSecretCount1.add(new HTML("0"));
    pSecretCount2.add(new HTML("0"));
    
    // control buttons
    HorizontalPanel hpBottom = new HorizontalPanel();
    hpBottom.add(bClose);
    hpBottom.add(new HTML("&nbsp;&nbsp;"));
    hpBottom.add(bCreateAccount);
    
    // key 1
    HorizontalPanel hpK1 = new HorizontalPanel();
    hpK1.setSpacing(4);
    hpK1.add(lk1);
    hpK1.add(tbK1);
    hpK1.add(pKeyCount1);
    
    // key 2
    HorizontalPanel hpK2 = new HorizontalPanel();
    hpK2.setSpacing(4);
    hpK2.add(lk2);
    hpK2.add(tbK2);
    hpK2.add(pKeyCount2);
    
    // secret 1
    HorizontalPanel hpS1 = new HorizontalPanel();
    hpS1.setSpacing(4);
    hpS1.add(ls1);
    hpS1.add(tbS1);
    hpS1.add(pSecretCount1);
    
    // secret 2
    HorizontalPanel hpS2 = new HorizontalPanel();
    hpS2.setSpacing(4);
    hpS2.add(ls2);
    hpS2.add(tbS2);
    hpS2.add(pSecretCount2);
    
    // key container
    pKey.add(pKeyError);
    pKey.add(hpK1);
    pKey.add(hpK2);
    
    // secret container
    pSecret.add(pSecretError);
    pSecret.add(hpS1);
    pSecret.add(hpS2);
    
    pAccept.add(pAcceptError);
    pAccept.add(cbAccept);
    
    pWidget.add(pKey);
    pWidget.add(pSecret);
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(new HTML("Terms of Use"));
    pWidget.add(taTerms); 
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(pAccept);
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(hpBottom);
    
    pKey.setStyleName("");
    pSecret.setStyleName("");
    
    // fields
    lk1.setStyleName("CreateUserAccount-Field");
    lk2.setStyleName("CreateUserAccount-Field");
    ls1.setStyleName("CreateUserAccount-Field");
    ls2.setStyleName("CreateUserAccount-Field");
    
    hpK1.setCellVerticalAlignment(lk1, VerticalPanel.ALIGN_MIDDLE);
    hpK2.setCellVerticalAlignment(lk2, VerticalPanel.ALIGN_MIDDLE);
    hpK1.setCellVerticalAlignment(pKeyCount1, VerticalPanel.ALIGN_MIDDLE);
    hpK2.setCellVerticalAlignment(pKeyCount2, VerticalPanel.ALIGN_MIDDLE);
    
    hpS1.setCellVerticalAlignment(ls1, VerticalPanel.ALIGN_MIDDLE);
    hpS2.setCellVerticalAlignment(ls2, VerticalPanel.ALIGN_MIDDLE);
    hpS1.setCellVerticalAlignment(pSecretCount1, VerticalPanel.ALIGN_MIDDLE);
    hpS2.setCellVerticalAlignment(pSecretCount2, VerticalPanel.ALIGN_MIDDLE);
    
    pKeyCount1.setStyleName("CreateUserAccount-CharCountError");
    pKeyCount2.setStyleName("CreateUserAccount-CharCountError");
    pSecretCount1.setStyleName("CreateUserAccount-CharCountError");
    pSecretCount2.setStyleName("CreateUserAccount-CharCountError");
  }
  
  public void setAccessToken(OAuthTokenData accessToken) {
    this.accessToken = accessToken;
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
  
    String u1 = tbK1.getText().trim();
    String u2 = tbK2.getText().trim();
    
    // TODO - no spaces in middle?
    
    boolean pass = false;
    if (u1.equals(u2)) {
      pass = true;
    }
    
    return pass;
    
  }
  
  private boolean doesPasswordMatch() {
    
    String p1 = tbS1.getText().trim();
    String p2 = tbS2.getText().trim();
    
    // TODO - no spaces in middle?
    
    boolean pass = false;
    if (p1.equals(p2)) {
      pass = true;
    } 
    
    return pass;
  }
  
  private void createUserStart() {
    
    // verify the inputs have data, and has what is needed.
    checkForErrors();
    
    if (canCreateUser == false) {
      System.out.println("createUserStart(): checkerrors failed");
      return;
    }

    // check to see if the user is in the database?
    doesConsumerExistAlready();
  }

  /**
   * check if the user exist in the system already?
   */
  private void doesConsumerExistAlready() {
    
    UserData userData = new UserData();
    userData.accessToken = accessToken;
    userData.consumerKey = getKey(); // could be -> getKeyHash();
    userData.consumerSecret = getPasswordHash();
    userData.sign();
    
    isUserExistRpc(userData);
  }
  
  private void drawKeyNotify(boolean bol, int error) {
    pKeyError.clear();
    if (bol == true) {
      pKey.setStyleName("CreateUserAccount-ErrorInput");
      if (error > 0) {
        pKeyError.add(new HTML(UserData.getError(error)));
      }
    } else {
      pKey.removeStyleName("CreateUserAccount-ErrorInput");
    }
  }
  
  private void drawSecretNotify(boolean bol, int error) {
    pSecretError.clear();
    if (bol == true) {
      pSecret.setStyleName("CreateUserAccount-ErrorInput");
      if (error > 0) {
        pSecretError.add(new HTML(UserData.getError(error)));
      }
    } else {
      pSecret.removeStyleName("CreateUserAccount-ErrorInput");
      pSecretError.clear();
    }
  }
  
  private void drawAcceptNotify(boolean bol, int error) {
    pAcceptError.clear();
    if (bol == true) {
      pAccept.setStyleName("CreateUserAccount-ErrorInput");
      if (error > 0) {
        pAcceptError.add(new HTML(UserData.getError(error)));
      }
    } else {
      pAccept.removeStyleName("CreateUserAccount-ErrorInput");
      pAcceptError.clear();
    }
  }
  
  private void processKeyExist(UserData userData) {
    
    if (userData.error > 0) {
      drawNotification(userData.getNotification());
      return;
    }
    
    // create account if there is no duplication
    createUserAccount();
  }
  
  /**
   * could hash the key possibly for being even more anonymous.
   *   this would possibly allow users to have multiple accounts by accident, if they forget there login
   *   Case sensitivity becomes and issue possibly.
   *   whats the possibility of a collision with just querying a key?
   *      Would have to compare both key and secret every time, which would be better I think
   *   could not search for username, b/c it would be a hash, so could not tell if it existed or not unless an exact match was made
   *   If compared both, the combinations are unlimited in respect to trying to find a lost account
   *  TODO  Could make this a user option, this seems like it could be the best!!!!
   * @return
   */
  private String getKeyHash() {
    String key = tbS1.getText().trim();
    Sha1 sha = new Sha1();
    String hash = sha.b64_sha1(key);
    return hash;
  }
  
  private String getKey() {
    String key = tbS1.getText().trim();
    return key;
  }
  
  private String getPasswordHash() {
    String password = tbS1.getText().trim();
    Sha1 sha = new Sha1();
    String hash = sha.b64_sha1(password);
    return hash;
  }
  
  private void checkForErrors() {
    
    System.out.println("check for errors"); 
    
    drawKeyNotify(false, 0);
    drawSecretNotify(false, 0);
    
    // are things blank?
    String k = tbK1.getText().trim();
    String s = tbS1.getText().trim();
    
    if (k.length() < consumerKey_Len && s.length() < consumerSecret_Len) {
      drawKeyNotify(true, UserData.KEYS_SHORT);
      drawSecretNotify(true, UserData.SECRETS_SHORT);

      return;
    }
    if (k.length() < consumerKey_Len) {
      drawKeyNotify(true, UserData.KEYS_SHORT);

      return;
    }
    if (s.length() < consumerSecret_Len) {
      drawSecretNotify(true, UserData.SECRETS_SHORT);

      return;
    }
    
    
    // Matching
    
    // do keys match
    boolean keysMatch = doesKeysMatch();
    
    // do passwords match
    boolean secretsMatch = doesPasswordMatch();
    
    int error = 0;
    if (keysMatch == false && secretsMatch == false) {
      error = UserData.BOTH_DONTMATCH;
      drawKeyNotify(true, error);
      drawSecretNotify(true, error);
    }
    
    // keys match?
    if (keysMatch == false) {
      error = UserData.KEYS_DONTMATCH;
      drawKeyNotify(true, error);
    } 
    
    // secrets match?
    if (secretsMatch == false) {
      error = UserData.SECRETS_DONTMATCH;
      drawSecretNotify(true, error);
    }
    
    // accepted checked?
    boolean accepted = cbAccept.isChecked();
    if (accepted == false) {
      error = UserData.ACCEPT_TERMS;
      drawAcceptNotify(true, error);
      return;
    }
    
    // there is an error to deal with
    if (error > 0) {
      return;
    }
    
    // reset the errors
    drawKeyNotify(false, 0);
    drawSecretNotify(false, 0);
    drawAcceptNotify(false, 0);
    
    // we can check to see if user exists now.
    canCreateUser = true;
  }
  
  /**
   * create user account after it passes all the checks
   */
  private void createUserAccount() {
    
    if (canCreateUser == false) {
      return;
    }

    // prepare for transport
    UserData userData = new UserData();
    userData.accessToken = accessToken;
    userData.consumerKey = getKey(); // could be - getKeyHash();;
    userData.consumerSecret = getPasswordHash();
    userData.acceptTerms = cbAccept.isChecked();
    userData.sign();
    
    createAccountRpc(userData);
  }
  
  private void processAccountCreation(UserData userData) {
    
    // close this
    
    // change to logged in, set the consumerToken
    
  }
  
  private void countCharacters(int input, TextBox tb) {
    
    int ilen = tb.getText().length();
    String len = Integer.toString(ilen);
        
    switch (input) {
    case 1: // key 1
      pKeyCount1.clear();
      pKeyCount1.add(new HTML(len));
      if (ilen > consumerKey_Len) {
        pKeyCount1.removeStyleName("CreateUserAccount-CharCountError");
        pKeyCount1.setStyleName("CreateUserAccount-CharCountPass");
      } else {
        pKeyCount1.setStyleName("CreateUserAccount-CharCountError");
        pKeyCount1.removeStyleName("CreateUserAccount-CharCountPass");
      }
      break;
    case 2: // key 2;
      pKeyCount2.clear();
      pKeyCount2.add(new HTML(len));
      if (ilen > consumerKey_Len) {
        pKeyCount2.removeStyleName("CreateUserAccount-CharCountError");
        pKeyCount2.setStyleName("CreateUserAccount-CharCountPass");
      } else {
        pKeyCount2.setStyleName("CreateUserAccount-CharCountError");
        pKeyCount2.removeStyleName("CreateUserAccount-CharCountPass");
      }
      break;
    case 3: // secret 1
      pSecretCount1.clear();
      pSecretCount1.add(new HTML(len));
      if (ilen > consumerSecret_Len) {
        pSecretCount1.removeStyleName("CreateUserAccount-CharCountError");
        pSecretCount1.setStyleName("CreateUserAccount-CharCountPass");
      } else {
        pSecretCount1.setStyleName("CreateUserAccount-CharCountError");
        pSecretCount1.removeStyleName("CreateUserAccount-CharCountPass");
      }
      break;
    case 4: // secret 2
      pSecretCount2.clear();
      pSecretCount2.add(new HTML(len));
      if (ilen > consumerSecret_Len) {
        pSecretCount2.removeStyleName("CreateUserAccount-CharCountError");
        pSecretCount2.setStyleName("CreateUserAccount-CharCountPass");
      } else {
        pSecretCount2.setStyleName("CreateUserAccount-CharCountError");
        pSecretCount2.removeStyleName("CreateUserAccount-CharCountPass");
      }
      break;
    }
    
  }
  
  public void onClick(Widget sender) {
    
    if (sender == bCreateAccount) {
      createUserStart();
      
    } else if (sender == bClose) {
      this.hide();
      // lets reset the historyToken, in-case user decides to click on create account agian
      History.newItem("account_Login");
    }
    
  }
  
  public void onChange(Widget sender) {
    
    if (sender == tbK1) {
      countCharacters(1, tbK1);
    } else if (sender == tbK2) {
      countCharacters(2, tbK2);
    } else if (sender == tbS1) {
      countCharacters(3, tbS1);
    } else if (sender == tbS2) {
      countCharacters(4, tbS2);
    }
    
  }
  
  public void onKeyDown(Widget sender, char keyCode, int modifiers) {
  }

  public void onKeyPress(Widget sender, char keyCode, int modifiers) {
  }

  public void onKeyUp(Widget sender, char keyCode, int modifiers) {
    
    if (sender == tbK1) {
      countCharacters(1, tbK1);
    } else if (sender == tbK2) {
      countCharacters(2, tbK2);
    } else if (sender == tbS1) {
      countCharacters(3, tbS1);
    } else if (sender == tbS2) {
      countCharacters(4, tbS2);
    }
    
  }
  
  public void onFocus(Widget sender) {
    if (sender == bCreateAccount) {
      // nothing to do here
    }
  }

  public void onLostFocus(Widget sender) {
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
