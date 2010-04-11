package org.gonevertical.loc.client.rpc;

import org.gonevertical.loc.client.ChannelData;
import org.gonevertical.loc.client.TrackData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpcservice")
public interface RpcService extends RemoteService {
  
  public String greetServer(String name);
  
  public boolean runTest();
  
  public boolean sendChannelData(ChannelData cd);
  
  public boolean track(TrackData td);
  
}
