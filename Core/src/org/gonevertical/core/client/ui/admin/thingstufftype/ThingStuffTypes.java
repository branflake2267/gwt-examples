package org.gonevertical.core.client.ui.admin.thingstufftype;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.Ui;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.widgets.Paging;
import org.gonevertical.core.client.ui.widgets.Row;

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

public class ThingStuffTypes extends Ui implements ClickHandler, ChangeHandler {
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pListTop = new VerticalPanel();
  private VerticalPanel pList = new VerticalPanel();
  private int[] widths = new int[5];
  
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  
  private ThingData thingData = null;
  
  private Paging wPage = new Paging();
  
  public ThingStuffTypes(ClientPersistence cp) {
    super(cp);
    
    pWidget.add(pMenu);
    pWidget.add(pListTop);
    pWidget.add(pList);
    pWidget.add(wPage);
    
    initWidget(pWidget);
    
    drawMenu();
    
    bAdd.addClickHandler(this);
    bSave.addClickHandler(this);
    wPage.addChangeHandler(this);
  }
  
  public void setData(ThingData thingData) {
    this.thingData = thingData;
  }
  
  public void draw() {
    getThingTypesRpc();
  }
  
  public void drawMenu() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bAdd);
    hp.add(new HTML("&nbsp;"));
    hp.add(bSave);
    
    pMenu.add(hp);
  }
  
  private void drawTopRow() {
    pListTop.clear();
    HTML l1 = new HTML("#");
    HTML l2 = new HTML("Id");
    HTML l3 = new HTML("Name");
    HTML l4 = new HTML("Type");
    HTML l5 = new HTML("&nbsp;");

    l1.setStyleName("core-row-top");
    l2.setStyleName("core-row-top");
    l3.setStyleName("core-row-top");
    l4.setStyleName("core-row-top");
    l5.setStyleName("core-row-top");
    
    Row th = new Row();
    th.add(l1, HorizontalPanel.ALIGN_CENTER);
    th.add(l2, HorizontalPanel.ALIGN_CENTER);
    th.add(l3, HorizontalPanel.ALIGN_CENTER);
    th.add(l4, HorizontalPanel.ALIGN_CENTER);
    th.add(l5, HorizontalPanel.ALIGN_CENTER);
    
    pListTop.add(th);
  }
  
  private void process(ThingStuffTypesData thingStuffTypesData) {
    
    pList.clear();
    
    if (thingStuffTypesData == null) {
      return;
    }
    
    if (thingStuffTypesData.getThingStuffTypeData() == null) {
      return;
    }
    ThingStuffTypeData[] thingStuffTypeData = thingStuffTypesData.getThingStuffTypeData();
    
    if (thingStuffTypeData.length == 0) {
    	return;
    }
    
    drawTopRow();
    
    wPage.setCounts(thingStuffTypeData.length);

    for (int i=0; i < thingStuffTypeData.length; i++){
      addStuffType(i, thingStuffTypeData[i]);
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
  
  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bAdd) {
      add();
    } else if (sender == bSave) {
      save();
    }
  }
  
  public void onChange(ChangeEvent event) {
  
  	Widget sender = (Widget) event.getSource();
  	
  	if (sender == wPage) {
  		getThingTypesRpc();
  	}
  
  }
  
 private void getThingTypesRpc() {
    
  	cp.showLoading(true);
    
    ThingStuffTypeDataFilter filter = new ThingStuffTypeDataFilter();
    filter.setLimit(wPage.getStart(), wPage.getLimit());
    
    rpc.getThingStuffTypes(cp.getAccessToken(), filter, new AsyncCallback<ThingStuffTypesData>() {
      public void onSuccess(ThingStuffTypesData ThingStuffTypesData) {
        process(ThingStuffTypesData);
        cp.showLoading(false);
      }
      public void onFailure(Throwable caught) {
      	cp.showLoading(false);
      	cp.setRpcFailure(caught);
      }
    });
    
  }

  private void saveThingTypesRpc(ThingStuffTypeData[] thingStuffTypeData) {
    
  	cp.showLoading(true);
    
    ThingStuffTypeDataFilter filter = new ThingStuffTypeDataFilter();
    filter.setLimit(wPage.getStart(), wPage.getLimit());

    rpc.saveThingStuffTypes(cp.getAccessToken(), filter, thingStuffTypeData, new AsyncCallback<ThingStuffTypesData>() {
      public void onSuccess(ThingStuffTypesData thingStuffTypesData) {
        process(thingStuffTypesData);
        cp.showLoading(false);
      }
      public void onFailure(Throwable caught) {
      	cp.showLoading(false);
      	cp.setRpcFailure(caught);
      }
    });
    
  }

  
}
