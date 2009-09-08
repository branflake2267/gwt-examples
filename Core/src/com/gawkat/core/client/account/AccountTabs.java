package com.gawkat.core.client.account;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.account.permission.ThingPermissions;
import com.gawkat.core.client.account.thing.Things;
import com.gawkat.core.client.account.thing.hierarchy.HierarchyView;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypes;
import com.gawkat.core.client.account.thingtype.ThingTypes;
import com.gawkat.core.client.global.QueryString;
import com.gawkat.core.client.global.QueryStringData;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;

public class AccountTabs extends Composite implements BeforeSelectionHandler<Integer>, SelectionHandler<Integer>, ValueChangeHandler<String> {

  private ClientPersistence cp = null;
  
  private TabPanel pWidget = new TabPanel();
  
  // tabed widgets
  private Profile wProfile = null;
  private ThingTypes wTypes = null;
  private Things wThings = null;
  private ThingStuffTypes wStuffTypes = null;
  private HierarchyView wHierarchyView = null;
  
  public AccountTabs(ClientPersistence cp) {
    this.cp = cp;
    
    wProfile = new Profile(cp);
    wTypes = new ThingTypes(cp);
    wStuffTypes = new ThingStuffTypes(cp);
    wThings = new Things(cp);
    wHierarchyView = new HierarchyView(cp);
    
    pWidget.add(wProfile, "My Profile");
    pWidget.add(wTypes, "Thing Types");
    pWidget.add(wStuffTypes, "Thing Stuff Types");
    pWidget.add(wThings, "Things");
    pWidget.add(wHierarchyView, "Hierarchy");

    initWidget(pWidget);
    
    pWidget.setWidth("100%");
    
    pWidget.addBeforeSelectionHandler(this);
    pWidget.addSelectionHandler(this);
    
    initHistory();
  }
  
  private void initHistory() {
    History.addValueChangeHandler(this);
  }
  
  public void draw() {
    
    QueryStringData qsd = QueryString.getQueryStringData();
    
    String ht = qsd.getHistoryToken();
    if (ht.equals("account_Profile") == true) {
      pWidget.selectTab(0);
    } else if (ht.equals("account_Types") == true) {
      pWidget.selectTab(1);
    } else if (ht.equals("account_StuffType") == true) {
      pWidget.selectTab(2);
    } else if (ht.equals("account_Things") == true) {
      pWidget.selectTab(3);
    } else if (ht.equals("account_ThingsHierarchy") == true) {
      pWidget.selectTab(4);
    }
    
  }
  
  private void drawProfile() {
    wProfile.draw();
  }
  
  private void drawTypes() {
    wTypes.draw();
  }
  
  private void drawStuffType() {
    wStuffTypes.draw();
  }
  
  private void drawThings() {
    wThings.draw();
  }
  
  private void drawThingsHierarchy() {
    wHierarchyView.draw();
  }
 
  public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
    
    int tab = event.getItem();
    
    if (tab == 0) {
      drawProfile();
    } else if (tab == 1) {
      drawTypes();
    } else if (tab == 2) {
      drawStuffType();
    } else if (tab == 3) {
      drawThings();
    } else if (tab == 4) {
      drawThingsHierarchy();
    } 
    
    
  }

  public void onSelection(SelectionEvent<Integer> event) {
  
    
    int tab = event.getSelectedItem();
    
    if (tab == 0) {
      History.newItem("account_Profile");
    } else if (tab == 1) {
      History.newItem("account_Types");
    } else if (tab == 2) {
      History.newItem("account_StuffType");
    } else if (tab == 3) {
      History.newItem("account_Things");
    } else if (tab == 4) {
      History.newItem("account_ThingsHierarchy");
    } 
    
  }


  public void onValueChange(ValueChangeEvent<String> event) {
    //draw();
  }
  
  
}
