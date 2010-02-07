package com.gawkat.core.client.account.thingstufftype;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.Row;
import com.gawkat.core.client.account.thing.ThingData;
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

public class ThingStuffTypes extends Composite implements ClickHandler, ChangeHandler {

  private ClientPersistence cp = null;
  private RpcCoreServiceAsync rpc = null;
  private LoadingWidget wLoading = new LoadingWidget();
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pListTop = new VerticalPanel();
  private VerticalPanel pList = new VerticalPanel();
  private int[] widths = new int[4];
  
  private PushButton bDefault = new PushButton("Add Defaults");
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  
  private ThingData thingData = null;
  
  public ThingStuffTypes(ClientPersistence cp) {
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
  
  public void setData(ThingData thingData) {
    this.thingData = thingData;
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
    HTML l3 = new HTML("Type");
    HTML l4 = new HTML("&nbsp;");

    l1.setStyleName("core-row-top");
    l2.setStyleName("core-row-top");
    l3.setStyleName("core-row-top");
    l4.setStyleName("core-row-top");
    
    Row th = new Row();
    th.add(l1, HorizontalPanel.ALIGN_CENTER);
    th.add(l2, HorizontalPanel.ALIGN_CENTER);
    th.add(l3, HorizontalPanel.ALIGN_CENTER);
    th.add(l4, HorizontalPanel.ALIGN_CENTER);
    
    pListTop.add(th);
  }
  
  private void process(ThingStuffTypesData thingStuffTypesData) {
    
    pList.clear();
    
    if (thingStuffTypesData == null) {
      return;
    }
    
    if (thingStuffTypesData.thingStuffTypeData == null) {
      return;
    }
    ThingStuffTypeData[] ThingStuffTypeData = thingStuffTypesData.thingStuffTypeData;
    
    drawTopRow();

    for (int i=0; i < ThingStuffTypeData.length; i++){
      addStuffType(i, ThingStuffTypeData[i]);
    }
    
    setWidths();
  }
  
  private void setWidths() {
    
    if (pList.getWidgetCount() <= 1) {
      return;
    }
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      ThingStuffType t = (ThingStuffType) pList.getWidget(i);
      widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    }
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      ThingStuffType t = (ThingStuffType) pList.getWidget(i);
      t.getRow().setWidths(widths);
    }
    
    Row th = (Row) pListTop.getWidget(0);
    th.setWidths(widths);
  }
  
  private ThingStuffType addStuffType(int i, ThingStuffTypeData ThingStuffTypeData) {
    ThingStuffType t = new ThingStuffType(cp);
    t.setData(i, ThingStuffTypeData);
    pList.add(t);
    widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    t.edit(true);
    return t;
  }
  
  private void add() {
    int i = pList.getWidgetCount();
    if (i==0) {
      drawTopRow();
    }
    ThingStuffTypeData ThingStuffTypeData = new ThingStuffTypeData();
    ThingStuffType t = addStuffType(i, ThingStuffTypeData);
    t.getRow().setWidths(widths);
    setWidths();
  }
  
  private void save() {
    int wc = pList.getWidgetCount();
    ThingStuffTypeData[] ThingStuffTypeData = new ThingStuffTypeData[wc];
    
    for (int i=0; i < ThingStuffTypeData.length; i++) {
      ThingStuffType tt = (ThingStuffType) pList.getWidget(i);
      ThingStuffTypeData td = tt.getData();
      ThingStuffTypeData[i] = td;
    }
    saveThingTypesRpc(ThingStuffTypeData);
  }
  
  private void setDefaults() {
    rpc.setDefaults(cp.getAccessToken(), 1, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean result) {
        draw();
      }
      public void onFailure(Throwable caught) { 
      }
    });
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
  
  private void getThingTypesRpc() {
    
    wLoading.show();
    
    // TODO use this later
    ThingStuffTypeFilterData filter = new ThingStuffTypeFilterData();
    
    rpc.getThingStuffTypes(cp.getAccessToken(), filter, new AsyncCallback<ThingStuffTypesData>() {
      public void onSuccess(ThingStuffTypesData ThingStuffTypesData) {
        process(ThingStuffTypesData);
        wLoading.hide();
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }

  public void onChange(ChangeEvent event) {
    
    //setWidths();
  }

  private void saveThingTypesRpc(ThingStuffTypeData[] ThingStuffTypeData) {
    
    wLoading.show();
    
    // TODO
    ThingStuffTypeFilterData filter = new ThingStuffTypeFilterData();

    rpc.saveThingStuffTypes(cp.getAccessToken(), filter, ThingStuffTypeData, new AsyncCallback<ThingStuffTypesData>() {
      public void onSuccess(ThingStuffTypesData ThingStuffTypesData) {
        process(ThingStuffTypesData);
        wLoading.hide();
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }

 

  
}
