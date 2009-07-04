package com.gawkat.core.server.jdo;

public class SetDefaults {

  public static int THINGTYPES = 1;
  
  public SetDefaults() {
  }
  
  public boolean setDefaults(int defaultType) {
    
    if (defaultType == SetDefaults.THINGTYPES) {
      setThingTypes();
    }
    
    return true;
  }
  
  public void setThingTypes() {
    
    Thing_TypeJdo a = new Thing_TypeJdo();
    a.insert("Application");
    
    Thing_TypeJdo b = new Thing_TypeJdo();
    b.insert("User");
    
    Thing_TypeJdo c = new Thing_TypeJdo();
    c.insert("Group");
    
  }
  

  
}
