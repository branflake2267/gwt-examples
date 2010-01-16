package com.gawkat.core.test;

import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.SetDefaults;

public class Run_SetDefaults {

  /**
   * @param args
   */
  public static void main(String[] args) {
   
    ServerPersistence sp = new ServerPersistence();
    
    SetDefaults sd = new SetDefaults(sp);
    sd.setDefaults(SetDefaults.THINGTYPES);
    sd.setDefaults(SetDefaults.THINGAPP);
    sd.setDefaults(SetDefaults.THINGUSER);

  }

}
