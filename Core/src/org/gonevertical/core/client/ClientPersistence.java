package org.gonevertical.core.client;

import org.gonevertical.core.client.global.LoadingWidget;
import org.gonevertical.core.client.oauth.OAuthTokenData;

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
  
  //application credentials
  private String appConsumerKey = "demo_application";
  private String appConsumerSecret = "c1d0e06998305903ac76f589bbd6d4b61a670ba6"; //salt:password
  
  private String inputLabel_ConsumerKey = "Email";
  
  // reference EventManager for event type
  private int changeEvent = 0;
  
  // make a blank slate
  private OAuthTokenData accessToken = null;
  
  private LoadingWidget wLoading = new LoadingWidget();
  
  /**
   * constructor
   */
  public ClientPersistence() {
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
