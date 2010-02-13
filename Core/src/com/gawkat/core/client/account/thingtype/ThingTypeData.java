package com.gawkat.core.client.account.thingtype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingTypeData implements IsSerializable {

  // required thing types
  public static final int TYPE_APPLICATION = 1;
  public static final int TYPE_USER = 2;
  public static final int TYPE_GROUP = 3;
  
  // to set the defaults use this int
  public static final int DEFAULT_TYPE = 1;
  
  private long id;
  
  private String name;
  
  public void set(long id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public long getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

	public void setKey(int id) {
	  this.id = id;
  }
}
