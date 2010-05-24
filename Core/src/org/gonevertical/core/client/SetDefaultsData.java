package org.gonevertical.core.client;

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
	public static final int THINGSTUFFTYPE_NAME = 1;
	public static final int THINGSTUFFTYPE_LINK = 2;
	public static final int THINGSTUFFTYPE_ADMIN = 3;
	public static final int THINGSTUFFTYPE_CANVIEW = 4; // can view
	public static final int THINGSTUFFTYPE_CANEDIT = 5;// can edit
	public static final int THINGSTUFFTYPE_CANADD = 6; // can add
	public static final int THINGSTUFFTYPE_CANTVIEW = 7; // can't view
	public static final int THINGSTUFFTYPE_CANTEDIT = 8; // can't edit
	public static final int THINGSTUFFTYPE_CANTADD = 9; // can't add
	public static final int THINGSTUFFTYPE_ALIAS = 10; // alias
	public static final int THINGSTUFFTYPE_NAMENICK = 11; // nick name
	public static final int THINGSTUFFTYPE_NAMEFIRST = 12; // first name
	public static final int THINGSTUFFTYPE_NAMEMIDDLE = 13; // middle name
	public static final int THINGSTUFFTYPE_NAMELAST = 14; // last name
	public static final int THINGSTUFFTYPE_EMAIL = 15; // email
	public static final int THINGSTUFFTYPE_MOBILE = 16; // mobile
	public static final int THINGSTUFFTYPE_PHONE = 17; // phone
	public static final int THINGSTUFFTYPE_DESCRIPTION = 18; // description
	
	/**
	 * default things
	 */
	public static final int THING_APPLICATION = 1;
	public static final int THING_ADMINISTRATOR = 2;
	public static final int THING_DEMOUSER = 3;
	public static final int THING_PERMISSION_OPEN = 4;
	public static final int THING_PERMISSION_CLOSED = 5;
	public static final int THING_WIDGET_THINGTYPES = 6;
	public static final int THING_WIDGET_THINGSTUFFTYPES = 7;
	public static final int THING_WIDGET_THINGS = 8;
	public static final int THING_WIDGET_EDITTHING = 9;
  
	/**
	 * set defaults up choices
	 */
	public static final int DEFAULT_ALL = 1;
  
}
