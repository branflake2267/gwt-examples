package com.gawkat.core.client.admin.thingtype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingTypeData implements IsSerializable {

  private Long id;
  
  private String name;
  
  public void set(Long id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
}
