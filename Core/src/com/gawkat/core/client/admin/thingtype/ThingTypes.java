package com.gawkat.core.client.admin.thingtype;

import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThingTypes extends Composite implements ClickHandler {

  private RpcCoreServiceAsync call = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pList = new VerticalPanel();
  
  private PushButton bDefault = new PushButton("Defaults");
  
  public ThingTypes() {
    
    pWidget.add(pMenu);
    pWidget.add(pList);
    
    initWidget(pWidget);
    
    call = RpcCore.initRpc();
    
    drawMenu();
  }
  
  public void draw() {
    getThingTypesRpc();
  }
  
  public void drawMenu() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bDefault);
    
    pWidget.add(hp);
    
    bDefault.addClickHandler(this);
  }
  
  private void process(ThingTypeData[] thingTypeData) {
    
    if (thingTypeData == null) {
      return;
    }
    
    for (int i=0; i < thingTypeData.length; i++){
      addThingType(thingTypeData[i]);
    }
  }
  
  private void addThingType(ThingTypeData thingTypeData) {
    ThingType t = new ThingType();
    t.setData(thingTypeData);
    pList.add(t);
  }
  
  /**
   * add the defaults application, user, group
   */
  private void setThingTypeDefault() {
    
    call.setDefaults(ThingTypeData.DEFAULT_TYPE, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean result) {
        draw();
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }
  
  private void getThingTypesRpc() {
    
    // TODO use this later
    ThingTypeFilterData filter = new ThingTypeFilterData();
    
    call.getThingTypes(filter, new AsyncCallback<ThingTypeData[]>() {
      public void onSuccess(ThingTypeData[] thingTypeData) {
        process(thingTypeData);
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }

  public void onClick(ClickEvent event) {
  
    Widget sender = (Widget) event.getSource();
    
    if (sender == bDefault) {
      setThingTypeDefault();
    }
  }
  
}
