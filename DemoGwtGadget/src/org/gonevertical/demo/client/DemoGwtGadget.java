package org.gonevertical.demo.client;

import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

@ModulePrefs(//
    title = "Demo GWT Gadget", //
    directory_title = "Testing GWT Gadget", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "GoneVertical.org")
@com.google.gwt.gadgets.client.Gadget.UseLongManifestName(false)
@com.google.gwt.gadgets.client.Gadget.AllowHtmlQuirksMode(false)
public class DemoGwtGadget extends Gadget<DemoGadgetPreferences> {

  protected void init(DemoGadgetPreferences preferences) {
	  
  	HTML html = new HTML("Demo GWT Gadget has loaded.");
  	
  	VerticalPanel pWidget = new VerticalPanel();
	  pWidget.add(html);
	  
  	RootPanel.get().add(pWidget);
  }
	
}
