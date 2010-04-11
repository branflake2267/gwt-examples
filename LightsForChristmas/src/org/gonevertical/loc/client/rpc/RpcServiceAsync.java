package org.gonevertical.loc.client.rpc;

import org.gonevertical.loc.client.ChannelData;
import org.gonevertical.loc.client.TrackData;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {
  
  public void greetServer(String input, AsyncCallback<String> callback);
  
  public void runTest(AsyncCallback<Boolean> callback);
  
  public void sendChannelData(ChannelData cd, AsyncCallback<Boolean> callback);
  
  public void track(TrackData td, AsyncCallback<Boolean> callback);
}
