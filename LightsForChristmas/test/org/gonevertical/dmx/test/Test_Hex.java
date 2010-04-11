package org.gonevertical.dmx.test;

public class Test_Hex {

  public static void main(String[] args) {
    
    test();
  }
  
  public static String stringToHex(String base)
  {
    StringBuffer buffer = new StringBuffer();
    int intValue;
    for(int x = 0; x < base.length(); x++)
    {
      int cursor = 0;
      intValue = base.charAt(x);
      String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
      for(int i = 0; i < binaryChar.length(); i++)
      {
        if(binaryChar.charAt(i) == '1')
        {
          cursor += 1;
        }
      }
      if((cursor % 2) > 0)
      {
        intValue += 128;
      }
      buffer.append(Integer.toHexString(intValue) + " ");
    }
    return buffer.toString();
  }

  public static void test()
  {
    char som = 0x7E;
    char eom = 0xE7;
    
    char label = 6;
    
    char dataLength = 10;
    
    
    
    String s = Character.toString(som) + Character.toString(label) + "test" + Character.toString(eom);
    System.out.println("before: " + s);
    System.out.println("after: " + stringToHex(s));
    
    System.out.println("label: " + label);
    //System.out.println("dataLen: " + Byte.toString(dataLength));
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
