package org.gonevertical.gdata.client.rpc;

import org.gonevertical.gdata.client.login.LoginData;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {

  public void getLoginData(String currentUrl, AsyncCallback<LoginData> callback);
  
}
