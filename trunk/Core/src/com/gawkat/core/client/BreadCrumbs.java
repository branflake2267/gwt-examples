package com.gawkat.core.client;

import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.global.QueryString;
import com.gawkat.core.client.global.QueryStringData;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class BreadCrumbs extends Composite implements ValueChangeHandler<String> {

  private ClientPersistence cp = null;
  
  private HorizontalPanel pWidget = new HorizontalPanel();
  
  public BreadCrumbs(ClientPersistence cp) {
    this.cp = cp;
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(pWidget);
    hp.add(new HTML("&nbsp;"));
    hp.add(cp.getLoadingWidget());
    
    initWidget(hp);
    
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
      link = "account_Home";
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
      
    } else if (qsd.getHistoryToken().matches("account_Home") == true) {
      name = "Home";
      
    } else if (qsd.getHistoryToken().matches("account_Things") == true) {
      name = "Things";
      
    } else if (qsd.getHistoryToken().matches("account_StuffType") == true) {
      name = "Thing Stuff Types";
      
    } else if (qsd.getHistoryToken().matches("account_Types") == true) {
      name = "Thing Types";
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
