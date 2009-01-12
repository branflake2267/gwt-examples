package com.gawkat.gwt.oauth.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class Rpc {
	
	/**
	 *  instantiated service proxy
	 *  
	 *  It is safe to cache the instantiated service proxy to avoid 
	 *  creating it for subsequent calls. For example, you can instantiate 
	 *  the service proxy in the module's onModuleLoad() method and save the 
	 *  resulting instance as a class member. 
	 *  
	 *  http://code.google.com/docreader/#p=google-web-toolkit-doc-1-5&s=google-web-toolkit-doc-1-5&t=DevGuideMakingACall
	 */
	public static RpcServiceAsync initRpc() {
		RpcServiceAsync callRpcService = (RpcServiceAsync) GWT.create(RpcService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) callRpcService;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "rpcServiceAccounts";
		endpoint.setServiceEntryPoint(moduleRelativeURL);
		
		return callRpcService;
	}
}
