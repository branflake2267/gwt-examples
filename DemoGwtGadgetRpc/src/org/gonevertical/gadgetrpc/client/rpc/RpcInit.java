package org.gonevertical.gadgetrpc.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gadgets.client.gwtrpc.GadgetsGwtRpc;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class RpcInit {

	public static RpcServiceAsync init() {
	  
	  RpcServiceAsync call = GWT.create(RpcService.class);
    ServiceDefTarget serviceDef = (ServiceDefTarget) call;

    // Uses Gadgets container as proxy for GWT RPC requests
    GadgetsGwtRpc.redirectThroughProxy(serviceDef);
	  
	  // regular gwt
		//RpcServiceAsync call = (RpcServiceAsync) GWT.create(RpcService.class);
	  
		return call;
	}
	
}
