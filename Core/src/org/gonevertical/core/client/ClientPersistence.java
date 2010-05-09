package org.gonevertical.core.client;

import org.gonevertical.core.client.account.ui.LoginWidget;
import org.gonevertical.core.client.global.LoadingWidget;
import org.gonevertical.core.client.oauth.OAuthTokenData;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * keep track of persistent objects through out the entire application
 * 
 * @author branflake2267
 *
 */
public class ClientPersistence extends Composite  {
  
  //application credentials
  private String appConsumerKey = null; // demo_application
  private String appConsumerSecret = null; // salt:password - c1d0e06998305903ac76f589bbd6d4b61a670ba6
  
  private String inputLabel_ConsumerKey = "Email";
  
  // reference EventManager for event type
  private int changeEvent = 0;
  
  // make a blank slate
  private OAuthTokenData accessToken = null;
  
  private LoadingWidget wLoading = new LoadingWidget();
  
  private BreadCrumbs wBreadCrumbsWidget = null;
  private LoginWidget wLoginWidget = null;
  
  // google analytics tracking
  private Track track = new Track();
	private String trackingCategory;
	
	private boolean loginStatus = false;
	
  
  /**
   * init client persisentce across the application
   * set google analytics account
   */
  public ClientPersistence(String googleAnalyticsAccount, String appConsumerKey, String appConsumerSecret) {
  	this.appConsumerKey = appConsumerKey;
  	this.appConsumerSecret = appConsumerSecret;
  	track.setGoogleAnalyticsAccount(googleAnalyticsAccount);
  }
  
  public LoadingWidget getLoadingWidget() {
  	return wLoading;
  }
  
  public OAuthTokenData getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(OAuthTokenData accessToken) {
    this.accessToken = accessToken;
  }

  public String getAppConsumerKey() {
    return appConsumerKey;
  }
  
  public String getAppConsumerSecret() {
    return appConsumerSecret;
  }
  
  public int getChangeEvent() {
    return changeEvent;
  }
  
  public String getInputLabel_ConsumerKey() {
    return inputLabel_ConsumerKey;
  }
  
  /**
   * set a bread crumbs widget reference so we can add bread crumbs from anywhere in the app
   * 
   * @param wBreadCrumbs
   */
  public void setBreadCrumbsWidgetReference(BreadCrumbs wBreadCrumbs) {
  	if (wBreadCrumbs == null) {
  		System.err.println("ClientPersistence.setBreadCrumbsWidgetReference(): Did you set this in the wrong order?");
  		return;
  	}
  	this.wBreadCrumbsWidget = wBreadCrumbs;
  	wBreadCrumbs.setLoadingWidget(wLoading);
  }
  
	public void setLoginWidgetReference(LoginWidget wLoginWidget) {
		if (wLoginWidget == null) {
			System.err.println("ClientPersistence.setLoginWidgetReference(): Did you set this in the wrong order?");
			return;
		}
		this.wLoginWidget = wLoginWidget;
  }
  
  /**
   * add/register a bread crumb
   * 
   * @param forHistoryToken
   * @param showCrumbName
   */
  public void setBreadCrumb(String showCrumbName, String forHistoryToken) {
  	wBreadCrumbsWidget.setBreadCrumb(showCrumbName, forHistoryToken);
  }
  
	public void setBreadCrumbCategory(String showCrumbName, String forHistoryToken) {
		wBreadCrumbsWidget.setBreadCrumbCategory(showCrumbName, forHistoryToken);
  }
  
  /**
   * track an event
   * 
   * @param event
   */
  public void setTrack(String event) {
  	if (trackingCategory != null) {
  		event = trackingCategory + "/" + event;
  	}
  	track.setTrack(event);
  }
  
  public void setTrackingCategory(String category) {
  	this.trackingCategory = category;
  }
  
  /**
   * track an event with category
   * 
   * @param category
   * @param event
   */
  public void setTrack(String category, String event) {
  	track.setTrack(category,event);
  }
  
	/**
	 * successfull login sets this
	 * @param b
	 */
	public void setLoginStatus(boolean b) {
		this.loginStatus = b;
  }
	
  /**
   * get login status
   * @return
   */
  public boolean getLoginStatus() {
  	return loginStatus;
  }
   
  /**
   * cordinate events with the top parent
   * @param changeEvent
   */
  public void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  public void showLoading(boolean b) {
  	if (b == true) {
  		wLoading.show();
  	} else {
  		wLoading.hide();
  	}
  }

	public void setRpcFailure(Throwable caught) {
		// TODO - fixup later
		Window.alert("ClientPersistence.setRpcFailure: " + caught.toString());
		System.out.println(caught);
  }





  
  
}
