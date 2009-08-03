package com.gawkat.core.client;

import com.gawkat.core.client.admin.thingtype.ThingTypes;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Core implements EntryPoint {

  
  public void onModuleLoad() {
    
    //RootPanel.get().add(new HTML("loaded"));
    
    testThingTypes();
  }

  private void testThingTypes() {
    
    ThingTypes w = new ThingTypes();
    w.draw();
    RootPanel.get().add(w);
  }
 



}
