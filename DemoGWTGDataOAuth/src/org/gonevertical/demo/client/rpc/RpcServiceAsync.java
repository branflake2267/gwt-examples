package org.gonevertical.demo.client.rpc;

import org.gonevertical.demo.client.LoginData;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {

  public void getLoginData(String currentUrl, AsyncCallback<LoginData> callback);

  public void getHasToken(AsyncCallback<Boolean> callback);
 
}
