package org.gonevertical.demo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>RpcService</code>.
 */
public interface RpcCallServiceAsync {
  
	public void getBibleInfo(AsyncCallback<BibleData[]> callback);
	
}
