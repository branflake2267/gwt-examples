package com.tribling.gwt.RPC.basic.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface BasicService extends RemoteService {
	
	public String myMethod(String s);
	
}