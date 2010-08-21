package org.gonevertical.gaeloadtest.server;

import org.gonevertical.gaeloadtest.client.RpcService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	public boolean saveTest() {
		TestJdo tj = new TestJdo();
		tj.insert();
		return true;
	}
	
}
