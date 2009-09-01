package com.gawkat.core.server.jdo;

import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.oauth.Sha1;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingJdo;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

public class SetDefaults {

  private ServerPersistence sp = null;
  
  // what to set on using this class
  public static int THINGTYPES = 1;
  public static int THINGAPP = 2;
  public static int THINGUSER = 3;  

  
  public SetDefaults(ServerPersistence sp) {
    this.sp = sp;
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
    
    ServerPersistence sp = new ServerPersistence();
    
    ThingTypeData at = new ThingTypeData();
    at.setName("Application");
    
    ThingTypeData bt = new ThingTypeData();
    bt.setName("User");
    
    ThingTypeData ct = new ThingTypeData();
    ct.setName("Group");
    
    ThingTypeData dt = new ThingTypeData();
    dt.setName("Widget");
    
    ThingTypeData et = new ThingTypeData();
    et.setName("Permission");
    
    
    ThingTypeJdo a = new ThingTypeJdo();
    a.setData(at);
    a.insert();
    
    ThingTypeJdo b = new ThingTypeJdo();
    b.setData(bt);
    b.insert();
    
    ThingTypeJdo c = new ThingTypeJdo();
    c.setData(ct);
    c.insert();
    
    ThingTypeJdo d = new ThingTypeJdo();
    d.setData(dt);
    d.insert();
    
    ThingTypeJdo e = new ThingTypeJdo();
    e.setData(et);
    e.insert();
    
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
