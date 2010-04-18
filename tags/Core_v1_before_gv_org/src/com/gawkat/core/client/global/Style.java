package com.gawkat.core.client.global;

public class Style {

  public Style() {
  }
  
  /**
   * get alternating row style
   * 
   * @param row
   * @return
   */
  public static String getRowStyle(int row) {
   long l = (long) row;
   return set(l);
  }
  
  /**
   * get alternating row style
   * 
   * @param row
   * @return
   */
  public static String getRowStyle(long row) {
  	return set(row);
  }
  
  private static String set(long row) {
  	 boolean even = row % 2 == 0;
     String style = "";
     if (even == true) {
       style = "core-row-even";
     } else {
       style = "core-row-odd";   
     }
     return style;
  }
  
  /**
   * get row hover style
   * 
   * @return
   */
  public static String getRowStyleHover() {
    return "core-row-hover";
  }
  
  /**
   * get row selected style
   * 
   * @return
   */
  public static String getRowStyleSelected() {
  	return "core-row-selected";
  }
  
}
