package com.gawkat.core.client.admin.thingtype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingTypeData implements IsSerializable {

  // required thing types
  public static final int TYPE_APPLICATION = 1;
  public static final int TYPE_USER = 2;
  public static final int TYPE_GROUP = 3;
  
  // to set the defaults use this int
  public static final int DEFAULT_TYPE = 1;
  
  // identity
  private Long id;
  
  // name of thingType
  private String name;
  
  /**
   * set thingType
   * @param id
   * @param name
   */
  public void set(Long id, String name) {
    this.id = id;
    this.name = name;
  }
  
  /**
   * get identity
   * 
   * @return
   */
  public Long getId() {
    return this.id;
  }
  
  /**
   * get thingType name
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }
}
