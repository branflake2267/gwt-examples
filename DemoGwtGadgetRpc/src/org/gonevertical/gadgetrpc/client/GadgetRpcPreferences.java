package org.gonevertical.gadgetrpc.client;

import com.google.gwt.gadgets.client.BooleanPreference;
import com.google.gwt.gadgets.client.UserPreferences;
import com.google.gwt.gadgets.client.UserPreferences.PreferenceAttributes;

public interface GadgetRpcPreferences extends UserPreferences {

  @PreferenceAttributes(display_name = "BooleanWork", default_value = "false")
  BooleanPreference bolWork();
  

}
