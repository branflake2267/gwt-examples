package com.gawkat.core.client.account.thingstuff;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.Row;
import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.global.Style;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
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

public class ThingStuffs extends Composite implements ClickHandler {

  private ClientPersistence cp = null;
  private RpcCoreServiceAsync rpc = null;
  private LoadingWidget wLoading = new LoadingWidget();
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pListTop = new VerticalPanel();
  private VerticalPanel pListStuff = new VerticalPanel();
  private int[] widths = new int[3];
  
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  
  // stuff owner
  private ThingData thingData = null;
  
  // what kind of types are available to choose from?
  private ThingStuffTypesData thingStuffTypesData = null;
  
  /**
   * constructor
   * @param cp
   */
  public ThingStuffs(ClientPersistence cp) {
    this.cp = cp;
    
    pWidget.add(pMenu);
    pWidget.add(pListTop);
    pWidget.add(pListStuff);
    
    initWidget(pWidget);
    
    rpc = RpcCore.initRpc();
    
    drawMenu();
    
    bAdd.addClickHandler(this);
    bSave.addClickHandler(this);
    
    // defaults
    bSave.setVisible(false);
    
    pWidget.setStyleName("core-Account-ThingStuffs");
  }
  
  /**
   * stuff owner
   * @param thingData
   */
  public void setData(ThingData thingData) {
    this.thingData = thingData;
  }
  
  public void draw() {
    getThingStuffRpc();
  }
  
  private void drawMenu() {
    
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
    HTML l1 = new HTML("Name"); // drop down type
    HTML l2 = new HTML("Value"); // input box
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
  
  private void process(ThingStuffsData thingStuffsData) {
    
    pListStuff.clear();
    
    if (thingStuffsData == null) {
      return;
    }
    this.thingStuffTypesData = thingStuffsData.thingStuffTypesData;
    
    if (thingStuffsData.thingStuffData == null) {
      return;
    }
    ThingStuffData[] ThingStuffData = thingStuffsData.thingStuffData;
    
    if (ThingStuffData.length > 0) {
      drawTopRow();
    }

    for (int i=0; i < ThingStuffData.length; i++){
      addStuff(i, ThingStuffData[i]);
    }
    
    setWidths();
  }
  
  private void setWidths() {
    
    int wc = pListStuff.getWidgetCount();
    
    if (wc == 0) {
      return;
    }
    
    // get widths from list
    for (int i=0; i < pListStuff.getWidgetCount(); i++) {
      ThingStuff t = (ThingStuff) pListStuff.getWidget(i);
      widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    }
    
    // set widths to list
    for (int i=0; i < pListStuff.getWidgetCount(); i++) {
      ThingStuff t = (ThingStuff) pListStuff.getWidget(i);
      t.getRow().setWidths(widths);
    }
    
    // set the header names of list
    Row th = (Row) pListTop.getWidget(0);
    th.setWidths(widths);
  }
  
  private ThingStuff addStuff(int i, ThingStuffData thingStuffData) {
    ThingStuff t = new ThingStuff(cp);
    t.setData(thingData, thingStuffTypesData, thingStuffData);
    pListStuff.add(t);
    widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    t.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        ThingStuff tt = (ThingStuff) event.getSource();
        if (tt.getChangeEvent() == EventManager.THINGSTUFF_TYPECHANGE) {
          setWidths();
        }
      }
    });
    String style = Style.getRowStyle(i);
    t.addStyleName(style);
    
    return t;
  }
  
  private void add() {
    int i = pListStuff.getWidgetCount();
    if (i==0) {
      drawTopRow();
    }
    ThingStuffData thingStuffData = new ThingStuffData();
    ThingStuff t = addStuff(i, thingStuffData);
    t.getRow().setWidths(widths);
    setWidths();
  }
  
  public void save() {
    
    int wc = pListStuff.getWidgetCount();
    ThingStuffData[] thingStuffData = new ThingStuffData[wc];
    
    for (int i=0; i < thingStuffData.length; i++) {
      ThingStuff tt = (ThingStuff) pListStuff.getWidget(i);
      ThingStuffData td = tt.getData();
      thingStuffData[i] = td;
    }
    
    saveThingTypesRpc(thingStuffData);
  }
  
  public void clear() {
    pListStuff.clear();
  }
  
  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bAdd) {
      add();
    } else if (sender == bSave) {
      save();
    }
  }
  
  private void getThingStuffRpc() {
    
    wLoading.show();
    
    // TODO use this later
    ThingStuffFilterData filter = new ThingStuffFilterData();
    filter.thingId = thingData.getThingId();
    
    rpc.getThingStuffData(cp.getAccessToken(), filter, new AsyncCallback<ThingStuffsData>() {
      public void onSuccess(ThingStuffsData thingStuffsData) {
        process(thingStuffsData);
        wLoading.hide();
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }

  public void onChange(ChangeEvent event) {
  }

  private void saveThingTypesRpc(ThingStuffData[] thingStuffData) {
    
    wLoading.show();
  
    // TODO
    ThingStuffFilterData filter = new ThingStuffFilterData();
    filter.thingId = thingData.getThingId();

    rpc.saveThingStuffData(cp.getAccessToken(), filter, thingStuffData, new AsyncCallback<ThingStuffsData>() {
      public void onSuccess(ThingStuffsData thingStuffsData) {
        process(thingStuffsData);
        wLoading.hide();
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }
}
