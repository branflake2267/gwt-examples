package org.gonevertical.core.client.admin.thingtype;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.Row;
import org.gonevertical.core.client.account.ui.Paging;
import org.gonevertical.core.client.global.LoadingWidget;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

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
  private int[] widths = new int[4];
  
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  
  private Paging wPage = new Paging();
  
  public ThingTypes(ClientPersistence cp) {
    this.cp = cp;
    
    pWidget.add(pMenu);
    pWidget.add(pListTop);
    pWidget.add(pList);
    pWidget.add(wPage);
    
    initWidget(pWidget);
    
    rpc = RpcCore.initRpc();
    
    drawMenu();
    
    bAdd.addClickHandler(this);
    bSave.addClickHandler(this);
  }
  
  public void draw() {
    getThingTypesRpc();
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
    HTML l2 = new HTML("Id");
    HTML l3 = new HTML("Name");
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
  
  private void process(ThingTypesData thingTypesData) {
    
    pList.clear();
    
    if (thingTypesData == null) {
      return;
    }
    
    if (thingTypesData.thingTypeData == null) {
      return;
    }
    
    ThingTypeData[] thingTypeData = thingTypesData.thingTypeData;
    
    if (thingTypeData.length == 0) {
    	return;
    }
    
    drawTopRow();
    
    wPage.setCounts(thingTypesData.total);

    int count = (int) wPage.getCountOffset();
    for (int i=0; i < thingTypeData.length; i++){
      addThingType(count, thingTypeData[i]);
      count++;
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
    
    if (sender == bAdd) {
      add();
    } else if (sender == bSave) {
      save();
    }
  }
  
  private void getThingTypesRpc() {
    
  	cp.showLoading(true);
    
    // TODO use this later
    ThingTypeDataFilter filter = new ThingTypeDataFilter();
    
    rpc.getThingTypes(cp.getAccessToken(), filter, new AsyncCallback<ThingTypesData>() {
      public void onSuccess(ThingTypesData thingTypesData) {
        process(thingTypesData);
        cp.showLoading(false);
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }

  public void onChange(ChangeEvent event) {
    
    //setWidths();
  }

  private void saveThingTypesRpc(ThingTypeData[] thingTypeData) {
    
  	cp.showLoading(true);
    
    ThingTypeDataFilter filter = new ThingTypeDataFilter();
    filter.setLimit(wPage.getStart(), wPage.getLimit());

    rpc.saveThingTypes(cp.getAccessToken(), filter, thingTypeData, new AsyncCallback<ThingTypesData>() {
      public void onSuccess(ThingTypesData thingTypesData) {
        process(thingTypesData);
        cp.showLoading(false);
      }
      public void onFailure(Throwable caught) {
      	cp.setRpcFailure(caught);
      }
    });
    
  }

  
}
