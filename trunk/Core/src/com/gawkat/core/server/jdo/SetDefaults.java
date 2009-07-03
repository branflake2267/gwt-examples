package com.gawkat.core.server.jdo;

public class SetDefaults {

  public SetDefaults() {
  }
  
  public void setDefaults() {
    setThingTypes();
  }
  
  public void setThingTypes() {
    
    Thing_TypeJdo thing = new Thing_TypeJdo();
    thing.insert("Application");
    thing.insert("User");
    thing.insert("Group");
    
  }
  

  
}
