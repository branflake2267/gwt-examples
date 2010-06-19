package org.gonevertical.core.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SetDefaultsData implements IsSerializable {

	/**
	 * default thing types
	 */
	@Deprecated
	public static final int THINGTYPE_APPLICATION = 1;
	@Deprecated
	public static final int THINGTYPE_PERSON = 2;
	@Deprecated
	public static final int THINGTYPE_GROUP = 3;
	@Deprecated
	public static final int THINGTYPE_WIDGET = 4;
	@Deprecated
	public static final int THINGTYPE_PERMISSION = 5;
	@Deprecated
	public static final int THINGTYPE_STUFFTEMPLATE = 6;
	
	/**
	 * default thing stuff types
	 */
	@Deprecated
	public static final int THINGSTUFFTYPE_NAME = 1;
	@Deprecated
	public static final int THINGSTUFFTYPE_LINK = 2;
	@Deprecated
	public static final int THINGSTUFFTYPE_ADMIN = 3;
	@Deprecated
	public static final int THINGSTUFFTYPE_CANVIEW = 4; // can view
	@Deprecated
	public static final int THINGSTUFFTYPE_CANEDIT = 5;// can edit
	@Deprecated
	public static final int THINGSTUFFTYPE_CANADD = 6; // can add
	@Deprecated
	public static final int THINGSTUFFTYPE_CANTVIEW = 7; // can't view
	@Deprecated
	public static final int THINGSTUFFTYPE_CANTEDIT = 8; // can't edit
	@Deprecated
	public static final int THINGSTUFFTYPE_CANTADD = 9; // can't add
	@Deprecated
	public static final int THINGSTUFFTYPE_ALIAS = 10; // alias
	@Deprecated
	public static final int THINGSTUFFTYPE_NAMENICK = 11; // nick name
	@Deprecated
	public static final int THINGSTUFFTYPE_NAMEFIRST = 12; // first name
	@Deprecated
	public static final int THINGSTUFFTYPE_NAMEMIDDLE = 13; // middle name
	@Deprecated
	public static final int THINGSTUFFTYPE_NAMELAST = 14; // last name
	@Deprecated
	public static final int THINGSTUFFTYPE_EMAIL = 15; // email
	@Deprecated
	public static final int THINGSTUFFTYPE_MOBILE = 16; // mobile
	@Deprecated
	public static final int THINGSTUFFTYPE_PHONE = 17; // phone
	@Deprecated
	public static final int THINGSTUFFTYPE_DESCRIPTION = 18; // description
	
	/**
	 * default things
	 */
	@Deprecated
	public static final int THING_APPLICATION = 1;
	@Deprecated
	public static final int THING_ADMINISTRATOR = 2;
	@Deprecated
	public static final int THING_DEMOUSER = 3;
	@Deprecated
	public static final int THING_PERMISSION_OPEN = 4;
	@Deprecated
	public static final int THING_PERMISSION_CLOSED = 5;
	@Deprecated
	public static final int THING_WIDGET_THINGTYPES = 6;
	@Deprecated
	public static final int THING_WIDGET_THINGSTUFFTYPES = 7;
	@Deprecated
	public static final int THING_WIDGET_THINGS = 8;
	@Deprecated
	public static final int THING_WIDGET_EDITTHING = 9;
	
  
	/**
	 * set defaults up choices
	 */
	public static final int DEFAULT_ALL = 1;
  
}
