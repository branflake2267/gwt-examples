package com.gawkat.core.server.jdo.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ThingPermission {

  // can't see or write a thing
  public static final int PT_NO_ACCESS = 0;

  // read only
  public static final int PT_READ_ONLY = 1;

  // read / write
  public static final int PT_READ_WRITE = 2;
  
  // write only
  public static final int PT_WRITE_ONLY = 3;
  
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long permissionId;
  
  // thing where talking about
  @Persistent
  private Long thingId;
  
  // has permission to another thing
  @Persistent
  private Long hasPermissionToThingId;
  
  // what type of permission - CONSTANT
  @Persistent
  private int permissionType;
  
  
}
