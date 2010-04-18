package com.gawkat.core.client.account.permission;


import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingPermissionData implements IsSerializable {

  // can't see or write a thing
  public static final int PNO_ACCESS = 0;

  // read only
  public static final int PREADONLY = 1;

  // read / write
  public static final int PREADWRITE = 2;
  
  // write only
  public static final int PWRITEONLY = 3;
  
  
  private Long permissionId;
  
  // thing where talking about
  private Long thingId;
  
  // has permission to another thing
  private Long hasPermissionToThingId;
  
  // what type of permission - CONSTANT
  private int access = PNO_ACCESS;

  public ThingPermissionData() {	
  }
  
	public long getId() {
	  return permissionId;
  }
	
	public long getThingId() {
		return thingId;
	}
	
	public long getHasPermssionToThingId() {
		return hasPermissionToThingId;
	}
	
	public int getAccess() {
		return access;
	}
  
}
