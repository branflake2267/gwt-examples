package org.gonevertical.demo.client;

import com.google.gwt.gadgets.client.AdsUserPreferences;
import com.google.gwt.gadgets.client.BooleanPreference;
import com.google.gwt.gadgets.client.StringPreference;
import com.google.gwt.gadgets.client.UserPreferences;
import com.google.gwt.gadgets.client.UserPreferences.PreferenceAttributes;
import com.google.gwt.gadgets.client.UserPreferences.PreferenceAttributes.Options;
import com.google.gwt.visualization.client.visualizations.Visualization;



public interface SpreadsheetGadgetPreferences extends UserPreferences {

  @PreferenceAttributes(display_name = "ThisWorkBolPref", default_value = "false")
  BooleanPreference workBolPref();
  
  //<UserPref name="_table_query_url" display_name="Data source url" required="true"/>
  @PreferenceAttributes(display_name = "Select Address Range")
  StringPreference _table_query_url();
  
  
}
