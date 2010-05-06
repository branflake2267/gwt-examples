package org.gonevertical.demo.client;

import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

@ModulePrefs(//
    title = "Timestamp Converter", //
    directory_title = "Timestamp Converter", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "GoneVerical.org", //
    description = "Test or convert your Javascript or Unix timestamps.", //
    thumbnail = "/images/thumb.png", //
    screenshot = "/images/screen.png")
public class DemoGwtDateTime_Gadget extends Gadget<DemoGadgetPreferences> {

	/**
	 * entry point for gadget
	 */
  protected void init(DemoGadgetPreferences preferences) {
	  
  	DateTimeWidget w = new DateTimeWidget();
  	
	  RootPanel.get().add(w);
  	
	  Track.track("gadgetHome");
	  
  }
	
}
