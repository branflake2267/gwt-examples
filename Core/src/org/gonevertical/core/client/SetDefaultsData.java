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
	// can view
	// can edit
	// can add
	// can't view
	// can't edit
	// can't add
	// alias
	// nick name
	// first name
	// middle name
	// last name
	// email
	// mobile
	// phone
	// description
	
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
