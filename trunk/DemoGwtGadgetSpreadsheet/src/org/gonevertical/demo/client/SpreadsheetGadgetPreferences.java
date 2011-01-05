package org.gonevertical.demo.client;

import com.google.gwt.gadgets.client.BooleanPreference;
import com.google.gwt.gadgets.client.StringPreference;
import com.google.gwt.gadgets.client.UserPreferences;



public interface SpreadsheetGadgetPreferences extends UserPreferences {

  @PreferenceAttributes(display_name = "ThisWorkBolPref", default_value = "false")
  BooleanPreference workBolPref();
  
  //<UserPref name="_table_query_url" display_name="Data source url" required="true"/>
  @PreferenceAttributes(display_name = "Select Address Range")
  StringPreference _table_query_url();
  
  
}
