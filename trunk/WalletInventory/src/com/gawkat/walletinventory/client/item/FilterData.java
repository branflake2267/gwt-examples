package com.gawkat.walletinventory.client.item;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FilterData implements IsSerializable {

  private int start = 0;
  private int limit = 10;
  
  public void setPaging(int start, int limit) {
    this.start = start;
    this.limit = limit;
  }
  
  public int getStart() {
    return this.start;
  }
  
  public int getLimit() {
    return this.limit;
  }
  
}
