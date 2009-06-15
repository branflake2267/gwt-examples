package com.gawkat.walletinventory.client.item;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ItemData implements IsSerializable {

  // db id
  private Long id;
  
  // name of item in iventory
  private String name = null;
  
  // note about the item, like phone number
  private String note = null;
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
  public String getName() {
    return name;
  }
  
  public String getNote() {
    return note;
  }

  public Long getId() {
    return id;
  }
}
