package com.tribling.gwt.test.oauth.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tribling.gwt.test.oauth.client.rpc.RpcService;
import com.tribling.gwt.test.oauth.client.rpc.RpcServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OAuth implements EntryPoint {

	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// test rpc
		TestRpcCall testRpc = new TestRpcCall();
		
		

		RootPanel.get().add(testRpc);

	}


	
	
}
