package org.gonevertical.loc.server.buttons;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class ReadButton implements Runnable {

  private byte cb;
  
  private Robot robot = null;
  
  private int buttonTrack = 0;
  
  public ReadButton() {
  }
  
  public void run() {
    startRobot();
  }
  
  private void startRobot() {
    try {
      robot = new Robot();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }
  
  public void setData(int data) {
    
    // debug
    //System.out.println("button: " + data);
    
    if (data != buttonTrack) {
      doWhat(data);
      buttonTrack = data;
    }
  }
  
  private void doWhat(int data) {
    
    //System.out.println("performing Key Simulate: " + data);
  
    // http://www.mathsisfun.com/flash.php?path=/combinatorics/images/combinations-permutations.swf&w=930&h=504&col=%23FFFFFF&title=Combinations+and+Permutations+Calculator
    // {a,b} {a,c} {a,d} {b,c} {b,d} {c,d} - 2 button pushed combos
    // {a,b,c} {a,b,d} {a,c,d} {b,c,d} - 3 button pushed combos
    // {a,b,c,d} // all 4 pushed
    
    switch (data) {
    // 1 button push
    case 64: // 1- P1L a
      setP1L_a();
      break;
    case 16: // 2- P1R b
      setP1R_b();
      break;
    case 128: // 3- start
      setStart();
      break;
    case 32: // 4- stop
      setStop();
      break;
    case 4: // 5- P2L c
      setP2L_c();
      break;
    case 1: // 6- P2R d
      setP2R_d();
      break;
      
    // 2 button push combos
    case 80: // a,b // both P1L&R pushed
      setP1L_a();
      break;
    case 68: // a,c 
      setP1L_a();
      setP2L_c();
      break;
    case 65: // a,d
      setP1L_a();
      setP2R_d();
      break;
    case 20: // b,c
      setP2R_d();
      setP2L_c();
      break;
    case 17: // b,d
      setP1R_b();
      setP2R_d();
      break;
    case 5: // c,d // both P2L&R pushed
      setP2R_d();
      break;
    
    // 3 button push combos
    // {a,b,c} {a,b,d} {a,c,d} {b,c,d}
    case 84:
      setP1L_a();
      setP2L_c();
      break;
    case 81:
      setP1L_a();
      setP2R_d();
      break;
    case 69:
      setP1L_a();
      setP2R_d();
      break;
    case 21:
      setP1R_b();
      setP2R_d();
      break;
    
    // 4 button push
    // {a,b,c,d}
    case 85:
      setP1L_a();
      setP2R_d();
      break;

    }
    
  }
  
  /**
   * robot char a
   */
  private void setP1L_a() {
    robot.keyPress(KeyEvent.VK_A); 
    robot.keyRelease(KeyEvent.VK_A);
  }
  
  /**
   * robot char d
   */
  private void setP1R_b() {
    robot.keyPress(KeyEvent.VK_D); 
    robot.keyRelease(KeyEvent.VK_D);
  }
  
  /**
   * robot 1
   */
  private void setStart() {
    robot.keyPress(KeyEvent.VK_1); 
    robot.keyRelease(KeyEvent.VK_1);
  }
  
  /**
   * robot 2
   */
  private void setStop() {
    robot.keyPress(KeyEvent.VK_2); 
    robot.keyRelease(KeyEvent.VK_2);
  }
  
  /**
   * robot j
   */
  private void setP2L_c() {
    robot.keyPress(KeyEvent.VK_J); 
    robot.keyRelease(KeyEvent.VK_J);
  }
  
  /**
   * robot l
   */
  private void setP2R_d() {
    robot.keyPress(KeyEvent.VK_L); 
    robot.keyRelease(KeyEvent.VK_L);
  }
  
  public void setByte(byte b) {
    encode(b);
  }
  
  private String encode(byte[] b) {
    StringBuilder s = new StringBuilder(2 * b.length);
    for (int i = 0; i < b.length; i++) {
      int v = b[i] & 0xff;
      s.append((char)Hexhars[v >> 4]);
      s.append((char)Hexhars[v & 0xf]);
    }
    return s.toString();
  }

  private String encode(byte ab) {
    byte[] b = new byte[1];
    b[0] = ab;
    String s = encode(b);
    return s;
  }

  private final byte[] Hexhars = {
      '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'a', 'b',
      'c', 'd', 'e', 'f' 
  };


  
}
