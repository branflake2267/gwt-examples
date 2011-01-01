package org.gonevertical.gdata.client.rpc;

import com.google.gwt.core.client.GWT;

public class RpcInit {

	public static RpcServiceAsync init() {
		RpcServiceAsync call = (RpcServiceAsync) GWT.create(RpcService.class);
		return call;
	}
	
}
