package org.gonevertical.loc.server.buttons;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;

/**
 * This version of the TwoWaySerialComm example makes use of the
 * SerialPortEventListener to avoid polling.
 * 
 */
public class Run_SerialReader implements SerialPortEventListener {
  
  public static void main(String[] args) {
    try {
      (new Run_SerialReader()).connect("/dev/ttyS0");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private ReadButton rbb = new ReadButton();
  private SerialPort serialPort;
  
  public Run_SerialReader() {
    super();
    createThread();
  }
  
  public void connect(String portName) throws Exception {
    
    CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
    if (portIdentifier.isCurrentlyOwned()) {
      System.out.println("Error: Port is currently in use");
    } else {
      CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

      if (commPort instanceof SerialPort) {
        serialPort = (SerialPort) commPort;
        serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
      } else {
        System.out.println("Error: Only serial ports are handled by this example.");
      }
    }
  }

  private void createThread() {
    Thread t = new Thread(rbb);
    t.start();
  }
  
  private void setData() {
    int data;
    
    InputStream in = null;
    try {
      in = serialPort.getInputStream();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    
    try {
      while ((data = in.read()) > -1) {
        rbb.setData(data);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public void serialEvent(SerialPortEvent event) {
    setData();
  }

}