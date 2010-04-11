package org.gonevertical.dmx.test;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

public class WriteToSerial {

  private Enumeration portList;
  private CommPortIdentifier portId;
  
  private SerialPort serialPort;
  private OutputStream outputStream;
  private boolean outputBufferEmptyFlag = false;

  
  /**
   * constructor
   */
  public WriteToSerial() {
  }
  
  /**
   * 
   * 
    python example
    def transmit_to_widget(label, data, data_size):
      ser.write(chr(SOM_VALUE))
      ser.write(chr(label))
      ser.write(chr(data_size & 0xFF))
      ser.write(chr((data_size >> 8) & 0xFF))
      for j in range(data_size):
        ser.write(data[j])
      ser.write(chr(EOM_VALUE))
  
   * @param label
   * @param messageString
   * @param data_size
   */
  private void writeToSerial(int label, byte[] messageString, int data_size) {
    
   
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
    
    System.out.println("the end.");
  }
  
}
