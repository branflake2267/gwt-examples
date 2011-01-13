package org.gonevertical.demo.server;

import org.gonevertical.demo.client.LoginData;
import org.gonevertical.demo.client.rpc.RpcService;
import org.gonevertical.demo.server.jdo.AppTokenJdo;
import org.gonevertical.demo.server.jdo.AppTokenStore;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  public LoginData getLoginData(String currentUrl) {
    Google_Login gl = new Google_Login();
    return gl.getLoginData(currentUrl);
  }
  
  public boolean getHasToken() {
    return AppTokenStore.getHasToken();
  }

}
