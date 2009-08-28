package com.gawkat.core.client;

import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
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
  
  // reference EventManager for event type
  private int changeEvent = 0;
  
  private OAuthTokenData accessToken = null;
  
  public ClientPersistence() {
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
  
  /**
   * cordinate events with the top parent
   * @param changeEvent
   */
  public void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  


  
}
