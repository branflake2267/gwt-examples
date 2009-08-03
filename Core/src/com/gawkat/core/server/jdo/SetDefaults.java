package com.gawkat.core.server.jdo;

import com.gawkat.core.client.admin.thingtype.ThingTypeData;
import com.gawkat.core.client.oauth.Sha1;
import com.gawkat.core.server.jdo.data.ThingJdo;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

public class SetDefaults {

  // what to set on using this class
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
    a.insert("Application"); // id:1
    
    ThingTypeJdo b = new ThingTypeJdo();
    b.insert("User"); // id:2
    
    ThingTypeJdo c = new ThingTypeJdo();
    c.insert("Group"); // id:3
    
  }
  
  /**
   * set default application
   */
  private void setApplication() {
    
    Sha1 sha = new Sha1();
    
    long thingTypeId = ThingTypeData.TYPE_APPLICATION;
    String key = "demo_application";
    String secret = sha.hex_hmac_sha1("salt", "password");
    
    ThingJdo a = new ThingJdo();
    a.insert(thingTypeId, key, secret);
    
  }
  
  /**
   * set a default user for testing
   */
  private void setUser() {
    Sha1 sha = new Sha1();
    
    long thingTypeId = ThingTypeData.TYPE_USER;
    String key = "demo_user";
    String secret = sha.hex_hmac_sha1("salt", "password");
    
    ThingJdo a = new ThingJdo();
    a.insert(thingTypeId, key, secret);
  }
  
}
