package org.gonevertical.demo.client;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gadgets.client.AdsFeature;
import com.google.gwt.gadgets.client.DynamicHeightFeature;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.gadgets.client.GoogleAnalyticsFeature;
import com.google.gwt.gadgets.client.GoogleAnalyticsFeature.Tracker;
import com.google.gwt.gadgets.client.NeedsAds;
import com.google.gwt.gadgets.client.NeedsDynamicHeight;
import com.google.gwt.gadgets.client.NeedsGoogleAnalytics;
import com.google.gwt.gadgets.client.NeedsSetPrefs;
import com.google.gwt.gadgets.client.NeedsSetTitle;
import com.google.gwt.gadgets.client.NeedsViews;
import com.google.gwt.gadgets.client.SetPrefsFeature;
import com.google.gwt.gadgets.client.SetTitleFeature;
import com.google.gwt.gadgets.client.ViewFeature;
import com.google.gwt.gadgets.client.io.AuthorizationType;
import com.google.gwt.gadgets.client.io.IoProvider;
import com.google.gwt.gadgets.client.io.MethodType;
import com.google.gwt.gadgets.client.io.Response;
import com.google.gwt.gadgets.client.io.ResponseReceivedHandler;
import com.google.gwt.gadgets.client.osapi.NeedsOsapi;
import com.google.gwt.gadgets.client.osapi.OsapiFeature;
import com.google.gwt.gadgets.client.rpc.NeedsRpc;
import com.google.gwt.gadgets.client.rpc.RpcFeature;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

@ModulePrefs(//
    title = "Demo GWT Spreadsheet Gadget", //
    directory_title = "Testing GWT Spreadsheet Docs Gadget", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "GoneVertical.org", //
    height = 610, //
    width = 400)
