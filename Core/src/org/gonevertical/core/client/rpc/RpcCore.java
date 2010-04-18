package org.gonevertical.core.client.rpc;

import com.google.gwt.core.client.GWT;

public class RpcCore {
	
  /**
   * init rpc call provider service
   * @return
   */
	public static RpcCoreServiceAsync initRpc() {
		RpcCoreServiceAsync call = (RpcCoreServiceAsync) GWT.create(RpcCoreService.class);
		return call;
	}
	
}
