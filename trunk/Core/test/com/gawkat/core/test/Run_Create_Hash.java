package com.gawkat.core.test;

import com.gawkat.core.client.oauth.Sha1;

public class Run_Create_Hash {

  public static void main(String[] args) {
    
    Sha1 sha = new Sha1();
    
    String key = "salt";
    String data = "password";
    String hash = sha.hex_hmac_sha1(key, data);
    
    System.out.println("hash: " + hash);
    
  }
  
}
