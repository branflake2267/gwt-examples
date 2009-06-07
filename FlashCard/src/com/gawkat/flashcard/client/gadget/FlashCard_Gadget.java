package com.gawkat.flashcard.client.gadget;

import com.gawkat.flashcard.client.Navigation;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gadgets.client.Gadget;
import com.google.gwt.gadgets.client.UserPreferences;
import com.google.gwt.gadgets.client.Gadget.ModulePrefs;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

@ModulePrefs(//
    title = "Math Flash Card", //
    directory_title = "Math Flash Card - Keep your brain trained.", //
    description="Practice your your times tables.", //
    author = "Brandon Donnelson", //
    author_email = "branflake2267@gmail.com", //
    author_affiliation = "GoneVertical.com", //
    author_location="Arlington, WA, 98223", //
    thumbnail = "/images/thumbnail.png", //
    screenshot = "/images/screenshot.png")
public class FlashCard_Gadget extends Gadget<UserPreferences> implements MyGadgetFeatures {
  
  // TODO - gadget preferences doesn't set up artifact needed for compile
  
  public void init(UserPreferences preferences) {
    
    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    RootPanel.get().add(vp);
    
    Navigation nav = new Navigation();
    vp.add(nav);
    nav.start();
    
    vp.setCellHorizontalAlignment(nav, HorizontalPanel.ALIGN_CENTER);
    
    // TODO - this work??
    onModuleLoad();
  }


 

  


}
