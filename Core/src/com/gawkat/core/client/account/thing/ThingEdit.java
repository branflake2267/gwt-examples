package com.gawkat.core.client.account.thing;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.account.ChangePassword;
import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstuff.ThingStuffFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffs;
import com.gawkat.core.client.account.thingstuff.ThingStuffsData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.LoadingWidget;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThingEdit extends Composite implements ClickHandler, ChangeHandler {
  
  private ClientPersistence cp = null;
  
  private RpcCoreServiceAsync rpc = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private ThingStuffs wStuff = null;
  private ThingStuffs wAboutStuff = null;
  
  private ThingData thingData = null;

  private ThingTypesData thingTypesData = null;
  
  private VerticalPanel pTop = new VerticalPanel();
  private VerticalPanel pStuff = new VerticalPanel();
  private VerticalPanel pAboutStuff = new VerticalPanel();
  
  private TextBox tbKey = new TextBox();
  
  private PushButton bChangePassword = new PushButton("Change Password");
  
  private LoadingWidget wLoading = new LoadingWidget();

	private ThingStuffsData thingsStuffData;
  
  public ThingEdit(ClientPersistence cp) {
    this.cp = cp;
    
    wStuff = new ThingStuffs(cp);
    wAboutStuff = new ThingStuffs(cp);
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(pStuff);
    hp.add(pAboutStuff);
    
    pWidget.add(pTop);
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    bChangePassword.addClickHandler(this);
    
    rpc = RpcCore.initRpc();
    
    wStuff.addStyleName("test3");
    
    wStuff.addChangeHandler(this);
  }
  
  public void setData(ThingData thingData, ThingTypesData thingTypesData) {
    this.thingData = thingData;
    this.thingTypesData  = thingTypesData;
  }
  
  /**
   * inital draw
   */
  public void draw() {
    drawTop();
    
    ThingStuffFilterData filter = new ThingStuffFilterData();
    filter.thingId = thingData.getThingId();
    
    getThingStuffRpc(filter);
  }
  
  /**
   * draw the thing stuffs
   * 
   * @param thingStuffsData
   */
  private void process(ThingStuffsData thingStuffsData) {
  	this.thingsStuffData = thingStuffsData;
  	
  	pStuff.clear();
    pStuff.add(wStuff);
    wStuff.draw(thingData, thingStuffsData);
    
  }
  
  /**
   * draw about stuff
   * 
   * @param thingStuffsData
   */
  private void processAbout(ThingStuffData thingStuffData) {

  	ThingStuffsData thingStuffsData = null;
  	if (thingStuffData != null) {
  		thingStuffsData = thingStuffData.getThingStuffsAbout();
  	} else {
  		thingStuffsData = new ThingStuffsData();
  	}
  	thingStuffsData.thingStuffTypesData = this.thingsStuffData.thingStuffTypesData;
  	
  	pAboutStuff.clear();
  	pAboutStuff.add(wAboutStuff);
    wAboutStuff.draw(thingData, thingStuffsData);
    
  }
  
  private void drawTop() {
    pTop.clear();
    
    tbKey.setText(thingData.getKey());
    
    HorizontalPanel hp = new HorizontalPanel();
    pTop.add(hp);
    
    //hp.add(new HTML("Type: " + thingTypeData.getName())); // TODO enable
    hp.add(new HTML("&nbsp;&nbsp;&nbsp;"));
    hp.add(new HTML("Id: " + thingData.getThingId()));

    Grid grid = new Grid(2, 2);
    pTop.add(grid);
    
    HTML l1 = new HTML("Username");
    HTML l2 = new HTML("Password");
    
    HorizontalPanel bButtons = new HorizontalPanel();
    bButtons.add(bChangePassword);
    
    grid.setWidget(0, 0, l1);
    grid.setWidget(0, 1, tbKey);
    
    grid.setWidget(1, 0, l2);
    grid.setWidget(1, 1, bButtons);
    
  }
  
  private void changePassword() {
    ChangePassword p = new ChangePassword(cp);
    p.draw(thingData);
    p.center();
  }
  
  public void save() {
    wStuff.save();
  }
  
  public void clear() {
    //wStuff.clear();
  }
  
  public void onClick(ClickEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == bChangePassword) {
      changePassword();
    }
    
  }
  
  public void onChange(ChangeEvent event) {
  	Widget sender = (Widget) event.getSource();
  	
  	if (sender == wStuff) {
  		int changeEvent = wStuff.getChangeEvent();
  		if (changeEvent == EventManager.LOAD_ABOUTTHINGSTUFF) {
  			processAbout(wStuff.getAboutThingStuffData());
  		}
  	}
  	
  }
  
 private void getThingStuffRpc(ThingStuffFilterData filter) {
    
    wLoading.show();
    
    rpc.getThingStuffData(cp.getAccessToken(), filter, new AsyncCallback<ThingStuffsData>() {
      public void onSuccess(ThingStuffsData thingStuffsData) {
        process(thingStuffsData);
        wLoading.hide();
      }
      
			public void onFailure(Throwable caught) {
      }
    });
    
  }



}
