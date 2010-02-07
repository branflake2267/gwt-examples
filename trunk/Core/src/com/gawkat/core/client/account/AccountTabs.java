package com.gawkat.core.client.account;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.account.thing.Things;
import com.gawkat.core.client.account.thing.ownership.ThingLink;
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
  private ThingTypes wTypes = null;
  private ThingStuffTypes wStuffTypes = null;
  private Things wThings = null;
  
  public AccountTabs(ClientPersistence cp) {
    this.cp = cp;
    
    wTypes = new ThingTypes(cp);
    wStuffTypes = new ThingStuffTypes(cp);
    wThings = new Things(cp);
    
    pWidget.add(wTypes, "Thing Types");
    pWidget.add(wStuffTypes, "Stuff Types");
    pWidget.add(wThings, "Things");

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
    if (ht.equals("account_Types") == true) {
      pWidget.selectTab(0);
      
    } else if (ht.equals("account_StuffType") == true) {
      pWidget.selectTab(1);
      
    } else if (ht.equals("account_Things") == true) {
      pWidget.selectTab(2);
    }
    
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
  
  public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
    
    int tab = event.getItem();
    
    if (tab == 0) {
      drawTypes();
      
    } else if (tab == 1) {
      drawStuffType();
      
    } else if (tab == 2) {
      drawThings();
      
    } 
    
    
  }

  public void onSelection(SelectionEvent<Integer> event) {
  
    
    int tab = event.getSelectedItem();
    
    if (tab == 0) {
      History.newItem("account_Types");
      
    } else if (tab == 1) {
      History.newItem("account_StuffType");
      
    } else if (tab == 2) {
      History.newItem("account_Things");
      
    } 
    
  }


  public void onValueChange(ValueChangeEvent<String> event) {
    //draw();
  }
  
  
}
