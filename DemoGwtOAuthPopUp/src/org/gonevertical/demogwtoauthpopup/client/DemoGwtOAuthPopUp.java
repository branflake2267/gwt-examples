package org.gonevertical.demogwtoauthpopup.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.gadgets.client.NeedsLockedDomain;
import com.google.gwt.gadgets.client.io.AuthorizationType;
import com.google.gwt.gadgets.client.io.IoProvider;
import com.google.gwt.gadgets.client.io.MethodType;
import com.google.gwt.gadgets.client.io.Response;
import com.google.gwt.gadgets.client.io.ResponseReceivedHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

@ModulePrefs(//
    title = "Demo GWT Gadget OAuth", //
    directory_title = "Demo GWT Gadget OAuth", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "http://gwt-examples.googlecode.com")
@com.google.gwt.gadgets.client.Gadget.UseLongManifestName(false)
@com.google.gwt.gadgets.client.Gadget.AllowHtmlQuirksMode(false)
@com.google.gwt.gadgets.client.Gadget.InjectModulePrefs(files="ModulePrefs.txt")
@com.google.gwt.gadgets.client.Gadget.InjectContent(files="Content.txt")
public class DemoGwtOAuthPopUp extends Gadget<GadgetPreferences> implements NeedsLockedDomain {

  private VerticalPanel vpContent = new VerticalPanel();
  
  protected void init(GadgetPreferences preferences) {
    
    HTML html = new HTML("Demo GWT Gadget has loaded. v19");
    
    VerticalPanel pWidget = new VerticalPanel();
    pWidget.add(vpContent);
    pWidget.add(html);
    
    RootPanel.get().add(pWidget);
    
    makeRequest();
  }

  private void drawPersonalize(final String url) {
    vpContent.clear();
    String s = "<a>Personalize this gadget<img src=\"http://demogwtgadgetoauthpopup.appspot.com/demogwtoauthpopup/images/new.gif\"/></a>";
    HTML h = new HTML(s);
    vpContent.add(h);
    h.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        open(url);
      }
    });
     
  }

  private void makeRequest() {
    
    ResponseReceivedHandler<String> handler = new ResponseReceivedHandler<String>() {
      public void onResponseReceived(ResponseReceivedEvent<String> event) {
        
        Response<String> response = event.getResponse();
        
        Window.alert("1 response. url?: " + response.getOauthApprovalUrl() + " txt: " + response.getText() + 
            " statuscd: " + response.getStatusCode() + " oaer: " + response.getOauthError());
        
        if (response.getStatusCode() == 200 && response.getOauthApprovalUrl() != null) {
          drawPersonalize(response.getOauthApprovalUrl());
        }
        
        if (response.getStatusCode() == 200) {
          //Window.alert("2 reponse == 200" + response.toString() + " d: " + response.getData().toString());
          
        } else {
          if (response.getErrors() != null) {
            String e = "";
            JsArrayString ea = response.getErrors();
            for (int i=0; i < response.getErrors().length(); i++) {
              e += ea.get(i) + "\n";
            }
            Window.alert("errors: " + e);
          }
        }
        
        
      }
    };
    
    RequestOptionsExtended options = RequestOptionsExtended.newInstanceEx();
    options.setMethodType(MethodType.GET);
    options.setAuthorizationType(AuthorizationType.OAUTH);
    options.setOAuthServiceName("google");
    options.setOAuthUseToken("always");
    
    String url = "http://www.blogger.com/feeds/default/blogs";
    IoProvider.get().makeRequestAsText(url, handler, options);
    
  }
 


  /**
   * open window, then start a process to watch for it to close
   * 
   * @param url
   */
  private void open(String url) {
    openWindow(url);
    loop();
  }

  /**
   * open new window
   * 
   * @param url
   * @return
   */
  private final native void openWindow(String url) /*-{
    $wnd.winHandle = $wnd.open(url, "_blank", null);
  }-*/;
  
  /**
   * is window still open
   * 
   * @return
   */
  private final native boolean isWindowClosed() /*-{
    var b = false;
    if ($wnd.winHandle.closed == true) {
      b = true;
    }
    return b;
  }-*/;
  
  /**
   * loop over and over watching for window to close
   */
  private void loop() {
    Timer t = new Timer() {
      public void run() {
        if (isWindowClosed() == true) {
          setWindowClosed();
          return;
        }
        loop();
      }
    };
    t.schedule(100);
  }
  
  /**
   * call this when the window closes
   */
  public void setWindowClosed() {
    Window.alert("setWindowClosed");
    
    // TODO - did we get accesss or not
    
  }
  
}
