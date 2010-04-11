package org.gonevertical.loc.server;

import org.gonevertical.loc.client.ChannelData;
import org.gonevertical.loc.client.TrackData;
import org.gonevertical.loc.client.rpc.RpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  public String greetServer(String input) {
    return "";
  }
  
  public boolean runTest() {
    DMX_Server d = new DMX_Server();
    d.runTest();
    return false;
  }
  
  public boolean sendChannelData(ChannelData cd) {
    try {
      DMX_Server d = new DMX_Server();
      d.setChannelData(cd);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
  
  public boolean track(TrackData td) {

    new DB_Track().track(td);
    
    return true;
  }
}
