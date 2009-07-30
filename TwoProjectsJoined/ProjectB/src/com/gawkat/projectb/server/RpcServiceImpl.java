package com.gawkat.projectb.server;

import com.gawkat.projectb.client.RpcService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements
    RpcService {

  public String greetServer(String input) {
    String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");
    return "Hello, " + input + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent;
  }
}
