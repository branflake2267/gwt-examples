package com.tribling.gwt.test.oauth.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tribling.gwt.test.oauth.client.rpc.RpcService;
import com.tribling.gwt.test.oauth.client.rpc.RpcServiceAsync;

public class TestRpcCall extends Composite {

	public static RpcServiceAsync callRpcService;
	
	private VerticalPanel pWidget = new VerticalPanel();

	public TestRpcCall() {

		// init the rpc proxy
		// its publicly available to the other classes
		initRpc();
		
		// init a composite widget
		initWidget(pWidget);

		// test a rpc call 
		String s = "client side : ";
		getStringFromServer(s);
	}
	
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
	public void initRpc() {
		callRpcService = (RpcServiceAsync) GWT.create(RpcService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) callRpcService;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "rpcService";
		endpoint.setServiceEntryPoint(moduleRelativeURL);
	}


	public void getStringFromServer(String s) {
		
		AsyncCallback<String> callback = new AsyncCallback<String>() {	
			// on failure
			public void onFailure(Throwable ex) {
				RootPanel.get().add(new HTML(ex.toString()));
			}
			// on success
			public void onSuccess(String result) {
				processCallback(result);				
			}
		};
		
		// execute rpc and wait for its response
		callRpcService.testMethod(s, callback);
	}

	private void processCallback(String s) {
		pWidget.add(new HTML("callback: " + s));
	}

}
