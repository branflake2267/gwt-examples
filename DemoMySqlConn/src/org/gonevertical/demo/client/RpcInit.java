package org.gonevertical.demo.client;

import com.google.gwt.core.client.GWT;

public class RpcInit {
	
	public RpcInit() {
		
	}

	public static RpcCallServiceAsync initRpc() {
		RpcCallServiceAsync rpc = (RpcCallServiceAsync) GWT.create(RpcCallService.class);
		return rpc;
	}
	
}
