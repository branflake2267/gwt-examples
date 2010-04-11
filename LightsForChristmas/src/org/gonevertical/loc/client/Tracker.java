package org.gonevertical.loc.client;

import org.gonevertical.loc.client.rpc.Rpc;
import org.gonevertical.loc.client.rpc.RpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class Tracker {

  private RpcServiceAsync rpc;

  public Tracker() {
    rpc = Rpc.initRpc();
  }
  
  public void setTrack(int type) {
    TrackData td = new TrackData();
    td.type = type;
    setTrackRcp(td);
  }
  
  public void setTrackRcp(TrackData td) {
    rpc.track(td, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean result) {
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }
  
}
