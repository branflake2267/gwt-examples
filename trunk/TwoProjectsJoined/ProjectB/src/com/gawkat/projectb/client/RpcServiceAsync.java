package com.gawkat.projectb.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {
  void greetServer(String input, AsyncCallback<String> callback);
}
