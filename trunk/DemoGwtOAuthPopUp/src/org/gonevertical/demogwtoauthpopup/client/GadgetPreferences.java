package org.gonevertical.demogwtoauthpopup.client;

import com.google.gwt.gadgets.client.BooleanPreference;
import com.google.gwt.gadgets.client.UserPreferences;

public interface GadgetPreferences extends UserPreferences {

  @PreferenceAttributes(display_name = "BooleanWork", default_value = "false")
  BooleanPreference bolWork();
	

}
