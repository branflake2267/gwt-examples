package org.gonevertical.demogwtoauthpopup.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.gadgets.client.io.RequestOptions;

public class RequestOptionsExtended extends RequestOptions {

  protected RequestOptionsExtended() {
  }
  
  public static RequestOptionsExtended newInstanceEx() {
    return JavaScriptObject.createObject().cast();
  }
  
  public final native RequestOptions setOAuthServiceName(String s) /*-{
    this.OAUTH_SERVICE_NAME = s;
    return this;
  }-*/;
  
  public final native RequestOptions setOAuthUseToken(String s) /*-{
    this.OAUTH_USE_TOKEN = s;
    return this;
  }-*/;
    
}
