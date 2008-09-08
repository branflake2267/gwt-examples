package com.tribling.gwt.test.mysqlconn.client;


import com.google.gwt.user.client.rpc.RemoteService;

public interface MySQLConnService extends RemoteService {
	
	/**
	 * get bible info
	 * 
	 * @return
	 */
	public BibleData[] getBibleInfo();

}
