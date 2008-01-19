package com.tribling.gwt.RPC.adv.client;

import com.google.gwt.core.client.EntryPoint;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RPCadv implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

	  
	  
	//init the class that will contact the server
	ContactServer cS = new ContactServer();
	
	//call the method that gets the data
	cS.getServerData();
	
	
  }
}
