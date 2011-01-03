package org.gonevertical.gadgetrpc.client;

import org.gonevertical.gadgetrpc.client.layout.Home;

import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.gadgets.client.NeedsLockedDomain;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

@ModulePrefs(//
    title = "Demo GWT Gadget Rpc", //
    directory_title = "Demo GWT Gadget Rpc", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "http://gwt-examples.googlecode.com")
@com.google.gwt.gadgets.client.Gadget.UseLongManifestName(false)
@com.google.gwt.gadgets.client.Gadget.AllowHtmlQuirksMode(false)
public class GadgetRpc extends Gadget<GadgetRpcPreferences> implements NeedsLockedDomain {

  protected void init(GadgetRpcPreferences preferences) {
    
    HTML html = new HTML("Demo GWT Gadget has loaded. v6");
    
    Home home = new Home();
    home.setWidth("600px");
    
    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    vp.add(home);
    vp.setCellHorizontalAlignment(home, HorizontalPanel.ALIGN_CENTER);
    vp.add(new HTML("&nbsp;"));
    vp.add(html);
    
    RootPanel.get().add(vp);
    
    
   
    

  }
  
}
