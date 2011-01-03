package org.gonevertical.gadgetrpc.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.gonevertical.gadgetrpc.client.layout.LoginData;
import org.gonevertical.gadgetrpc.client.rpc.RpcService;
import org.gonevertical.gadgetrpc.server.login.Google_Login;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPCServletUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  private static final Logger log = Logger.getLogger(RpcServiceImpl.class.getName());
  
  @Override
  protected void onBeforeRequestDeserialized(String serializedRequest) {
    System.out.println("work????: " + serializedRequest);
    log.warning("warn work" + serializedRequest);
  }
  
  public LoginData getLoginData(String currentUrl) {
    Google_Login gl = new Google_Login();
    return gl.getLoginData(currentUrl);
  }
  
  /**
   * Do not validate HTTP headers - iGoogle munges them.
   *  application/x-www-form-urlencoded
   */
  @Override
  public String readContent(HttpServletRequest request) throws ServletException, IOException {
    String s = RPCServletUtils.readContent(request, "application/x-www-form-urlencoded", null);
    return s;
  }
  
  @Override
  protected void checkPermutationStrongName() throws SecurityException {
   // do nothing - skip this for now
  }
  
}
