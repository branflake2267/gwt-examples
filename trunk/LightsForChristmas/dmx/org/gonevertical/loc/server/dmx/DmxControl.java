package org.gonevertical.loc.server.dmx;

import java.util.ArrayList;

import org.gonevertical.game.dmx.ChannelData;



public class DmxControl {

  // serial port being used
  private String dev = null;
  
  // channel data being sent from client
  private ChannelData channelData = null;
  
  // serial port connection instance
  private SerialConn serial = new SerialConn();

  private boolean closeOnWrite;
  
  /**
   * constructor
   */
  public DmxControl() {
  }
  
  public void setData(ChannelData channelData) {
	  this.channelData = channelData;
	  
	  writeDataToSerial();
  }
  
  public void closeOnWrite(boolean b) {
    this.closeOnWrite = b;
    serial.closeOnWrite(b);
  }
  
  private void writeDataToSerial() {
    
    char som = 0x7E; // starting delimiter
    char eom = 0xE7; // ending delimiter
    char label = 6; // do this (label)
    
    Integer[] channels = channelData.getChannels();
   
    int data_size = channels.length;
    
    char clsb = (char) (data_size & 0xFF);
    char cmsb = (char) ((data_size >> 8) & 0xFF);
    
    byte lsb = (byte) clsb;
    byte msb = (byte) cmsb;
    
    // setup message to serial
    ArrayList<Byte> ab = new ArrayList<Byte>();
    ab.add((byte) som); // start delimiter
    ab.add((byte) label); // label - whats happening
    ab.add(lsb); // lesser significant byte 
    ab.add(msb); // more significant byte
    //ab.add(Byte.parseByte("0"));
    for (int i=0; i < data_size; i++) {
      ab.add(channels[i].byteValue());
    }
    ab.add((byte) eom);
    
    // change to byte array
    byte[] bytes = new byte[ab.size()];
    for (int i=0; i < ab.size(); i++) {
      bytes[i] = ab.get(i);
    }
    
    serial.write(bytes);
  }
  
}
