package com.gawkat.core.client.global;

public class Style {

  public Style() {
  }
  
  public static String getRowStyle(int row) {
    boolean even = row % 2 == 0;
    String style = "";
    if (even == true) {
      style = "core-row-even";
    } else {
      style = "core-row-odd";   
    }
    return style;
  }
  
  public static String getRowStyleHover() {
    return "core-row-hover";
  }
  
}
