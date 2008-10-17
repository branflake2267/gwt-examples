package com.tribling.gwt.test.oauth.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;

public interface RpcService extends RemoteService {

	/**
	 * example rpc request
	 * @param s
	 * @return
	 */
	public String testMethod(String s);

}
