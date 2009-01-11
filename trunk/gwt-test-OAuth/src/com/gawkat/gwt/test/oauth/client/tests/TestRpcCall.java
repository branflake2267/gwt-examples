package com.gawkat.gwt.test.oauth.client.tests;

import com.gawkat.gwt.test.oauth.client.rpc.Rpc;
import com.gawkat.gwt.test.oauth.client.rpc.RpcServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TestRpcCall extends Composite {

	public RpcServiceAsync callRpcService;
	
	private VerticalPanel pWidget = new VerticalPanel();

	public TestRpcCall() {

		// init the rpc proxy
		// its publicly available to the other classes
		callRpcService = Rpc.initRpc();
		
		// init a composite widget
		initWidget(pWidget);

		// test a rpc call 
		String s = "Testing RPC: client side: ";
		getStringFromServer(s);
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
		pWidget.add(new HTML("Server Response:(" + s + ") "));
	}

}
