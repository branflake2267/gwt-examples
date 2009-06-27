package com.tribling.gwt.RPC.basic.client;

import com.google.gwt.core.client.EntryPoint;

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
