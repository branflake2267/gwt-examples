package com.gawkat.jdo.server;

public class Run_Test {

  public static void main(String[] args) {
    try {
      TestJdo o = new TestJdo();
      o.test("hello there");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
