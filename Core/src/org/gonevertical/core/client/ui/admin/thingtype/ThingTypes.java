package org.gonevertical.core.client.ui.admin.thingtype;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.ui.Ui;
import org.gonevertical.core.client.ui.widgets.Paging;
import org.gonevertical.core.client.ui.widgets.Row;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThingTypes extends Ui implements ClickHandler, ChangeHandler {
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pList = new VerticalPanel();
  private int[] widths = new int[13];
  
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  
  private Paging wPage = new Paging();
  
  private Row th = new Row();
  
  public ThingTypes(ClientPersistence cp) {
    super(cp);
    
    th = new Row();
    
    pWidget.add(pMenu);
    pWidget.add(th);
    pWidget.add(pList);
    pWidget.add(wPage);
    
    initWidget(pWidget);
    
    drawMenu();
    
    bAdd.addClickHandler(this);
    bSave.addClickHandler(this);
  }
  
  public void draw() {
    // TODO use this filter later
    ThingTypeDataFilter filter = new ThingTypeDataFilter();
    getThingTypesRpc(filter);
  }
  
  public void drawMenu() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bAdd);
    hp.add(new HTML("&nbsp;"));
    hp.add(bSave);
    
    pMenu.add(hp);
  }
  
  private void drawTopRow() {
  	
  	if (th.getWidths().length > 0) {
  		return;
  	}

    HTML l1 = new HTML("#");
    HTML l2 = new HTML("Id");
    HTML l3 = new HTML("Name");
    HTML l4 = new HTML("StartDt");
    HTML l5 = new HTML("EndDt");
    HTML l6 = new HTML("Rank");
    HTML l7 = new HTML("CreatedBy");
    HTML l8 = new HTML("CreatedDt");
    HTML l9 = new HTML("UpdatedBy");
    HTML l10 = new HTML("UpdatedDt");
    HTML l11 = new HTML("Owners");
    HTML l12 = new HTML("&nbsp;");

    l1.setStyleName("core-row-top");
    l2.setStyleName("core-row-top");
    l3.setStyleName("core-row-top");
    l4.setStyleName("core-row-top");
    l5.setStyleName("core-row-top");
    l6.setStyleName("core-row-top");
    l7.setStyleName("core-row-top");
    l8.setStyleName("core-row-top");
    l9.setStyleName("core-row-top");
    l10.setStyleName("core-row-top");
    l11.setStyleName("core-row-top");
    l12.setStyleName("core-row-top");
    
    
    th.add(l1, HorizontalPanel.ALIGN_CENTER);
    th.add(l2, HorizontalPanel.ALIGN_CENTER);
    th.add(l3, HorizontalPanel.ALIGN_CENTER);
    th.add(l4, HorizontalPanel.ALIGN_CENTER);
    th.add(l5, HorizontalPanel.ALIGN_CENTER);
    th.add(l6, HorizontalPanel.ALIGN_CENTER);
    th.add(l7, HorizontalPanel.ALIGN_CENTER);
    th.add(l8, HorizontalPanel.ALIGN_CENTER);
    th.add(l9, HorizontalPanel.ALIGN_CENTER);
    th.add(l10, HorizontalPanel.ALIGN_CENTER);
    th.add(l11, HorizontalPanel.ALIGN_CENTER);
    th.add(l12, HorizontalPanel.ALIGN_CENTER);
  }
  
  private void process(ThingTypesData thingTypesData) {
    
    pList.clear();
    
    if (thingTypesData == null) {
      return;
    }
    
    if (thingTypesData.getThingTypeData() == null) {
      return;
    }
    
    ThingTypeData[] thingTypeData = thingTypesData.getThingTypeData();
    
    if (thingTypeData.length == 0) {
    	return;
    }
    
    wPage.setCounts(thingTypesData.getTotal());

    int count = (int) wPage.getCountOffset();
    for (int i=0; i < thingTypeData.length; i++) {
    	if (i == 0) {
    		drawTopRow();
    	}
      addThingType(count, thingTypeData[i]);
      count++;
    }
    
    setWidths();
  }
  
  private void setWidths() {
    
    if (pList.getWidgetCount() <= 1) {
      return;
    }
    
    // get fields length
    widths = th.getWidths();
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      ThingType t = (ThingType) pList.getWidget(i);
      widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    }
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      ThingType t = (ThingType) pList.getWidget(i);
      t.getRow().setWidths(widths);
    }
    
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
  	if (i == 0) {
  		drawTopRow();
  	}
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
  
  private void getThingTypesRpc(ThingTypeDataFilter filter) {
    
  	cp.showLoading(true);
   
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
