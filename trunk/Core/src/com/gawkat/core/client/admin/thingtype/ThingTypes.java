package com.gawkat.core.client.admin.thingtype;

import com.gawkat.core.client.rpc.Rpc;
import com.gawkat.core.client.rpc.RpcServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThingTypes extends Composite {

  private RpcServiceAsync call = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pList = new VerticalPanel();
  
  public ThingTypes() {
    
    pWidget.add(pMenu);
    pWidget.add(pList);
    
    initWidget(pWidget);
    
    call = Rpc.initRpc();
  }
  
  private void process(ThingTypeData[] thingTypeData) {
    
    for (int i=0; i < thingTypeData.length; i++){
      addThingType(thingTypeData[i]);
    }
    
  }
  
  private void addThingType(ThingTypeData thingTypeData) {
    ThingType t = new ThingType();
    pList.add(t);
  }
  
  private void getThingTypesRpc() {
    
    ThingTypeFilterData filter = new ThingTypeFilterData();
    
    call.getThingTypes(filter, new AsyncCallback<ThingTypeData[]>() {
      public void onSuccess(ThingTypeData[] thingTypeData) {
        process(thingTypeData);
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }
  
}
