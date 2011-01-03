package org.gonevertical.gadgetrpc.server;

import org.gonevertical.gadgetrpc.client.layout.LoginData;
import org.gonevertical.gadgetrpc.client.rpc.RpcService;
import org.gonevertical.gadgetrpc.server.login.Google_Login;

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
