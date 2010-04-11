package org.gonevertical.loc.server;

import org.gonevertical.loc.client.ChannelData;
import org.gonevertical.loc.server.dmx.DmxControl;
import org.gonevertical.loc.server.dmx.Run_Test_Dmx;

public class DMX_Server {

  public DMX_Server() {
    
  }
  
  public void setChannelData(ChannelData cd) {
    DmxControl dmx = new DmxControl(); 
    dmx.closeOnWrite(true);
    dmx.setData(cd);
  }
  
  public boolean runTest() {
    
    System.out.println("test run");
    
    Run_Test_Dmx t = new Run_Test_Dmx();
    t.closeOnWrite(true);
    t.run();
    
    return false;
  }
}
