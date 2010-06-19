package org.gonevertical.core.client.admin;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.admin.thing.ThingData;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChangePassword extends DialogBox implements ClickHandler {

  private ClientPersistence cp;

  private RpcCoreServiceAsync rpc = null;
  
  private VerticalPanel pWidget = new VerticalPanel();

  private VerticalPanel pNotification = new VerticalPanel();
  
  private PasswordWidget wSecret = new PasswordWidget();
  
  private PushButton bClose = new PushButton("Cancel");
  private PushButton bSave = new PushButton("Save");
  
  private ThingData thingData = null;
  
  public ChangePassword(ClientPersistence cp) {
    this.cp = cp;

    setText("Change Password");

    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bClose);
    hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
    hp.add(bSave);
    hp.add(new HTML("&nbsp;"));
    hp.add(pNotification);
    
    pWidget.add(wSecret);
    
    pWidget.add(hp);

    setWidget(pWidget);
    
    bClose.addClickHandler(this);
    bSave.addClickHandler(this);
    
    rpc = RpcCore.initRpc();
  }

  public void draw(ThingData thingData) {
    this.thingData = thingData;
  }
  
  private void processChange(boolean b) {

    if (b == true) {
      drawNotify("Your password was changed successfully.");
    } else if (b == false) {
      drawNotify("I wasn't able to change your password, try again");
    }

  }
  
  private void drawNotify(String s) {
    pNotification.clear();
    pNotification.setVisible(true);
    
    HTML html = new HTML(s);
    html.addStyleName("core-Notification");
    html.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
    pNotification.add(html);
    
    Timer t = new Timer() {
      public void run() {
        hide();
        removeFromParent();
      }
    };
    t.schedule(4000);
    
  }
  
  private void save() {
    
    boolean secretsMatch = wSecret.doesPasswordMatch();
    if (secretsMatch == false) {
      wSecret.drawSecretNotify(true, UserData.SECRETS_DONTMATCH);
    }
    
    ChangePasswordData cpd = new ChangePasswordData();
    cpd.setData(thingData, wSecret.getPasswordHash());
    cpd.sign();
    
    chanePasswordRpc(cpd);
  }
  
  public void onClick(ClickEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == bClose) {
      this.hide();
      this.removeFromParent(); // TODO - time this so animation works
    } else if (sender == bSave) {
      save();
    }
    
  }

  private void chanePasswordRpc(ChangePasswordData changePassswordData) {

    rpc.changePassword(cp.getAccessToken(), changePassswordData, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean b) {
        processChange(b);
      }
      public void onFailure(Throwable caught) {
      	cp.setRpcFailure(caught);
      }
    });

  }

 

}
