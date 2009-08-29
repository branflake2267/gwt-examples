package com.gawkat.core.client.account;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.global.QueryString;
import com.gawkat.core.client.global.QueryStringData;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class BreadCrumbs extends Composite implements ValueChangeHandler<String> {

  private ClientPersistence cp = null;
  
  private HorizontalPanel pWidget = new HorizontalPanel();
  
  public BreadCrumbs(ClientPersistence cp) {
    this.cp = cp;
    
    initWidget(pWidget);
    
    pWidget.addStyleName("core-Account-Breadcrumbs");
    
    initHistory();
  }
  
  private void initHistory() {
    History.addValueChangeHandler(this);
  }
  
  private void setHome() {
    BreadCrumb b = new BreadCrumb("Home", "home");
    pWidget.add(b);
  }
  
  public void draw() {
    pWidget.clear();
    
    setHome();
    
    QueryStringData qsd = QueryString.getQueryStringData();

    String name = "";
    String link = "";
    if (qsd.getHistoryToken().matches("account_.*") == true) {
      name = "Account";
      link = "account_Profile";
    }
    
    if (name.length() == 0) {
      return;
    }
    
    BreadCrumb b = new BreadCrumb(name, link);
    pWidget.add(b);
    
    drawEnd(qsd);
  }

  public void drawEnd(QueryStringData qsd) {
    
    String name = "";
    if (qsd.getHistoryToken().matches("account_Create") == true) {
      name = "Create";
    } else if (qsd.getHistoryToken().matches("account_Profile") == true) {
      name = "Profile";
    }
    
    if (name.length() == 0) {
      return;
    }
    BreadCrumb b = new BreadCrumb(name);
    pWidget.add(b);
  }
  
  public void onValueChange(ValueChangeEvent<String> event) {
    draw(); 
  }
  
}
