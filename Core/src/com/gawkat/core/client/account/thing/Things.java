package com.gawkat.core.client.account.thing;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.Row;
import com.gawkat.core.client.SetDefaultsData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypeFilterData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.account.ui.Paging;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.gawkat.core.server.jdo.data.SetDefaults;
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

public class Things extends Composite implements ClickHandler, ChangeHandler {
  
  private ClientPersistence cp = null;
  private RpcCoreServiceAsync rpc = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private ThingEdit wEdit = null;
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pListTop = new VerticalPanel();
  private VerticalPanel pList = new VerticalPanel();
  private int[] widths = new int[5];
  
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  private PushButton bBack = new PushButton("Back");
  
  // use for edit
  private ThingTypesData thingTypesData = null;
  
  private Paging wPage = new Paging();
  
  public Things(ClientPersistence cp) {
    this.cp = cp;
    
    wEdit = new ThingEdit(cp);
    
    pWidget.add(pMenu);
    pWidget.add(pListTop);
    pWidget.add(pList);
    pWidget.add(wEdit);
    pWidget.add(wPage);
    
    initWidget(pWidget);
    
    // defaults
    drawEdit(false);
    
    rpc = RpcCore.initRpc();
    
    drawMenu();
    
    bAdd.addClickHandler(this);
    bSave.addClickHandler(this);
    bBack.addClickHandler(this);
    wPage.addChangeHandler(this);
    
    //pList.addStyleName("test1");
    //wEdit.addStyleName("test2");
    //pWidget.addStyleName("test3");
    
    // no need for now
    //pWidget.setCellHorizontalAlignment(wPage, HorizontalPanel.ALIGN_CENTER);
  }
  
  public void draw() {
  	drawEdit(false);
    getThingsRpc();
  }
  
  public void drawMenu() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bBack);
    hp.add(new HTML("&nbsp;"));
    hp.add(bAdd);
    hp.add(new HTML("&nbsp;"));
    hp.add(bSave);
    
    pMenu.add(hp);
  }
  
  private void drawTopRow() {
    pListTop.clear();
    HTML l1 = new HTML("#");
    HTML l2 = new HTML("Id");
    HTML l3 = new HTML("ThingType");
    HTML l4 = new HTML("UserName");
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
    
    widths = Row.getMaxWidths(widths, th.getWidths());
  }
  
  private void process(ThingsData thingsData) {
    
    pList.clear();
    
    if (thingsData == null) {
      return;
    }
    
    if (thingsData.thingData == null) {
      return;
    }
    
    // set choices
    thingTypesData = thingsData.thingTypesData;
    
    ThingData[] t = thingsData.thingData;
    
    if (t.length == 0) {
    	return;
    }
    
    drawTopRow();
    
    wPage.setCounts(thingsData.total);
    
    long count = wPage.getCountOffset();
    for (int i=0; i < t.length; i++) {
      ThingTypeData thingTypeData = thingsData.thingTypesData.getThingType(t[i].getThingTypeId());
      addThing(count, t[i], thingTypeData);
      count++;
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
  
  private Thing addThing(long count, ThingData thingData, ThingTypeData thingTypeData) {
    Thing t = new Thing(cp);
    t.setData(count, thingData, thingTypeData);
    pList.add(t);
    widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    t.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        Thing tt = (Thing) event.getSource();
        int changeEvent = tt.getChangeEvent();
        if (changeEvent == EventManager.THING_VIEW) {
          view(tt.getData());
        } else if (changeEvent == EventManager.THING_EDIT) {
          edit(tt.getData());
        }
      }
    });
    return t;
  }
  
  private void add() {
    int i = pList.getWidgetCount();
    ThingData thingData = new ThingData();
    Thing t = addThing(i, thingData, null);
    t.getRow().setWidths(widths);
    setWidths();
  }
  
  private void save() {
    
  	// when edit mode is on, skip the saving of the rest of the things
    if (wEdit.isVisible() == true) {
      wEdit.save();
      return;
    }

    int wc = pList.getWidgetCount();
    if (wc == 0) {
      return;
    }
    
    ThingData[] thingData = new ThingData[wc];
    
    for (int i=0; i < thingData.length; i++) {
      Thing tt = (Thing) pList.getWidget(i);
      ThingData td = tt.getData();
      thingData[i] = td;
    }
    saveThingsRpc(thingData);
  }
  
  private void view(ThingData thingData) {
    
  }
  
  private void drawEdit(boolean b) {
    if (b == true) {
      pListTop.setVisible(false);
      pList.setVisible(false);
      wEdit.setVisible(true);
      bBack.setVisible(true);
      bAdd.setVisible(false);
      bSave.setVisible(true);
      wPage.setVisible(false);
      
    } else if (b == false ) {
      pListTop.setVisible(true);
      pList.setVisible(true);
      wEdit.setVisible(false);
      bBack.setVisible(false);
      bAdd.setVisible(true);
      bSave.setVisible(true);
      wPage.setVisible(true);
    }
  }
  
  private void edit(ThingData thingData) {
    drawEdit(true);
    wEdit.setData(thingData, thingTypesData);
    wEdit.draw();
  }
  
  public void onClick(ClickEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == bAdd) {
      add();
      
    } else if (sender == bSave) {
      save();
      
    } else if (sender == bBack) {
      drawEdit(false);
    }
  }
  
  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    
    if (sender == wPage) {
    	getThingsRpc();
    }
    
  }
  
  private void getThingsRpc() {
  	
  	cp.showLoading(true);
    
    ThingFilterData filter = new ThingFilterData();
    filter.start = wPage.getStart();
    filter.limit = wPage.getLimit();
    
    rpc.getThings(cp.getAccessToken(), filter, new AsyncCallback<ThingsData>() {
      public void onSuccess(ThingsData thingsData) {
        
      	process(thingsData);
        
        cp.showLoading(false);
        
      }
      public void onFailure(Throwable caught) {
      	cp.setRpcFailure(caught);
      }
    });
    
  }

  private void saveThingsRpc(ThingData[] thingData) {
    
  	cp.showLoading(true);
    
    ThingFilterData filter = new ThingFilterData();
    filter.start = wPage.getStart();
    filter.limit = wPage.getLimit();

    rpc.saveThings(cp.getAccessToken(), filter, thingData, new AsyncCallback<ThingsData>() {
			public void onSuccess(ThingsData thingData) {
				process(thingData);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});
    
  }

  
}
