package org.gonevertical.core.client;

import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.global.LoadingWidget;
import org.gonevertical.core.client.oauth.OAuthTokenData;
import org.gonevertical.core.client.ui.breadcrumbs.BreadCrumbs;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;

/**
 * keep track of persistent objects through out the entire application
 * 
 * @author branflake2267
 *
 */
public class ClientPersistence extends Composite  {
  
	/**
	 * encrypt and hash the password with some salt
	 */
	public static final String PASSWORD_SALT = "salt";
	
  //application credentials
  private String appConsumerKey = null; // demo_application
  private String appConsumerSecret = null; // salt:password - c1d0e06998305903ac76f589bbd6d4b61a670ba6
  
  private String inputLabel_ConsumerKey = "Email";
  
  // reference EventManager for event type
  private int changeEvent = EventManager.ZERO;
  
  // make a blank slate
  private OAuthTokenData accessToken = null;
  
  private LoadingWidget wLoading = new LoadingWidget();
  
  private BreadCrumbs wBreadCrumbsWidget = null;
  
  
  // google analytics tracking
  private Track track = new Track();
	private String trackingCategory;
	
	private boolean applicationLoaded = false;
	private boolean loginStatus = false;

	private String newUserLanding;
	
	/**
	 * constructor - init persitence widget
	 */
	public ClientPersistence() {
		
		// add overall observations to listen for events that we want to act on globally in the app
		addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
		  	if (changeEvent == EventManager.APPLICATION_LOADED) {
		  		applicationLoaded = true;
		  		
		  	} else if (changeEvent == EventManager.USER_LOGGEDIN) {
		  		loginStatus = true;
		  		
		  	} else if (changeEvent == EventManager.USER_LOGGEDOUT) {
		  		loginStatus = false;
		  		
		  	} 
			}
		});
		
	}

  /**
   * init client persisentce across the application
   * set google analytics account
   */
  public void init(String googleAnalyticsAccount, String trackingCategory, String appConsumerKey, String appConsumerSecret) {
  	this.appConsumerKey = appConsumerKey;
  	this.appConsumerSecret = appConsumerSecret;
  	this.trackingCategory = trackingCategory;
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
  	final int event = changeEvent;
    return event;
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
   
  /**
   * track an event with category
   * 
   * @param category
   * @param event
   */
  public void setTrack(String category, String event) {
  	track.setTrack(category,event);
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
		
		showLoading(false);
  }
	
  /**
   * get login status
   * @return
   */
  public boolean getLoginStatus() {
  	return loginStatus;
  }
  
  public boolean getApplicationLoadedStatus() {
  	return applicationLoaded;
  }
   
  /**
   * cordinate events with the top parent
   * @param changeEvent
   */
  public void fireChange(int changeEvent) {
  	System.out.println("ClientPersistence.fireChange() changeEvent=" + changeEvent);
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

	public void resetEvent() {
		changeEvent = EventManager.ZERO;
  }

	public void setNewUserLanding(String newUserLanding) {
		this.newUserLanding = newUserLanding;
  }

	public String getNewUserLanding() {
		String s = newUserLanding;
		if (newUserLanding == null || newUserLanding.length() == 0) {
			s = "core_profile_aboutme";
		}
		return s;
	}

  
}
