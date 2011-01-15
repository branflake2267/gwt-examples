package org.gonevertical.demo.client.layout;

import org.gonevertical.demo.client.EventManager;
import org.gonevertical.demo.client.LoginData;
import org.gonevertical.demo.client.rpc.RpcInit;
import org.gonevertical.demo.client.rpc.RpcServiceAsync;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class Home extends Composite {

  private int loginState;
  
  private HTML h1;
  private HTML h2;
  private RpcServiceAsync rpc;
  private boolean loggedIn;
  private LoginData loginData;
  private HTML hNick;
  private AskForAccessButton askForAccessButton;
  private HTML hNote;

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
    
    VerticalPanel verticalPanel_1 = new VerticalPanel();
    vpMain.add(verticalPanel_1);
    
    hNote = new HTML("First login to google. Then click ask for access, to get Google Docs acces. I'll add some document listing later.", true);
    verticalPanel_1.add(hNote);
    
    VerticalPanel vp = new VerticalPanel();
    vpMain.add(vp);
    vp.setWidth("100%");
    
    HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
    vp.add(horizontalPanel_1);
    
    askForAccessButton = new AskForAccessButton();
    horizontalPanel_1.add(askForAccessButton);
    
    HTML htmlYouNeedAccess = new HTML("You need access first to get blog data.", true);
    horizontalPanel_1.add(htmlYouNeedAccess);
    horizontalPanel_1.setCellVerticalAlignment(htmlYouNeedAccess, HasVerticalAlignment.ALIGN_MIDDLE);
    
    VerticalPanel verticalPanel = new VerticalPanel();
    vpMain.add(verticalPanel);
    
    HTML htmlnbsp = new HTML("&nbsp;", true);
    verticalPanel.add(htmlnbsp);
    
    setup();
  }

  private void setup() {
    rpc = RpcInit.init();
    
    String currentUrl = Window.Location.getPath() + Window.Location.getQueryString() + "#" + History.getToken();
    getLoginData(currentUrl);
    
    askForAccessButton.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        AskForAccessButton b = (AskForAccessButton) event.getSource();
        int e = b.getChangeEvent();
        if (e == EventManager.OAUTHTOKEN_RETRIEVED) {
          getSomeBlogData();
        }
      }
    });
    
  }
  
  private void getSomeBlogData() {
    rpc.getBlogList(new AsyncCallback<String>() {
      public void onSuccess(String s) {
        processBlogData(s);
      }
      public void onFailure(Throwable caught) {
        // TODO
      }
    });
  }

  protected void processBlogData(String s) {
    askForAccessButton.setVisible(false);
    hNote.setHTML("Got some Blog Data: <br/><br/>" + s);
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
      loginState = EventManager.LOGGEDOUT;
      drawLoggedOut();
    } else {
      loginState = EventManager.LOGGEDIN;
      drawLoggedIn();
    }

  }

  private void drawLoggedOut() {
    String url = loginData.getGoogleLoginUrl();
    String signIn = "<a href=\"" + url + "\">Sign In</a>&nbsp;&nbsp;";
    String create = "<a href=\"" + url + "\">Create Account</a>";
    h1.setHTML(signIn);
    h2.setHTML(create);
    
    askForAccessButton.getBAskForAccess().setEnabled(false);
  }

  private void drawLoggedIn() {
    String url = loginData.getGoogleLogoutUrl();
    String nick = "<a>" + loginData.getGoogleNickNotNull() + "</a>&nbsp;&nbsp;";
    String logOut = "<a href=\"" + url + "\">Sign out</a>";
    h1.setHTML(nick);
    h2.setHTML(logOut);
    
    askForAccessButton.getBAskForAccess().setEnabled(true);
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

  public HTML getHNote() {
    return hNote;
  }
}
