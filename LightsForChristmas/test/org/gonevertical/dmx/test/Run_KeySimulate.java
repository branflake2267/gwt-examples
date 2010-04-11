package org.gonevertical.dmx.test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Run_KeySimulate {

  public static void main(String[] args) throws AWTException {

    Robot r = new Robot();

    r.delay(5000); //wait 5 seconds
    r.keyPress(KeyEvent.VK_N); //press 'n'
    r.keyRelease(KeyEvent.VK_N); //release 'n'
    
    
    
  }

  
}
