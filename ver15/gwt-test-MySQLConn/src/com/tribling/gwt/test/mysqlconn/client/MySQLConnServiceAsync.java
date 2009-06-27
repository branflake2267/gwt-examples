package com.tribling.gwt.test.mysqlconn.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The interface for the RPC server endpoint that provides GoneVertical
 * information for clients that will be calling aysychronously. 
 */
public interface MySQLConnServiceAsync {

	/**
	 * get bible info 
	 * 
	 * @param callback
	 */
	public void getBibleInfo(AsyncCallback callback);
	
	
	
}
