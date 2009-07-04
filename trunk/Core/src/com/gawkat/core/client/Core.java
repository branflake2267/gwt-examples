package com.gawkat.core.client;

import java.util.HashMap;

import com.gawkat.core.client.account.AccountManagementNavigation;
import com.gawkat.core.client.admin.thingtype.ThingTypes;
import com.gawkat.core.client.global.QueryString;
import com.gawkat.core.client.global.QueryStringData;
import com.gawkat.core.client.ui.LoginUi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

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
