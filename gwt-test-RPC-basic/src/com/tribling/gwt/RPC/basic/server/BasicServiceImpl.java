package com.tribling.gwt.RPC.basic.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.RPC.basic.client.BasicService;

/**
 * servlet 
 * @author branflake2267
 *
 */
public class BasicServiceImpl extends RemoteServiceServlet implements BasicService {

	/**
	 * constructor
	 */
	public BasicServiceImpl() {
	}
	
	/**
	 * method to call from client side
	 */
	public String myMethod(String s) {
		return "From Server: You sent us '" + s + "'";
	}

}
