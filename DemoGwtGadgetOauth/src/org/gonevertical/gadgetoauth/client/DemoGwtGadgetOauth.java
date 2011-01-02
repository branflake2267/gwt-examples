package org.gonevertical.gadgetoauth.client;

import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.gadgets.client.NeedsLockedDomain;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

@ModulePrefs(//
    title = "Demo GWT Gadget OAuth", //
    directory_title = "Demo GWT Gadget OAuth", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "http://gwt-examples.googlecode.com")
@com.google.gwt.gadgets.client.Gadget.UseLongManifestName(false)
@com.google.gwt.gadgets.client.Gadget.AllowHtmlQuirksMode(false)
@com.google.gwt.gadgets.client.Gadget.InjectModulePrefs(files="ModulePrefs.txt")
@com.google.gwt.gadgets.client.Gadget.InjectContent(files="Content.txt")
public class DemoGwtGadgetOauth extends Gadget<GadgetPreferences> implements NeedsLockedDomain {

  protected void init(GadgetPreferences preferences) {
    
    HTML html = new HTML("Demo GWT Gadget has loaded. v4");
    
    VerticalPanel pWidget = new VerticalPanel();
    pWidget.add(html);
    
    RootPanel.get().add(pWidget);
  }
  
}

