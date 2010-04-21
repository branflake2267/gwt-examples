package org.gonevertical.demo.server;

import java.util.EmptyStackException;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.gonevertical.demo.client.CallData;
import org.gonevertical.demo.client.RpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	private Logger logger = Logger.getLogger(RpcServiceImpl.class);
	
	public CallData callServer(CallData callData) {
		
		if (callData.type == 1) {
			callData.note = "from server";
			logger.info("test to server");
			
		} else if (callData.type == 2) {
			
  		testMethod();
  		
		}
		
		
		return callData;
	}

	private void testMethod() {
	  
		try {
	    throw new EmptyStackException();
    } catch (Exception e) {
	    logger.warn("Exception Thrown", e);
    }
	  
  }

}
