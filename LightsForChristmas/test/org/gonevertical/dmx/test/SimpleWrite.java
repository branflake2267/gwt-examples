package org.gonevertical.dmx.test;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

public class SimpleWrite {
  
  static Enumeration portList;
  static CommPortIdentifier portId;
  
  static SerialPort serialPort;
  static OutputStream outputStream;
  static boolean outputBufferEmptyFlag = false;


  public static void main(String[] args) {
    new SimpleWrite().write();
  }
  
  /**
   * 


python example
def transmit_to_widget(label, data, data_size):
  ser.write(chr(SOM_VALUE))
  ser.write(chr(label))
  ser.write(chr(data_size & 0xFF)) // lsb
  ser.write(chr((data_size >> 8) & 0xFF)) // msb
  for j in range(data_size):
    ser.write(data[j])
  ser.write(chr(EOM_VALUE))
  
  
   */
  private void write() {
    
    char som = 0x7E;
    char eom = 0xE7;
    char label = 6; // 10 serial , 6 send
    
    Integer[] chan = getChannelData();
    
    int data_size = chan.length;
    
    char clsb = (char) (data_size & 0xFF);
    char cmsb = (char) ((data_size >> 8) & 0xFF);
    
    byte lsb = (byte) clsb;
    byte msb = (byte) cmsb;
    
    
    ArrayList<Byte> ab = new ArrayList<Byte>();
    ab.add((byte) som); // start delimter
    ab.add((byte) label); // label - whats happening
    ab.add(lsb); // start byte
    ab.add(msb); // mast
    for (int i=0; i < data_size; i++) {
      ab.add(chan[i].byteValue());
    }
    ab.add((byte) eom);
    
    //Byte[] messageString = new Byte[ab.size()];
    //messageString = ab.toArray(messageString);
    byte[] messageString = new byte[ab.size()];
    for (int i=0; i < ab.size(); i++) {
      messageString[i] = ab.get(i);
    }
    
   
    // debug in hex
    debugBytes(messageString);
    
    
    boolean portFound = false;
    String defaultPort = "/dev/ttyUSB0";

 

    portList = CommPortIdentifier.getPortIdentifiers();

    while (portList.hasMoreElements()) {
      portId = (CommPortIdentifier) portList.nextElement();

      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

        if (portId.getName().equals(defaultPort)) {
          System.out.println("Found port " + defaultPort);

          portFound = true;

          try {
            serialPort = (SerialPort) portId.open("SimpleWrite", 2000);
          } catch (PortInUseException e) {
            System.out.println("Port in use.");
            e.printStackTrace();
            continue;
          }

          try {
            outputStream = serialPort.getOutputStream();
          } catch (IOException e) {
            e.printStackTrace();
          }

          try {
            serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
          } catch (UnsupportedCommOperationException e) {
            e.printStackTrace();
          }

          try {
            serialPort.notifyOnOutputEmpty(true);
          } catch (Exception e) {
            System.out.println("Error setting event notification");
            System.out.println(e.toString());
            e.printStackTrace();
            System.exit(-1);
          }

          System.out.println("Writing \"" + messageString + "\" to " + serialPort.getName());

          try {
            outputStream.write(messageString);
          } catch (IOException e) {
            e.printStackTrace();
          }

          try {
            Thread.sleep(2000); // Be sure data is xferred before closing
          } catch (Exception e) {
            e.printStackTrace();
          }
          serialPort.close();
          System.exit(1);
        }
      }
    }

    if (!portFound) {
      System.out.println("port " + defaultPort + " not found.");
    }
    
    
    
  }
  
  private Integer[] getChannelData() {
    
    int[] a = range();
    int[] b = range();
    
    ArrayList<Integer> ch = new ArrayList<Integer>();
    
    ch.add(0); // intensity
    
    for (int i=0; i < a.length; i++) { // first 256
      ch.add(a[i]);
    }
    
    for (int i=0; i < a.length; i++) { // second 256
      ch.add(a[i]);
    }
    
    Integer ia[] = new Integer[ch.size()];
    ia = ch.toArray(ia);
    
    return ia;
  }
  
  private int[] range() {
    int[] a = new int[256];
    for (int i=0; i < a.length; i++) {
      
      //if (i==0) { // 1
        //a[i] = 255;
      //} else if (i==1) { // 2
        //a[i] = 255;
      //} else if (i==2) { // 3
        //a[i] = 255;
      //} else {
        a[i] = 40;
      //}
    }
    return a;
  }

  
  private void debugBytes(byte[] b) {
    
    String hex = encode(b);
    System.out.println("hex: " + hex);
    
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

  private final byte[] Hexhars = {
      '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'a', 'b',
      'c', 'd', 'e', 'f' 
  };
  
  
  
  
}
