package com.gawkat.core.client.account.thing;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.Row;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Things extends Composite implements ClickHandler {
  
  private ClientPersistence cp = null;
  private RpcCoreServiceAsync rpc = null;
  private LoadingWidget wLoading = new LoadingWidget();
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pListTop = new VerticalPanel();
  private VerticalPanel pList = new VerticalPanel();
  private int[] widths = new int[3];
  
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  
  public Things(ClientPersistence cp) {
    this.cp = cp;
    
    pWidget.add(pMenu);
    pWidget.add(pListTop);
    pWidget.add(pList);
    
    initWidget(pWidget);
    
    rpc = RpcCore.initRpc();
    
    drawMenu();
    
    bAdd.addClickHandler(this);
    bSave.addClickHandler(this);
  }
  
  public void draw() {
    getThingsRpc();
  }
  
  public void drawMenu() {
    
    HorizontalPanel hp = new HorizontalPanel();
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
  
  private void process(ThingsData thingsData) {
    
    pList.clear();
    
    if (thingsData == null) {
      return;
    }
    
    if (thingsData.thingData == null) {
      return;
    }
    ThingData[] t = thingsData.thingData;
    
    drawTopRow();

    for (int i=0; i < t.length; i++){
      addThing(i, t[i]);
    }
    
    setWidths();
  }
  
  private void setWidths() {
    for (int i=0; i < pList.getWidgetCount(); i++) {
      Thing t = (Thing) pList.getWidget(i);
      widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    }
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      Thing t = (Thing) pList.getWidget(i);
      t.getRow().setWidths(widths);
    }
    
    Row th = (Row) pListTop.getWidget(0);
    th.setWidths(widths);
  }
  
  private Thing addThing(int i, ThingData thingData) {
    Thing t = new Thing(cp);
    t.setData(i, thingData);
    pList.add(t);
    widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    t.edit(true);
    return t;
  }
  
  private void add() {
    int i = pList.getWidgetCount();
    ThingData thingData = new ThingData();
    Thing t = addThing(i, thingData);
    t.getRow().setWidths(widths);
    setWidths();
  }
  
  private void save() {
    int wc = pList.getWidgetCount();
    ThingData[] thingData = new ThingData[wc];
    
    for (int i=0; i < thingData.length; i++) {
      Thing tt = (Thing) pList.getWidget(i);
      ThingData td = tt.getData();
      thingData[i] = td;
    }
    //saveThingsRpc(thingData);
  }
  
  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bAdd) {
      add();
    } else if (sender == bSave) {
      save();
    }
  }
  
  private void getThingsRpc() {
    /*
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
    */
  }

  public void onChange(ChangeEvent event) {
    
    //setWidths();
  }

  private void saveThingsRpc(ThingTypeData[] thingData) {
    /*
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
    */
  }

  
}
