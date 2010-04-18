package org.gonevertical.core.client.tests;

import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TestRpcCallWidget extends Composite {

	public RpcCoreServiceAsync callRpcService;
	
	private VerticalPanel pWidget = new VerticalPanel();

	public TestRpcCallWidget() {

		// init the rpc proxy
		// its publicly available to the other classes
		callRpcService = RpcCore.initRpc();
		
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
