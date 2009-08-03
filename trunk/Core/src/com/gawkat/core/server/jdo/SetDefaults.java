package com.gawkat.core.server.jdo;

import com.gawkat.core.server.jdo.data.ThingJdo;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

public class SetDefaults {

  public static int THINGTYPES = 1;
  public static int THINGAPP = 2;
  public static int THINGUSER = 3;  

  
  public SetDefaults() {
  }
  
  public boolean setDefaults(int defaultType) {
    
    if (defaultType == SetDefaults.THINGTYPES) {
      setThingTypes();
    } else if (defaultType == SetDefaults.THINGAPP) {
      setApplication();
    } else if (defaultType == SetDefaults.THINGUSER) {
      setUser();
    }
    
    
    return true;
  }
  
  /**
   * set default things
   */
  private void setThingTypes() {
    
    ThingTypeJdo a = new ThingTypeJdo();
    a.insert("Application");
    
    ThingTypeJdo b = new ThingTypeJdo();
    b.insert("User");
    
    ThingTypeJdo c = new ThingTypeJdo();
    c.insert("Group");
    
  }
  
  /**
   * set default application
   */
  private void setApplication() {
    
    long thingTypeId = 1;
    String key = "App_Test";
    String secret = "";
    
    ThingJdo a = new ThingJdo();
    a.insert(thingTypeId, key, secret);
    
  }
  
  /**
   * set a default user for testing
   */
  private void setUser() {
    
  }
  
}
