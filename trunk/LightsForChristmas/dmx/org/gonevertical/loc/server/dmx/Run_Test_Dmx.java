package org.gonevertical.loc.server.dmx;

import org.gonevertical.loc.client.ChannelData;

public class Run_Test_Dmx {

  private ChannelData cd = new ChannelData();
  
  private DmxControl dmx = null;
  
  public static void main(String[] args) {
    new Run_Test_Dmx().run();
  }
  
  public Run_Test_Dmx() {
    dmx = new DmxControl();
  }
  
  
  public void run() {
    System.out.println("run");
    test1();
    loopTest();
  }
  
  public void write(ChannelData cd) {
    dmx.setData(cd);
  }
  
  private void loopTest() {
    int ch = 1;
    int is = 30;
    for (int i=0; i < 200; i++) {
      
      if (is > 255) {
        is = 255;
      }
      
      cd.setChannel(1, 0);
      cd.setChannel(2, 0);
      cd.setChannel(3, 0);
      cd.setChannel(4, 0);

      cd.setChannel(ch, is);
      
      if (ch == 4) {
        ch = 1;
      } else {
        ch++;
      }
      
      if (is >= 255) {
        is = 30;
      } else {
        is = is + 30;
      }
      
      write(cd);
      
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("index: " + i);
    }
  }
  
  private void test1() {

    cd.setChannel(1, 50);
    cd.setChannel(2, 100);
    cd.setChannel(3, 150);
    cd.setChannel(4, 255);
    write(cd);
    
    
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    cd.setChannel(1, 255);
    cd.setChannel(2, 255);
    cd.setChannel(3, 200);
    cd.setChannel(4, 50);
    write(cd);
    
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    cd.setChannel(1, 50);
    cd.setChannel(2, 80);
    cd.setChannel(3, 255);
    cd.setChannel(4, 100);
    write(cd);
    
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    cd.setChannel(1, 100);
    cd.setChannel(2, 200);
    cd.setChannel(3, 20);
    cd.setChannel(4, 50);
    write(cd);
    
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    cd.setChannel(1, 50);
    cd.setChannel(2, 100);
    cd.setChannel(3, 200);
    cd.setChannel(4, 50);
    write(cd);
    
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
   
    cd.setChannel(1, 0);
    cd.setChannel(2, 0);
    cd.setChannel(3, 0);
    cd.setChannel(4, 0);
    
    write(cd);
    
  }

  public void closeOnWrite(boolean b) {
    dmx.closeOnWrite(b);
  }
  
}
