package com.gawkat.core.server.db;

import com.gawkat.core.client.account.permission.ThingPermissionData;
import com.gawkat.core.client.account.permission.ThingPermissionsData;
import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;

public class Db_ThingPermission {

  private ServerPersistence sp = new ServerPersistence();
  
  public Db_ThingPermission(ServerPersistence sp) {
    this.sp = sp;
  }
 
  /**
   * get thing permissions for
   * 
   * @param accessToken
   * @return
   */
  public ThingPermissionsData getThingPermission(OAuthTokenData accessToken, long thingId) {
    ThingPermissionsData t = new ThingPermissionsData();
    t.permissions = getThingPermissions(accessToken, thingId);
    return t;
  }
  
  /**
   * user is asking permission to use the thing, does he have it? and at what king of permission
   * 
   * @param accessToken
   * @param thingId
   * @return
   */
  private ThingPermissionData[] getThingPermissions(OAuthTokenData accessToken, long thingId) {
    
    // get the user of the session, asking for permission to use this thing
    Db_User u = new Db_User(sp);
    ThingData t = u.getUser(accessToken);
    
    //ThingPermissionJdo[] tpjs = ThingPermissionJdo.query(t.thingId, thingId);
  
    
    return null;
  }
  
}
