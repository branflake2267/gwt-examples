package org.gonevertical.loc.client.digit;

public class DigitDef {


  /**
     _
    |_|
    |_|
   
     0_
    1|_|4 5(mid)
    2|_|6
      3
   
   */
  
  
  public int[] getDef(String s) {
  
    int[] d = new int[7];
    
    s = s.toLowerCase();
    
    if (s.equals("0") == true) {
      d[0] = 1;
      d[1] = 1;
      d[2] = 1;
      d[3] = 1;
      d[4] = 1;
      d[5] = 0;
      d[6] = 1;
    } else if (s.equals("1") == true) {
      d[0] = 0;
      d[1] = 0;
      d[2] = 0;
      d[3] = 0;
      d[4] = 1;
      d[5] = 0;
      d[6] = 1;
    } else if (s.equals("2") == true) {
      d[0] = 1;
      d[1] = 0;
      d[2] = 1;
      d[3] = 1;
      d[4] = 1;
      d[5] = 1;
      d[6] = 0;
    } else if (s.equals("3") == true) {
      d[0] = 1;
      d[1] = 0;
      d[2] = 0;
      d[3] = 1;
      d[4] = 1;
      d[5] = 1;
      d[6] = 1;
    } else if (s.equals("4") == true) {
      d[0] = 0;
      d[1] = 1;
      d[2] = 0;
      d[3] = 3;
      d[4] = 1;
      d[5] = 1;
      d[6] = 1;
    } else if (s.equals("5") == true) {
      d[0] = 1;
      d[1] = 1;
      d[2] = 0;
      d[3] = 1;
      d[4] = 0;
      d[5] = 1;
      d[6] = 1;
    } else if (s.equals("6") == true) {
      d[0] = 1;
      d[1] = 1;
      d[2] = 1;
      d[3] = 1;
      d[4] = 0;
      d[5] = 1;
      d[6] = 1;
    } else if (s.equals("7") == true) {
      d[0] = 1;
      d[1] = 0;
      d[2] = 0;
      d[3] = 0;
      d[4] = 1;
      d[5] = 0;
      d[6] = 1;
    } else if (s.equals("8") == true) {
      d[0] = 1;
      d[1] = 1;
      d[2] = 1;
      d[3] = 1;
      d[4] = 1;
      d[5] = 1;
      d[6] = 1;
    } else if (s.equals("9") == true) {
      d[0] = 1;
      d[1] = 1;
      d[2] = 0;
      d[3] = 0;
      d[4] = 1;
      d[5] = 1;
      d[6] = 1;
    } else {
      d[0] = 0;
      d[1] = 0;
      d[2] = 0;
      d[3] = 0;
      d[4] = 0;
      d[5] = 0;
      d[6] = 0;
    }
    
    return d;
  } 
}
