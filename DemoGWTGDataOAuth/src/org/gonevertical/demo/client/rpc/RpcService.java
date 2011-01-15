package org.gonevertical.demo.client.rpc;

import org.gonevertical.demo.client.LoginData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpcService")
public interface RpcService extends RemoteService {
 
  public LoginData getLoginData(String currentUrl);
  
  public boolean getHasToken();
  
  public String getBlogList();
  
}
