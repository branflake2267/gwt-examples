package org.gonevertical.core.client.ui.admin.thingstufftype;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.ui.Ui;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
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

public class ThingStuffTypes extends Ui implements ClickHandler, ChangeHandler {
  
  private VerticalPanel pWidget = new VerticalPanel();
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pList = new VerticalPanel();
  private int[] widths = new int[14];
  
  private PushButton bAdd = new PushButton("Add");
  private PushButton bSave = new PushButton("Save");
  private PushButton bFilter = new PushButton("Filter");
  
  private ThingData thingData = null;
  
  private Paging wPage = new Paging();
  private ThingStuffFilter wFilter = new ThingStuffFilter(cp);
  
  private Row th = new Row();
  
  /**
   * constructor 
   * 
   * @param cp
   */
  public ThingStuffTypes(ClientPersistence cp) {
    super(cp);
    
    th = new Row();
    
    pWidget.add(wFilter);
    pWidget.add(pMenu);
    pWidget.add(th);
    pWidget.add(pList);
    pWidget.add(wPage);
    
    initWidget(pWidget);
    
    drawMenu();
    
    bAdd.addClickHandler(this);
    bSave.addClickHandler(this);
    wPage.addChangeHandler(this);
    bFilter.addClickHandler(this);
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
    hp.add(new HTML("&nbsp;"));
    hp.add(bFilter);
    
    pMenu.add(hp);
  }
  
  private void drawTopRow() {
  	
  	if (th.getWidths().length > 0) {
  		return;
  	}
  	
    HTML l1 = new HTML("#");
    HTML l2 = new HTML("Id");
    HTML l3 = new HTML("Name");
    HTML l4 = new HTML("Type");
    HTML l5 = new HTML("StartDt");
    HTML l6 = new HTML("EndDt");
    HTML l7 = new HTML("Rank");
    HTML l8 = new HTML("CreatedBy");
    HTML l9 = new HTML("CreatedDt");
    HTML l10 = new HTML("UpdatedBy");
    HTML l11 = new HTML("UpdatedDt");
    HTML l12 = new HTML("Owners");
    HTML l13 = new HTML("&nbsp;");

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
    l13.setStyleName("core-row-top");
    
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
    th.add(l13, HorizontalPanel.ALIGN_CENTER);
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
    
    wPage.setCounts(thingStuffTypeData.length);

    long count = wPage.getCountOffset();
    for (int i=0; i < thingStuffTypeData.length; i++){
    	if (i == 0) {
    		drawTopRow();
    	}
      addStuffType((int)count, thingStuffTypeData[i]);
      count++;
    }
    
    setWidths();
  }
  
  private void setWidths() {
    
    if (pList.getWidgetCount() <= 1) {
      return;
    }
    
    widths = th.getWidths();
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      ThingStuffType t = (ThingStuffType) pList.getWidget(i);
      widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    }
    
    for (int i=0; i < pList.getWidgetCount(); i++) {
      ThingStuffType t = (ThingStuffType) pList.getWidget(i);
      t.getRow().setWidths(widths);
    }
    
    th.setWidths(widths);
  }
  
  private ThingStuffType addStuffType(int count, ThingStuffTypeData ThingStuffTypeData) {
    ThingStuffType t = new ThingStuffType(cp);
    t.setData(count, ThingStuffTypeData);
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
  		
  	} else if (sender == bFilter) {
  		getThingTypesRpc();
  		
  	}
  
  }
  
 private void getThingTypesRpc() {
    
  	cp.showLoading(true);
    
    ThingStuffTypeDataFilter filter = new ThingStuffTypeDataFilter();
    filter.setValueTypeId(wFilter.getThingStuffValueTypeIds());
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
