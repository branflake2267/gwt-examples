package org.gonevertical.gadgetrpc.client.rpc;

import org.gonevertical.gadgetrpc.client.layout.LoginData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpcService")
public interface RpcService extends RemoteService {
 
  public LoginData getLoginData(String currentUrl);
  
}
