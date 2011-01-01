package org.gonevertical.gdata.server;

import org.gonevertical.gdata.client.login.LoginData;
import org.gonevertical.gdata.client.rpc.RpcService;
import org.gonevertical.gdata.server.login.Google_Login;

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
  
  
  
}
