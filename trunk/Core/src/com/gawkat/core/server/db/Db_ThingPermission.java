package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;

public class Db_ThingPermission {

  private ServerPersistence sp = new ServerPersistence();
  
  public Db_ThingPermission(ServerPersistence sp) {
    this.sp = sp;
  }
 
 
}
