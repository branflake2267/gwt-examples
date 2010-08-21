package org.gonevertical.gaeloadtest.client;

import com.google.gwt.core.client.GWT;

public class RpcInit {
	
  /**
   * init rpc call provider service
   * @return
   */
	public static RpcServiceAsync initRpc() {
		RpcServiceAsync call = (RpcServiceAsync) GWT.create(RpcService.class);
		return call;
	}
	
}
