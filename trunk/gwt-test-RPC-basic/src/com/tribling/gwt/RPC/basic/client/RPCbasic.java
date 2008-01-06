package com.tribling.gwt.RPC.basic.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RPCbasic implements EntryPoint {


	public void onModuleLoad() {
		
		//init the class that will contact the server
		ContactServer cS = new ContactServer();
		
		//call the method that gets the data
		cS.getServerData();
	
	}
	
	
}
