package com.gawkat.core.client.rpc;

import com.google.gwt.core.client.GWT;

public class Rpc {
	
	public static RpcServiceAsync initRpc() {
		RpcServiceAsync call = (RpcServiceAsync) GWT.create(RpcService.class);
		return call;
	}
	
}
