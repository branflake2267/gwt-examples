package org.gonevertical.demo.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.gadgets.client.DynamicHeightFeature;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.NeedsDynamicHeight;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

@ModulePrefs(//
    title = "Timestamp Converter", //
    directory_title = "Timestamp Converter", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "GoneVerical.org", //
    description = "Test or convert your Javascript or Unix epoch timestamp. See more on http://gwt-examples.googlecode.com", //
    title_url = "http://gwt-examples.googlecode.com", //
    author_location = "Arlington, WA, 98223", //
    thumbnail = "/images/thumb.png", //
    screenshot = "/images/screen.png")
public class DemoGwtDateTime_Gadget extends Gadget<DemoGadgetPreferences> implements NeedsDynamicHeight, ChangeHandler {

	private DynamicHeightFeature featureHeight;

	/**
	 * entry point for gadget
	 */
  protected void init(DemoGadgetPreferences preferences) {
	  
  	DateTimeWidget wdt = new DateTimeWidget();
  	
	  RootPanel.get().add(wdt);
	  
	  wdt.addChangeHandler(this);
  	
	  Track.track("gadgetHome");
	  
  }

  public void initializeFeature(DynamicHeightFeature feature) {
  	this.featureHeight = feature;
  	feature.adjustHeight();
  }

  public void onChange(ChangeEvent event) {
  	Widget sender = (Widget) event.getSource();
  	
  	// adjust height
  	featureHeight.adjustHeight();
  	
  }
	
}

