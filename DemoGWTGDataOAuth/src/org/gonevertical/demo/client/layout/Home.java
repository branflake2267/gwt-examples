package org.gonevertical.demo.client.layout;

import org.gonevertical.demo.client.LoginData;
import org.gonevertical.demo.client.rpc.RpcInit;
import org.gonevertical.demo.client.rpc.RpcServiceAsync;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Home extends Composite {
  
  public static final int LOGGEDOUT = 1;
  public static final int LOGGEDIN = 2;
  private int loginState;
  
  private HTML h1;
  private HTML h2;
  private RpcServiceAsync rpc;
  private boolean loggedIn;
  private LoginData loginData;
  private HTML hNick;
  private AskForAccessButton askForAccessButton;

  public Home() {
    
    VerticalPanel vpMain = new VerticalPanel();
    initWidget(vpMain);
    vpMain.setWidth("100%");
    
    VerticalPanel vpTop = new VerticalPanel();
    vpMain.add(vpTop);
    vpTop.setWidth("100%");
    
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    vpTop.add(horizontalPanel);
    vpTop.setCellHorizontalAlignment(horizontalPanel, HasHorizontalAlignment.ALIGN_RIGHT);
    
    hNick = new HTML("&nbsp;", true);
    horizontalPanel.add(hNick);
    
    h1 = new HTML("&nbsp;", true);
    horizontalPanel.add(h1);
    
    h2 = new HTML("&nbsp;", true);
    horizontalPanel.add(h2);
    
    VerticalPanel vp = new VerticalPanel();
    vpMain.add(vp);
    vp.setWidth("100%");
    
    HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
    vp.add(horizontalPanel_1);
    
    askForAccessButton = new AskForAccessButton();
    horizontalPanel_1.add(askForAccessButton);
    
    VerticalPanel verticalPanel = new VerticalPanel();
    vpMain.add(verticalPanel);
    
    HTML htmlnbsp = new HTML("&nbsp;", true);
    verticalPanel.add(htmlnbsp);
    
    VerticalPanel verticalPanel_1 = new VerticalPanel();
    vpMain.add(verticalPanel_1);
    
    HTML htmlFirstLoginTo = new HTML("First login to google. Then click ask for access, to get Google Docs acces. I'll add some document listing later.", true);
    verticalPanel_1.add(htmlFirstLoginTo);
    
    setup();
  }

  private void setup() {
    rpc = RpcInit.init();
    
    String currentUrl = Window.Location.getPath() + Window.Location.getQueryString() + "#" + History.getToken();
    getLoginData(currentUrl);
  }
  
  private void getLoginData(String currentUrl) {
    rpc.getLoginData(currentUrl, new AsyncCallback<LoginData>() {
      public void onSuccess(LoginData loginData) {
        processLoginData(loginData);
      }
      public void onFailure(Throwable caught) {
        // TODO
      }
    });
  }

  private void processLoginData(LoginData loginData) {
    this.loginData = loginData;
    
    if (loginData == null || loginData.getGoogleLoggedIn() == false) {
      loginState = LOGGEDOUT;
      drawLoggedOut();
    } else {
      loginState = LOGGEDIN;
      drawLoggedIn();
    }

  }

  private void drawLoggedOut() {
    String url = loginData.getGoogleLoginUrl();
    String signIn = "<a href=\"" + url + "\">Sign In</a>&nbsp;&nbsp;";
    String create = "<a href=\"" + url + "\">Create Account</a>";
    h1.setHTML(signIn);
    h2.setHTML(create);
    
    askForAccessButton.getPshbtnAskForAccess().setEnabled(false);
  }

  private void drawLoggedIn() {
    String url = loginData.getGoogleLogoutUrl();
    String nick = "<a>" + loginData.getGoogleNickNotNull() + "</a>&nbsp;&nbsp;";
    String logOut = "<a href=\"" + url + "\">Sign out</a>";
    h1.setHTML(nick);
    h2.setHTML(logOut);
    
    askForAccessButton.getPshbtnAskForAccess().setEnabled(true);
  }
  
  
  public HTML getH1() {
    return h1;
  }
  public HTML getH2() {
    return h2;
  }
  public HTML getHNick() {
    return hNick;
  }

}
