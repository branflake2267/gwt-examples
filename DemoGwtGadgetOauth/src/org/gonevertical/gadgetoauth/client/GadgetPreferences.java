package org.gonevertical.gadgetoauth.client;

import com.google.gwt.gadgets.client.BooleanPreference;
import com.google.gwt.gadgets.client.UserPreferences;

public interface GadgetPreferences extends UserPreferences {

  @PreferenceAttributes(display_name = "Boolean_Work", default_value = "false")
  BooleanPreference bolWork();
	
  
}
