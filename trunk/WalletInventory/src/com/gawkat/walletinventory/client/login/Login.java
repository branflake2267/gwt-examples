package com.gawkat.walletinventory.client.login;

import com.gawkat.walletinventory.client.rpc.Rpc;
import com.gawkat.walletinventory.client.rpc.RpcServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login extends Composite {

  private RpcServiceAsync rpc = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  public Login() {
    
    initWidget(pWidget);
    
    rpc = Rpc.init();
    
    getLoginData();
  }
  
  private void processLoginData(LoginData loginData) {
  
    String name = null;
    String url = null;
    if (loginData.isLoggedIn() == false) {
      name = "login";
      url = loginData.getLoginUrl();
    } else {
      name = "logout " + loginData.getNickname();
      url = loginData.getLogoutUrl();
    }
    
    HTML hl = new HTML("<a href=\"" + url + "\">" + name + "</a>");
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(hl);
    
    pWidget.add(hp);
    
  }
  
  private void getLoginData() {
    
    String requestUri = GWT.getHostPageBaseURL();
    
    rpc.getLoginData(requestUri, new AsyncCallback<LoginData>() {
      public void onSuccess(LoginData loginData) {
        processLoginData(loginData);
      }

      public void onFailure(Throwable caught) {
        // TODO
      }
    });
  }
  
}
