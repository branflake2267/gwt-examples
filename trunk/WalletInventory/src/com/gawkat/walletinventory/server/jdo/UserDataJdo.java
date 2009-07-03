package com.gawkat.walletinventory.server.jdo;

public class UserDataJdo {
  
  private Long userId;
  
  private String email;
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public Long getUserId() {
    return this.userId;
  }
  
}
