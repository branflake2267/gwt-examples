package org.gonevertical.dmx.test;

public class String2Byte {

  public static void main(String[] args) {
    new String2Byte().run(); 
  }
  
  private void run() {
    
    
    int s = 258 & 0xff;
    
    int i = 0;
    
    String h = Integer.toHexString(i);
    
    byte b = (byte) (0) & 0xFF;
    
    System.out.println("int: " + h);
    
    byte[] bytes = new byte[1];
    bytes[0] = b;
    

    
    debugBytes(bytes);
    
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
