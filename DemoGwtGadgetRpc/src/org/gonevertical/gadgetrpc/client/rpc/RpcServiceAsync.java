package org.gonevertical.gadgetrpc.client.rpc;

import org.gonevertical.gadgetrpc.client.layout.LoginData;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {

  public void getLoginData(String currentUrl, AsyncCallback<LoginData> callback);
  
}
