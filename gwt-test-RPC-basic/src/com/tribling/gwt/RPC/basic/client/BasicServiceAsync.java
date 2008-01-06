package com.tribling.gwt.RPC.basic.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BasicServiceAsync {
	public void myMethod(String s, AsyncCallback callback);
}