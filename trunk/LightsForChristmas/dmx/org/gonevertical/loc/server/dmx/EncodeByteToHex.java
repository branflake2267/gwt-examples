package org.gonevertical.loc.server.dmx;

public class EncodeByteToHex {

  public EncodeByteToHex() {
    
  }
  
  public static void debugBytes(byte[] b) {
    
    String hex = encode(b);
    System.out.println("hex: " + hex);
    
  }
  
  private static String encode(byte[] b) {

    StringBuilder s = new StringBuilder(2 * b.length);

    for (int i = 0; i < b.length; i++) {

      int v = b[i] & 0xff;

      s.append((char)Hexhars[v >> 4]);
      s.append((char)Hexhars[v & 0xf]);
    }

    return s.toString();
  }

  private static final byte[] Hexhars = {
      '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'a', 'b',
      'c', 'd', 'e', 'f' 
  };
  
  
}