@com.google.gwt.gadgets.client.Gadget.UseLongManifestName(false)
@com.google.gwt.gadgets.client.Gadget.AllowHtmlQuirksMode(false)
public class SpreadsheetGadget extends Gadget<SpreadsheetGadgetPreferences> implements 
    NeedsDynamicHeight, NeedsAds, NeedsGoogleAnalytics, NeedsOsapi, NeedsSetTitle, NeedsViews, 
    NeedsRpc,NeedsSetPrefs {

  private Tracker tracker;
  private AdsFeature adsFeature;
  private DynamicHeightFeature heightFeature;
  private OsapiFeature osapiFeature;
  private ViewFeature viewFeature;
  private SetTitleFeature titleFeature;
  private SetPrefsFeature setPreFeature;
  private RpcFeature rpcFeature;
  
  private VerticalPanel vpContent = new VerticalPanel();
  
  /**
   * lets keep track of trying 1 and 2 for makerequest
   */
  private boolean tryagain;
  
  private SpreadsheetGadgetPreferences preferences;
  
  /**
   * init gadget
   */
  protected void init(SpreadsheetGadgetPreferences preferences) {
    this.preferences = preferences;
    
    // some simple ui
    HTML h1 = new HTML("Demo GWT Gadget has loaded. v23");
    HTML h2 = new HTML("<a target=\"_blank\" href=\"http://code.google.com/p/gwt-examples/wiki/DemoGwtGadgetOAuthPopup\">Back To gwt-examples.googlecode.com to see more</a>");
    
    VerticalPanel pWidget = new VerticalPanel();
    pWidget.add(vpContent);
    pWidget.add(h1);
    pWidget.add(h2);
    RootPanel.get().add(pWidget);
    
    
  	String eventName = "demogadgetspreadsheet";
  	String action = "home_loaded";
  	tracker.reportEvent(eventName, action);
  	
    seekPermissionFirst(false);
    
  }
  
  private void start() {
    vpContent.clear();
    
    Window.alert("Table Query Url" + preferences._table_query_url());
       
  }

  public void initializeFeature(GoogleAnalyticsFeature feature) {
    tracker = feature.createTracker("UA-2862268-9");
  }

  public void initializeFeature(AdsFeature feature) {
    this.adsFeature = feature;
  }

  public void initializeFeature(DynamicHeightFeature feature) {
    this.heightFeature = feature;
  }

  public void initializeFeature(OsapiFeature feature) {
    this.osapiFeature = feature;
  }
  
  public void initializeFeature(ViewFeature feature) {
    this.viewFeature = feature;
  }

  public void initializeFeature(SetTitleFeature feature) {
    this.titleFeature = feature;
  }

  public void initializeFeature(SetPrefsFeature feature) {
    this.setPreFeature = feature;
  }

  public void initializeFeature(RpcFeature feature) {
    this.rpcFeature = feature;
  }
  

  /**
   * if we don't have oauth
   * 
   * @param url - gdata url
   */
  private void drawPersonalize(final String url) {
    String trya = "";
    if (tryagain == true) {
      trya = "Try again, you must have not granted access to this.";
    }
    vpContent.clear();
    String s = "<a>Personalize this gadget&nbsp;<img src=\"http://demogwtgadgetoauthpopup.appspot.com/demogwtoauthpopup/images/new.gif\"/></a> " + trya;
    HTML h = new HTML(s);
    h.addStyleName("demogadget_personalize");
    vpContent.add(h);
    h.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        open(url);
      }
    });
     
  }

  /**
   * make a request for data, and it may ask us for permission to get it
   */
  private void seekPermissionFirst(boolean tryagian) {
    this.tryagain = tryagian;

    ResponseReceivedHandler<String> callback = new ResponseReceivedHandler<String>() {
      public void onResponseReceived(ResponseReceivedEvent<String> event) {
        Response<String> response = event.getResponse();
        processSeekPermissionResponse(response);
      }
    };

    RequestOptionsExtended options = RequestOptionsExtended.newInstanceEx();
    options.setMethodType(MethodType.GET);
    options.setAuthorizationType(AuthorizationType.OAUTH);
    options.setOAuthServiceName("google");
    options.setOAuthUseToken("always");

    String url = "https://spreadsheets.google.com/feeds/";
    IoProvider.get().makeRequestAsText(url, callback, options);
  }
 
  /**
   * MakeRequest's callback, lets ask to personalize if no oauth url, or we shoudl have some data if its been done already.
   * 
   * @param response - makerequest callback response
   */
  protected void processSeekPermissionResponse(Response<String> response) {
    
    if (response.getStatusCode() == 200 && response.getOauthApprovalUrl() != null) {
      drawPersonalize(response.getOauthApprovalUrl());
      
    } else if (response.getStatusCode() == 200 && response.getOauthApprovalUrl() == null) {
      start();
      
    } else if (response.getStatusCode() != 200) {
      if (response.getErrors() != null) {
        String s = "";
        s += " getStatusCode(): " + response.getStatusCode() + "<br/>\n";
        s += " getText(): " + response.getText() + "<br/>\n";
        s += " getOauthApprovalUrl(): " + response.getOauthApprovalUrl() + "<br/>\n";
        s += " getOauthErrorText(): " + response.getOauthErrorText() + "<br/>\n";
        s += " getOauthError(): " + response.getOauthError() + "<br/>\n";
        s += " getErrors(): <br/>\n";
        JsArrayString ea = response.getErrors();
        for (int i=0; i < response.getErrors().length(); i++) {
          s += ea.get(i) + "<br/>\n";
        }
        vpContent.clear();
        vpContent.add(new HTML(s));
      }
    }
    
  }

  /**
   * open window, then start a process to watch for it to close
   * 
   * @param url - oauth approval url
   */
  private void open(String url) {
    vpContent.clear();
    vpContent.add(new HTML("Waiting for permission window to close."));
    openWindow(url);
    loop();
  }

  /**
   * open new window
   * 
   * @param url - oauth approval url
   * @return
   */
  private final native void openWindow(String url) /*-{
    $wnd.winHandle = $wnd.open(url, "_blank", null);
  }-*/;
  
  /**
   * is popup window still open
   * 
   * @return - boolean
   */
  private final native boolean isWindowClosed() /*-{
    var b = false;
    // TODO check for undefined window
    if ($wnd.winHandle.closed == true) {
      b = true;
    }
    return b;
  }-*/;
  
  /**
   * loop over and over watching for window to close
   * TODO - add a recursive boundary, like only check 1000 times
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
    
    // lets try again - something may have been approved
    seekPermissionFirst(true);
    
  }
	
}
