package org.gonevertical.core.client.ui.login;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.oauth.Sha1;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PasswordWidget extends Composite implements KeyPressHandler, ChangeHandler {

  private VerticalPanel pWidget = new VerticalPanel();
  
  // when notification is needed for secret error
  private FlowPanel pSecretError = new FlowPanel();
  
  // secret container
  private VerticalPanel pSecret = new VerticalPanel();
  
  // secret character count
  private FlowPanel pSecretCount1 = new FlowPanel();
  private FlowPanel pSecretCount2 = new FlowPanel();
  
  // Secret (password)
  private TextBox tbS1 = new TextBox();
  private TextBox tbS2 = new TextBox();
  
  // min amount of characters wanted for password
  private final int consumerSecret_Len = 6;
  
  public PasswordWidget() {
    
    Label ls1 = new Label("Password");
    Label ls2 = new Label("Verify Password");
    
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
    
    // secret container
    pSecret.add(pSecretError);
    pSecret.add(hpS1);
    pSecret.add(hpS2);
    
    pSecretCount1.add(new HTML("0"));
    pSecretCount2.add(new HTML("0"));
    
    pWidget.add(pSecret);
    
    initWidget(pWidget);
    
    tbS1.setWidth("300px");
    tbS2.setWidth("300px");
    
    tbS1.addChangeHandler(this);
    tbS2.addChangeHandler(this);
    tbS1.addKeyPressHandler(this);
    tbS2.addKeyPressHandler(this);
    
    pSecretError.addStyleName("core-CreateUserAccount-Error");
    hpS1.setCellVerticalAlignment(ls1, VerticalPanel.ALIGN_MIDDLE);
    hpS2.setCellVerticalAlignment(ls2, VerticalPanel.ALIGN_MIDDLE);
    hpS1.setCellVerticalAlignment(pSecretCount1, VerticalPanel.ALIGN_MIDDLE);
    hpS2.setCellVerticalAlignment(pSecretCount2, VerticalPanel.ALIGN_MIDDLE);
    pSecretCount1.setStyleName("core-CreateUserAccount-CharCountError");
    pSecretCount2.setStyleName("core-CreateUserAccount-CharCountError");
    ls1.setStyleName("core-CreateUserAccount-Field");
    ls2.setStyleName("core-CreateUserAccount-Field");
  }

  private void countCharacters(int input, TextBox tb) {
    
    int ilen = tb.getText().length();
    String len = Integer.toString(ilen);
        
    switch (input) {
    case 3: // secret 1
      pSecretCount1.clear();
      pSecretCount1.add(new HTML(len));
      if (ilen > consumerSecret_Len) {
        pSecretCount1.removeStyleName("core-CreateUserAccount-CharCountError");
        pSecretCount1.setStyleName("core-CreateUserAccount-CharCountPass");
      } else {
        pSecretCount1.setStyleName("core-CreateUserAccount-CharCountError");
        pSecretCount1.removeStyleName("core-CreateUserAccount-CharCountPass");
      }
      break;
    case 4: // secret 2
      pSecretCount2.clear();
      pSecretCount2.add(new HTML(len));
      if (ilen > consumerSecret_Len) {
        pSecretCount2.removeStyleName("core-CreateUserAccount-CharCountError");
        pSecretCount2.setStyleName("core-CreateUserAccount-CharCountPass");
      } else {
        pSecretCount2.setStyleName("core-CreateUserAccount-CharCountError");
        pSecretCount2.removeStyleName("core-CreateUserAccount-CharCountPass");
      }
      break;
    }
    
  }
  
  boolean doesPasswordMatch() {
    
    String p1 = getS1();
    String p2 = getS2();
    
    // TODO - no spaces in middle?
    
    boolean pass = false;
    if (p1.equals(p2)) {
      pass = true;
    } 
    
    return pass;
  }
  
  public String getPasswordHash() {
    String password = getS1();
    Sha1 sha = new Sha1();
    String hash = sha.hex_hmac_sha1(ClientPersistence.PASSWORD_SALT, password);
    return hash;
  }
  
  public void drawSecretNotify(boolean bol, int error) {
    pSecretError.clear();
    if (bol == true) {
      pSecret.setStyleName("core-CreateUserAccount-ErrorInput");
      if (error > 0) {
        pSecretError.add(new HTML(UserData.getError(error)));
      }
    } else {
      pSecret.removeStyleName("core-CreateUserAccount-ErrorInput");
      pSecretError.clear();
    }
  }
  
  public String getS1() {
    return tbS1.getText().trim();
  }
  
  public String getS2() {
    return tbS2.getText().trim();
  }
  
  public void onKeyPress(KeyPressEvent event) {
    Widget sender = (Widget) event.getSource();
    
    if (sender == tbS1) {
      countCharacters(3, tbS1);
    } else if (sender == tbS2) {
      countCharacters(4, tbS2);
    }
  }

  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    
    if (sender == tbS1) {
      countCharacters(3, tbS1);
    } else if (sender == tbS2) {
      countCharacters(4, tbS2);
    }
    
  }
  
}
