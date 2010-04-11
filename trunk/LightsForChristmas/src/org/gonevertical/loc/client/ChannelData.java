package org.gonevertical.loc.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChannelData implements IsSerializable {

  /**
   * dmx channels
   * 
   * TODO was set at 512
   * 
   */
  private Integer[] channels = new Integer[250];
  
  /**
   * constructor - init array
   */
  public ChannelData() {
    for(int i=0; i < channels.length; i++) {
      channels[i] = 0;
    }
  }
  
  /**
   * constructor - set array
   * 
   * @param channels
   */
  public ChannelData(Integer[] channels) {
    this.channels = channels;
  }
  
  /**
   * get channels
   * 
   * @return
   */
  public Integer[] getChannels() {
    return channels;
  }
  
  public void setChannel(int index, int value) {
    this.channels[index] = value;
  }
  
  public void setChannel(boolean[] b) {
    for (int i=1; i < b.length+1; i++) {
      int o = 0;
      if (b[i-1] == true) {
        o = 255;
      } 
      channels[i] = o; 
    } 
  }

  
  
}
