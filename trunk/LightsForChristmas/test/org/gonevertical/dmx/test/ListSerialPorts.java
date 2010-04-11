package org.gonevertical.dmx.test;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

public class ListSerialPorts {

  public static void main(String[] args) {

    listPorts();

  }

  public static void listPorts1() {
    CommPortIdentifier portId;
    Enumeration portList = CommPortIdentifier.getPortIdentifiers();

    System.out.println();
    if (portList.hasMoreElements()) {
      System.out.println("Ports found:");
    } else {
      System.out.println("No ports found!");
    }
    while (portList.hasMoreElements()) {

      portId = (CommPortIdentifier) portList.nextElement();

      System.out.println("-> " + portId.getName());

    }
  }

  static void listPorts() {
    java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
    while (portEnum.hasMoreElements()) {
      CommPortIdentifier portIdentifier = portEnum.nextElement();
      System.out.println(portIdentifier.getName() + " - protName: " + getPortTypeName(portIdentifier.getPortType()));
    }
  }

  static String getPortTypeName(int portType) {
    switch (portType) {
    case CommPortIdentifier.PORT_I2C:
      return "I2C";
    case CommPortIdentifier.PORT_PARALLEL:
      return "Parallel";
    case CommPortIdentifier.PORT_RAW:
      return "Raw";
    case CommPortIdentifier.PORT_RS485:
      return "RS485";
    case CommPortIdentifier.PORT_SERIAL:
      return "Serial";
    default:
      return "unknown type";
    }
  }

}
