package com.gawkat.core.client.account.thingtype;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.Row;
import com.gawkat.core.client.SetDefaultsData;
import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.gawkat.core.server.jdo.SetDefaults;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThingTypes extends Composite implements ClickHandler, ChangeHandler {

  private ClientPersistence cp = null;
  private RpcCoreServiceAsync rpc = null;
  private LoadingWidget wLoading = new LoadingWidget();
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pListTop = new VerticalPanel();
  private VerticalPanel pList = new VerticalPanel();
  private int[] widths = new int[3];
  
  private PushButton bDefault = new PushButton("Add Defaults");
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  
  public ThingTypes(ClientPersistence cp) {
    this.cp = cp;
    
    pWidget.add(pMenu);
    pWidget.add(pListTop);
    pWidget.add(pList);
    
    initWidget(pWidget);
    
    rpc = RpcCore.initRpc();
    
    drawMenu();
    
    bDefault.addClickHandler(this);
    bAdd.addClickHandler(this);
    bSave.addClickHandler(this);
  }
  
  public void draw() {
    getThingTypesRpc();
  }
  
  public void drawMenu() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bDefault);
    hp.add(new HTML("&nbsp;"));
    hp.add(bAdd);
    hp.add(new HTML("&nbsp;"));
    hp.add(bSave);
    hp.add(new HTML("&nbsp;"));
    hp.add(wLoading);
    
    pMenu.add(hp);
    
    
  }
  
  private void drawTopRow() {
    pListTop.clear();
    HTML l1 = new HTML("#");
    HTML l2 = new HTML("Name");
    HTML l3 = new HTML("&nbsp;");

    l1.setStyleName("core-row-top");
    l2.setStyleName("core-row-top");
    l3.setStyleName("core-row-top");
    
    Row th = new Row();
    th.add(l1, HorizontalPanel.ALIGN_CENTER);
    th.add(l2, HorizontalPanel.ALIGN_CENTER);
    th.add(l3, HorizontalPanel.ALIGN_CENTER);
    
    pListTop.add(th);
  }
  
  private void process(ThingTypesData thingTypesData) {
    
    pList.clear();
    
    if (thingTypesData == null) {
      return;
    }
    
    if (thingTypesData.thingTypeData == null) {
      return;
    }
    ThingTypeData[] thingTypeData = thingTypesData.thingTypeData;
    
    drawTopRow();

    for (int i=0; i < thingTypeData.length; i++){
      addThingType(i, thingTypeData[i]);
    }
    
    setWidths();
  }
  
  private void setWidths() {
    
    if (pList.getWidgetCount() <= 1) {
      return;
    }
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      ThingType t = (ThingType) pList.getWidget(i);
      widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    }
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      ThingType t = (ThingType) pList.getWidget(i);
      t.getRow().setWidths(widths);
    }
    
    Row th = (Row) pListTop.getWidget(0);
    th.setWidths(widths);
  }
  
  private ThingType addThingType(int i, ThingTypeData thingTypeData) {
    ThingType t = new ThingType(cp);
    t.setData(i, thingTypeData);
    pList.add(t);
    widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    t.edit(true);
    return t;
  }
  
  private void add() {
    int i = pList.getWidgetCount();
    ThingTypeData thingTypeData = new ThingTypeData();
    ThingType t = addThingType(i, thingTypeData);
    t.getRow().setWidths(widths);
    setWidths();
  }
  
  private void save() {
    int wc = pList.getWidgetCount();
    ThingTypeData[] thingTypeData = new ThingTypeData[wc];
    
    for (int i=0; i < thingTypeData.length; i++) {
      ThingType tt = (ThingType) pList.getWidget(i);
      ThingTypeData td = tt.getData();
      thingTypeData[i] = td;
    }
    saveThingTypesRpc(thingTypeData);
  }
  
  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bDefault) {
      setDefaults();
    } else if (sender == bAdd) {
      add();
    } else if (sender == bSave) {
      save();
    }
  }
  
  /**
   * add the defaults application, user, group
   */
  private void setDefaults() {
    rpc.setDefaults(cp.getAccessToken(), SetDefaultsData.THINGS, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean result) {
        draw();
      }
      public void onFailure(Throwable caught) { 
      }
    });
  }
  
  private void getThingTypesRpc() {
    
    wLoading.show();
    
    // TODO use this later
    ThingTypeFilterData filter = new ThingTypeFilterData();
    
    rpc.getThingTypes(cp.getAccessToken(), filter, new AsyncCallback<ThingTypesData>() {
      public void onSuccess(ThingTypesData thingTypesData) {
        process(thingTypesData);
        wLoading.hide();
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }

  public void onChange(ChangeEvent event) {
    
    //setWidths();
  }

  private void saveThingTypesRpc(ThingTypeData[] thingTypeData) {
    
    wLoading.show();
    
    // TODO
    ThingTypeFilterData filter = new ThingTypeFilterData();

    rpc.saveThingTypes(cp.getAccessToken(), filter, thingTypeData, new AsyncCallback<ThingTypesData>() {
      public void onSuccess(ThingTypesData thingTypesData) {
        process(thingTypesData);
        wLoading.hide();
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }

  
}
