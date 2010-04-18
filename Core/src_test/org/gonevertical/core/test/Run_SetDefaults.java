package org.gonevertical.core.test;

import org.gonevertical.core.client.SetDefaultsData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.db.SetDefaults;

public class Run_SetDefaults {

  /**
   * @param args
   */
  public static void main(String[] args) {
   
    ServerPersistence sp = new ServerPersistence();
    
    SetDefaults sd = new SetDefaults(sp);
    sd.setDefaults(SetDefaultsData.DEFAULT_THING_TYPES);
    sd.setDefaults(SetDefaultsData.DEFAULT_THING_APPLICATIONS);
    sd.setDefaults(SetDefaultsData.DEFAULT_THING_USERS);

  }

}
