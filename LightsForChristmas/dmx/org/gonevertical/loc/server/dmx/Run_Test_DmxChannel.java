package org.gonevertical.loc.server.dmx;

import org.gonevertical.game.dmx.ChannelData;



public class Run_Test_DmxChannel {

  private ChannelData cd = new ChannelData();
  
  private DmxControl dmx = null;
  
  public static void main(String[] args) {
    new Run_Test_DmxChannel().run();
  }
  
  public Run_Test_DmxChannel() {
    dmx = new DmxControl();
  }
  
  
  public void run() {
    System.out.println("run");
    //testChannel(136);
    loopAllChannels();
  }
  
  public void write(ChannelData cd) {
    dmx.setData(cd);
  }
  
  private void testChannel(int channel) {
    
    //channel++;
    
    cd.setChannel(channel, 0);
    write(cd);
    
    cd.setChannel(channel, 255);
    write(cd);
  }
  
  private void loopAllChannels() {
    
    for (int i=0; i < 240; i++) {
      
      int channel = i + 1;
      
      System.out.println("channel: " + channel);
      
      cd.setChannel(channel, 0);
      write(cd);
      
    }
    
    for (int i=0; i < 240; i++) {
      int channel = i + 1;
      
      System.out.println("channel: " + channel);
      if (channel > 0) {
        //cd.setChannel(channel-1, 0);
      }
      cd.setChannel(channel, 255);
      write(cd);
      //System.out.println("pause");
      if(channel>115) {
        System.out.println("pause");
      }
    }
    
  }
}
