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
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pMenu = new VerticalPanel();
  
  private VerticalPanel pListTop = new VerticalPanel();
  private VerticalPanel pListStuff = new VerticalPanel();
  private int[] widths = new int[3];
  
  private PushButton bAdd = new PushButton("Add");
  
  // stuff owner
  private ThingData thingData = null;
  
  /**
   * this is the types that can be selected
   */
  private ThingStuffTypesData thingStuffTypesData = null;
  
	private int changeEvent = 0;
  
	/**
	 * ThingStuff array Index
	 *  keep track of what index is being moused over 
	 *  init with -1 in the beginning so we know nothing selected yet
	 */
	private int editingIndex = -1;
	
	/**
	 * when editing Index - This is for that index
	 *   tell thing edit this is the about we want to edit on the right
	 */
	private ThingStuffData editingIndexThis_AboutThingStuffData = null;
	private boolean ignoreMouseOver;
	
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
    
    pWidget.setStyleName("core-Account-ThingStuffs");
    
    pWidget.addStyleName("test1");
  }
  
  public void draw(ThingData thingData, ThingStuffsData thingStuffsData) {
  	this.thingData = thingData;
  	this.thingStuffTypesData = thingStuffsData.thingStuffTypesData;
  	process(thingStuffsData);
  }  

  private void drawMenu() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bAdd);
    
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
    editingIndex = -1;
    
    pListStuff.clear();
    
    if (thingStuffsData == null) {
      return;
    }
    
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
  
  private ThingStuff addStuff(int index, ThingStuffData thingStuffData) {
    ThingStuff t = new ThingStuff(cp);
    t.setData(index, thingData, thingStuffTypesData, thingStuffData);
    pListStuff.add(t);
    
    // DEBUG
    t.setStyleName("test4");
    
    widths = Row.getMaxWidths(widths, t.getRow().getWidths());
    
    // observe the thing stuff
    t.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {

        ThingStuff tt = (ThingStuff) event.getSource();
        if (tt.getChangeEvent() == EventManager.THINGSTUFF_TYPECHANGE && ignoreMouseOver == false) {
          setWidths();
          
        } else if (tt.getChangeEvent() == EventManager.ABOUTTHINGSTUFF_MOUSEOVER && ignoreMouseOver == false) { // tell thingedit to do something
        	
        	// save the previous modifications
        	setPreMouseOver(); 
        	
        	// load new stuff to modify
        	editingIndex = tt.getIndex(); 
        	editingIndexThis_AboutThingStuffData = tt.getThingStuffData();
        	setMouseOver(tt.getChangeEvent());
     
        }
      }
    });
    
    String style = Style.getRowStyle(index);
    t.addStyleName(style);
    
    return t;
  }
  
  private void setPreMouseOver() {
  	 	
  	setStyleDefault(editingIndex);
  	
  	// tell thingedit to update the about stuff
  	// NOTE editingIndex contains the last modified
  	fireChange(EventManager.ABOUTTHINGSTUFF_PREMOUSEOVER);
  	
  }
  
	private void setMouseOver(int event) {
  		
  	setStyleSelected(editingIndex);
  	
  	fireChange(event);
  }
  
  private void setStyleDefault(int thingStuffIndex) {
  	if (thingStuffIndex < 0 || pListStuff == null || pListStuff.getWidgetCount() == 0) {
  		return;
  	}
  	
  	ThingStuff tt = (ThingStuff) pListStuff.getWidget(thingStuffIndex);
    tt.removeStyleName(Style.getRowStyleSelected());
  }
  
  private void setStyleSelected(int thingStuffIndex) {
  	if (thingStuffIndex < 0) {
  		return;
  	}
  	
  	ThingStuff tt = (ThingStuff) pListStuff.getWidget(thingStuffIndex);
	  String style = Style.getRowStyleSelected();
    tt.addStyleName(style);
  }

  private void add() {
    int index = pListStuff.getWidgetCount();
    if (index == 0) {
      drawTopRow();
    }
    ThingStuffData thingStuffData = new ThingStuffData();
    ThingStuff t = addStuff(index, thingStuffData);
    t.getRow().setWidths(widths);
    setWidths();
  }
  
  public ThingStuffData[] getData() {
  	 
    int wc = pListStuff.getWidgetCount();
    
    if (wc == 0) {
    	return null;
    }
    
    ThingStuffData[] thingStuffData = new ThingStuffData[wc];
    
    for (int i=0; i < thingStuffData.length; i++) {
      ThingStuff tt = (ThingStuff) pListStuff.getWidget(i);
      ThingStuffData td = tt.getData();
      thingStuffData[i] = td;
    }
    
    return thingStuffData;
  }
  
  public ThingStuffData getAboutThingStuffData() {
  	return editingIndexThis_AboutThingStuffData;
  }
  
  public void setAboutThingStuffData(int index, ThingStuffData[] tsd) {
  	if (tsd == null || index < 0 || index > pListStuff.getWidgetCount()) {
  		return;
  	}
  	
  	System.out.println("ThingStuffs.setAboutThingStuffData in index (left) " + index);
  	
  	ThingStuff td = (ThingStuff) pListStuff.getWidget(index);
  	td.setAboutStuff(tsd);	
  }
  
  public int getChangeEvent() {
    return changeEvent;
  }
  
  private void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }
  
  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bAdd) {
      add();
    } 
  }
  
  public void onChange(ChangeEvent event) {
  }

	public int getEditingIndex() {
	  return editingIndex;
  }

	public void ignoreMouseOver(boolean b) {
	  this.ignoreMouseOver = b;
  }

  

	
}
