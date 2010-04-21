package org.gonevertical.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.protobuf.RpcCallback;

public class RpcInit {
	
	public RpcInit() {
	}

	public static RpcServiceAsync initRpc() {
		RpcServiceAsync rpc = (RpcServiceAsync) GWT.create(RpcService.class);
		return rpc;
	}
	
}
