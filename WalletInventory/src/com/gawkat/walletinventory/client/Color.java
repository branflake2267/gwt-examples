package com.gawkat.walletinventory.client;

import com.google.gwt.user.client.Random;

public class Color {

  public Color() {
    
  }

  /**
   * generate a random hex color
   * 
   * @return
   */
  public static String getRandomColor() {

    String hex1 = getRandomHex();
    String hex2 = getRandomHex();
    String hex3 = getRandomHex();
    String hex4 = getRandomHex();
    String hex5 = getRandomHex();
    String hex6 = getRandomHex();

    String color = "#" + hex1 + hex2 + hex3 + hex4 + hex5 + hex6;

    return color;
  }

  /**
   * get random hex
   * 
   * @return int
   */
  private static String getRandomHex() {
    String[] hex = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "A", "B", "C", "D", "E", "F" };
    int randomNum = Random.nextInt(hex.length);
    String sHex = hex[randomNum];
    return sHex;
  }

  
}
