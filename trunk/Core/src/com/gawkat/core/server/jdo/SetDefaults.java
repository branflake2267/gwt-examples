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
      createApplication();
    } else if (defaultType == SetDefaults.THINGUSER) {
      createUser();
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
    
    ThingTypeJdo d = new ThingTypeJdo();
    d.insert("Widget"); // id:4
    
  }
  
  /**
   * set default application
   */
  private void createApplication() {
    
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
  private void createUser() {
    Sha1 sha = new Sha1();
    
    long thingTypeId = ThingTypeData.TYPE_USER;
    String key = "demo_user";
    String password = "password";
    String secret = sha.b64_sha1(password);
    //String secret = sha.hex_hmac_sha1("salt", "password"); // would need to do this on the client side too.
    
    ThingJdo a = new ThingJdo();
    a.insert(thingTypeId, key, secret);
  }
  
}
