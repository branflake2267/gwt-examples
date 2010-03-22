package com.gawkat.core.client.account;


import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.oauth.Sha1;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
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

public class CreateUserAccount extends Composite implements KeyboardListener, FocusListener, ClickHandler, ChangeHandler {

  private ClientPersistence cp = null;
  
  // rpc system
  private RpcCoreServiceAsync rpc = null;
  
  private int changeEvent = 0;
  
  // main widget div
  private VerticalPanel pWidget = new VerticalPanel();
  
  // general notification of error after rpc, maybe combine into key/secret error too
  private VerticalPanel pNotification = new VerticalPanel();
  
  // when notification is needed for key error
  private FlowPanel pKeyError = new FlowPanel();
 
  // key container
  private VerticalPanel pKey = new VerticalPanel();

  // Key (username)
  private TextBox tbK1 = new TextBox();
  private TextBox tbK2 = new TextBox();
  
  // key character count
  private FlowPanel pKeyCount1 = new FlowPanel();
  private FlowPanel pKeyCount2 = new FlowPanel();
  
  private PasswordWidget wSecret = new PasswordWidget();

  // buttons
  private PushButton bCreateAccount = new PushButton("Create Account");


  // accept panel
  private VerticalPanel pAccept = new VerticalPanel();
  
  // accept error notification
  private FlowPanel pAcceptError = new FlowPanel();
  
  // accept privacy agreement and terms of use
  private CheckBox cbAccept = new CheckBox("Accept terms of use and privacy agreement?");
  
  // terms of use container
  private TextArea taTerms = new TextArea();
  
  // minumum lengths for consumerKey and Secret
  final private int consumerKey_Len = 6;
  final private int consumerSecret_Len = 6;
  
  // after all checks are processed, then we can create a user
  private boolean canCreateUser = false;
  
  
  /**
   * constructor - init widget
   */
  public CreateUserAccount(ClientPersistence cp) {
    this.cp = cp;
    
    // init widget
    initWidget(pWidget);
    
    // draw widget
    draw();
    
    // observe
    bCreateAccount.addClickHandler(this);
    bCreateAccount.addFocusListener(this);

    tbK1.addChangeHandler(this);
    tbK2.addChangeHandler(this);

    tbK1.addKeyboardListener(this);
    tbK2.addKeyboardListener(this);

      
    // init rpc
    rpc = RpcCore.initRpc();
    
    // style
    pWidget.setStyleName("CreateUserAccount");
    pNotification.setWidth("100%");
    pNotification.setStyleName("core-CreateUserAccount-Notification");
    
    pKeyError.addStyleName("core-CreateUserAccount-Error");
    pAcceptError.addStyleName("core-CreateUserAccount-Error");
  }
  
  /**
   * draw the inputs and items that make up the create user widget
   */
  public void draw() {
    
    tbK1.setWidth("300px");
    tbK2.setWidth("300px");

    taTerms.setWidth("100%");
    
    taTerms.setEnabled(false);
    
    String un = cp.getInputLabel_ConsumerKey();
    Label lk1 = new Label(un);
    Label lk2 = new Label("Verify " + un);
        
    pKeyCount1.add(new HTML("0"));
    pKeyCount2.add(new HTML("0"));

    // control buttons
    HorizontalPanel hpBottom = new HorizontalPanel();
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
    
    // key container
    pKey.add(pKeyError);
    pKey.add(hpK1);
    pKey.add(hpK2);
    
    pAccept.add(pAcceptError);
    pAccept.add(cbAccept);
    
    pWidget.add(pKey);
    pWidget.add(wSecret);
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(new HTML("Terms of Use"));
    pWidget.add(taTerms); 
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(pAccept);
    pWidget.add(new HTML("&nbsp;"));
    pWidget.add(hpBottom);
    
    // fields
    lk1.setStyleName("core-CreateUserAccount-Field");
    lk2.setStyleName("core-CreateUserAccount-Field");

    hpK1.setCellVerticalAlignment(lk1, VerticalPanel.ALIGN_MIDDLE);
    hpK2.setCellVerticalAlignment(lk2, VerticalPanel.ALIGN_MIDDLE);
    hpK1.setCellVerticalAlignment(pKeyCount1, VerticalPanel.ALIGN_MIDDLE);
    hpK2.setCellVerticalAlignment(pKeyCount2, VerticalPanel.ALIGN_MIDDLE);
    
    pKeyCount1.setStyleName("core-CreateUserAccount-CharCountError");
    pKeyCount2.setStyleName("core-CreateUserAccount-CharCountError");

  }
  
  /**
   * draw a notification
   * 
   * @param s
   */
  private void drawNotification(String s) {
    pNotification.clear();
    pNotification.setVisible(true);
    
    HTML html = new HTML(s);
    html.addStyleName("core-Notification");
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
    userData.accessToken = cp.getAccessToken();
    userData.consumerKey = getKey(); // could be -> getKeyHash();
    userData.consumerSecret = wSecret.getPasswordHash();
    userData.sign();
    
    doesUserExistRpc(userData);
  }
  
  private void drawKeyNotify(boolean bol, int error) {
    pKeyError.clear();
    if (bol == true) {
      pKey.setStyleName("core-CreateUserAccount-ErrorInput");
      if (error > 0) {
        pKeyError.add(new HTML(UserData.getError(error)));
      }
    } else {
      pKey.removeStyleName("core-CreateUserAccount-ErrorInput");
    }
  }
  
