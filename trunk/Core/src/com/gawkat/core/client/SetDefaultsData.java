package com.gawkat.core.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SetDefaultsData implements IsSerializable {

	/**
	 * default thing types
	 */
	public static final int THINGTYPE_APPLICATION = 1;
	public static final int THINGTYPE_PERSON = 2;
	public static final int THINGTYPE_GROUP = 3;
	public static final int THINGTYPE_WIDGET = 4;
	public static final int THINGTYPE_PERMISSION = 5;
	public static final int THINGTYPE_STUFFTEMPLATE = 6;
	
	/**
	 * default thing stuff types
	 */
	public static final int THINGSTUFFTYPE_ADMIN = 1;
	public static final int THINGSTUFFTYPE_LINK = 2;
	
	/**
	 * default things
	 */
	public static final int THING_APPLICATION = 1;
	public static final int THING_ADMINISTRATOR = 2;
	public static final int THING_DEMOUSER = 3;
  
	/**
	 * set defaults up choices
	 */
	public static final int DEFAULT_ALL = 1;
	public static final int DEFAULT_THINGS = 2;
  public static final int DEFAULT_THING_TYPES = 3;
  public static final int DEFAULT_THING_APPLICATIONS = 4;
  public static final int DEFAULT_THING_USERS = 5;  
  public static final int DEFAULT_THING_STUFF_TYPES = 6;
  
}
