package org.gonevertical.loc.server.dmx;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * serial communications to the dmx serial port
 * 
 * @author branflake2267
 *
 */
public class SerialConn {

  // reference to serial port object
  private SerialPort serialPort = null;
  
  // port to be used like /dev/ttyUSB0
  private String dev = null;

  private boolean closeOnWrite;
  
  public SerialConn() {
    
    // usb port that I will be writing to
    dev = "/dev/ttyUSB0";
  }
  
  /**
   * what serial port will we be using
   *   ref: /dev/serial/by-id
   *   ref: /dev/tty* possibly
   * @param dev
   */
  public void setPort(String dev) {
    this.dev = dev;
  }
  
  public SerialPort getSerialPort() {
    open();
    return serialPort;
  }
  
  /**
   * open the serial port, get it reading for writing
   * 
   * @param dev
   * @throws Exception
   */
  public void open() {
    dev = "/dev/ttyUSB0";
	  
    //System.out.println("SerialConn.open(): Trying to open: " + dev);
    
    CommPortIdentifier portIdentifier = null;
    try {
      portIdentifier = CommPortIdentifier.getPortIdentifier(dev);
    } catch (NoSuchPortException e) {
      e.printStackTrace();
    }
    
    if (portIdentifier.isCurrentlyOwned()) {
      //System.out.println("Error: open(): Port is currently in use. Maybe its stale, and not really in use.");
      return;
    } 
    
    //System.out.println("open identify:");
    CommPort commPort = null;
    try {
      commPort = portIdentifier.open(this.getClass().getName(), 100);
    } catch (PortInUseException e) {
      e.printStackTrace();
    }

    //System.out.println("identified and opened:");
    
    if (commPort instanceof SerialPort) {
      serialPort = (SerialPort) commPort;
      try {
        serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      } catch (UnsupportedCommOperationException e) {
        e.printStackTrace();
      }

    } else {
      //System.out.println("Error: Only serial ports are handled by this example.");
    }
    
    //System.out.println("SerialConn.open(): opened? " + commPort.getName());
      
  }
   
  /**
   * write message to the serial port
   * 
   * @param bytes
   */
  public void write(byte[] bytes) {
    
    // send debug output to the console
    //EncodeByteToHex.debugBytes(bytes);
    
    //if (serialPort == null) {
      //System.out.println("trying to open");
      try {
        open();
      } catch (Exception e) {
        //System.out.println("Error: write(): Something went wrong.");
        e.printStackTrace();
      }
    //}
    
    OutputStream out = null;
    //if (serialPort != null) {
    try {
      out = serialPort.getOutputStream();
      out.write(bytes);
    } catch (IOException e) {
      e.printStackTrace();
      //System.out.println("SerialConn.write() SerialPort getoutputStreamError");
    } 
    //} else {
      //System.out.println("serialPort skip");
    //}
    
    if (closeOnWrite == true) {
      //System.out.println("closing port");
      close();  
    }
  }
  
  /**
   * close the port
   */
  public void close() {
    if (serialPort != null) {
      serialPort.close();
    }
  }
  
  public void closeOnWrite(boolean b) {
    this.closeOnWrite = b;
  }
  
  
}