  private void drawAcceptNotify(boolean bol, int error) {
    pAcceptError.clear();
    if (bol == true) {
      pAccept.setStyleName("core-CreateUserAccount-ErrorInput");
      if (error > 0) {
        pAcceptError.add(new HTML(UserData.getError(error)));
      }
    } else {
      pAccept.removeStyleName("core-CreateUserAccount-ErrorInput");
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
    String key = wSecret.getS1();
    Sha1 sha = new Sha1();
    String hash = sha.b64_sha1(key);
    return hash;
  }
  
  private String getKey() {
    String key = tbK1.getText().trim();
    return key;
  }
  
 
  
  private void checkForErrors() {
    
    System.out.println("check for errors"); 
    
    drawKeyNotify(false, 0);
    wSecret.drawSecretNotify(false, 0);
    
    // are things blank?
    String k = tbK1.getText().trim();
    String s = wSecret.getS1();
    
    if (k.length() < consumerKey_Len && s.length() < consumerSecret_Len) {
      drawKeyNotify(true, UserData.KEYS_SHORT);
      wSecret.drawSecretNotify(true, UserData.SECRETS_SHORT);

      return;
    }
    if (k.length() < consumerKey_Len) {
      drawKeyNotify(true, UserData.KEYS_SHORT);

      return;
    }
    if (s.length() < consumerSecret_Len) {
      wSecret.drawSecretNotify(true, UserData.SECRETS_SHORT);

      return;
    }
    
    
    // Matching
    
    // do keys match
    boolean keysMatch = doesKeysMatch();
    
    // do passwords match
    boolean secretsMatch = wSecret.doesPasswordMatch();
    
    int error = 0;
    if (keysMatch == false && secretsMatch == false) {
      error = UserData.BOTH_DONTMATCH;
      drawKeyNotify(true, error);
      wSecret.drawSecretNotify(true, error);
    }
    
    // keys match?
    if (keysMatch == false) {
      error = UserData.KEYS_DONTMATCH;
      drawKeyNotify(true, error);
    } 
    
    // secrets match?
    if (secretsMatch == false) {
      error = UserData.SECRETS_DONTMATCH;
      wSecret.drawSecretNotify(true, error);
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
    wSecret.drawSecretNotify(false, 0);
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
    userData.accessToken = cp.getAccessToken();
    userData.consumerKey = getKey(); // could be - getKeyHash();;
    userData.consumerSecret = wSecret.getPasswordHash();
    userData.acceptTerms = cbAccept.getValue();
    userData.sign();
    
    createAccountRpc(userData);
  }
  
  private void processAccountCreation(UserData userData) {
    
    if (userData == null) {
      return;
    }
    
    // are there errors???
    if (userData.error > 0) {
      drawNotification(UserData.getError(userData.error));
      return;
    }
    
    cp.setAccessToken(userData.accessToken);

    History.newItem("account_Profile");
    
    // Notify change logged in - needs to notify Login Widget
    cp.fireChange(EventManager.NEW_USER_CREATED);
  }
  
  private void countCharacters(int input, TextBox tb) {
    
    int ilen = tb.getText().length();
    String len = Integer.toString(ilen);
        
    switch (input) {
    case 1: // key 1
      pKeyCount1.clear();
      pKeyCount1.add(new HTML(len));
      if (ilen > consumerKey_Len) {
        pKeyCount1.removeStyleName("core-CreateUserAccount-CharCountError");
        pKeyCount1.setStyleName("core-CreateUserAccount-CharCountPass");
      } else {
        pKeyCount1.setStyleName("core-CreateUserAccount-CharCountError");
        pKeyCount1.removeStyleName("core-CreateUserAccount-CharCountPass");
      }
      break;
    case 2: // key 2;
      pKeyCount2.clear();
      pKeyCount2.add(new HTML(len));
      if (ilen > consumerKey_Len) {
        pKeyCount2.removeStyleName("core-CreateUserAccount-CharCountError");
        pKeyCount2.setStyleName("core-CreateUserAccount-CharCountPass");
      } else {
        pKeyCount2.setStyleName("core-CreateUserAccount-CharCountError");
        pKeyCount2.removeStyleName("core-CreateUserAccount-CharCountPass");
      }
      break;
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
  
  public void onClick(ClickEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == bCreateAccount) {
      createUserStart();
      
    } 
  }
  
  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == tbK1) {
      countCharacters(1, tbK1);
    } else if (sender == tbK2) {
      countCharacters(2, tbK2);
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
  private void doesUserExistRpc(UserData userData) {
    
    AsyncCallback<UserData> callback = new AsyncCallback<UserData>() {
      public void onFailure(Throwable caught) {
      	cp.setRpcFailure(caught);
      }
      public void onSuccess(UserData userData) {
        processKeyExist(userData);
      }
    };
    rpc.doesUserNameExist(userData, callback);
  }

  /**
   * create user account
   * 
   * @param userData
   */
  private void createAccountRpc(UserData userData) {
    
    AsyncCallback<UserData> callback = new AsyncCallback<UserData>() {
      public void onFailure(Throwable caught) {
      	cp.setRpcFailure(caught);
      }
      public void onSuccess(UserData userData) {
        processAccountCreation(userData);
      }
    };
    rpc.createUser(userData, callback);
  }
  
}
