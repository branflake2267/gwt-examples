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
  private ThingStuffs wStuffAbout = null;
  
  private ThingData thingData = null;

  private ThingTypesData thingTypesData = null;
  
  private VerticalPanel pTop = new VerticalPanel();
  
  private TextBox tbKey = new TextBox();
  
  private PushButton bChangePassword = new PushButton("Change Password");

	private ThingStuffsData thingsStuffData;
	
	private int editingIndex = 0;
  
  public ThingEdit(ClientPersistence cp) {
    this.cp = cp;
    
    wStuff = new ThingStuffs(cp);
    wStuffAbout = new ThingStuffs(cp);
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(wStuff);
    hp.add(wStuffAbout);
    
    pWidget.add(pTop);
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    bChangePassword.addClickHandler(this);
    
    rpc = RpcCore.initRpc();
    
    wStuff.addStyleName("test3");
    
    wStuff.addChangeHandler(this);
    wStuffAbout.addChangeHandler(this);
    
    // don't observe thing stuff mouse overs when in about
    wStuffAbout.ignoreMouseOver(true);
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
  	
    wStuff.draw(thingData, thingStuffsData);
    
  }
  
  /**
   * draw about stuff on the right
   *   mouse over in thing stuff will cause this
   * 
   * @param thingStuffsData
   */
  private void drawAbout(ThingStuffData thingStuffData) {
  	
  	ThingStuffsData thingStuffsData_About = null;
  	if (thingStuffData != null) {
  		thingStuffsData_About = thingStuffData.getThingStuffsAbout();
  		
  	} else {
  		thingStuffsData_About = new ThingStuffsData();
  	}
  	
  	// if the about is new we need to create it to store in the types
  	if (thingStuffsData_About == null) {
  		thingStuffsData_About = new ThingStuffsData();
  	}
  	
  	thingStuffsData_About.thingStuffTypesData = this.thingsStuffData.thingStuffTypesData;
  	
    wStuffAbout.draw(thingData, thingStuffsData_About);
    
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
  
  /**
   * save the data to server
   */
  public void save() {
    ThingStuffData[] thingStuffData = wStuff.getData();
    saveThingStuffsData(thingStuffData);
  }
  
  /**
   * before a new moused over lets 
   * 
   * @param index - editing index, caused by mouse event in thing stuff
   */
  private void updateAboutStuff(int index) {
  	
  	// < -1 nothing selected yet
  	if (index < 0) { 
  		return;
  	}
  	
  	// get about stuff that was modified
  	ThingStuffData[] tsd = wStuffAbout.getData();
  	
  	if (tsd == null || tsd.length == 0) {
  		return;
  	}
  	
  	// update the thingstuff array 
  	wStuff.setAboutThingStuffData(index, tsd);
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
 
  		if (changeEvent == EventManager.ABOUTTHINGSTUFF_PREMOUSEOVER) { // thing stuff moused over, lets update before we change the data
  			editingIndex = wStuff.getEditingIndex();
  			updateAboutStuff(editingIndex);
  			
  		} else if (changeEvent == EventManager.ABOUTTHINGSTUFF_MOUSEOVER) { // this comes from mouse over int thing stuff
  			editingIndex = wStuff.getEditingIndex(); // what index was moused over
  			drawAbout(wStuff.getAboutThingStuffData());// update thing about stuff for editing
  		}
  		
  	} 
  	
  }
  
  private void getThingStuffRpc(ThingStuffFilterData filter) {
 
  	cp.showLoading(true);
    
    rpc.getThingStuffData(cp.getAccessToken(), filter, new AsyncCallback<ThingStuffsData>() {
      public void onSuccess(ThingStuffsData thingStuffsData) {
        process(thingStuffsData);
        cp.showLoading(false);
      }

			public void onFailure(Throwable caught) {
      }
    });
    
  }
 
 private void saveThingStuffsData(ThingStuffData[] thingStuffData) {
 
	 cp.showLoading(true);
 
   ThingStuffFilterData filter = new ThingStuffFilterData();
   filter.thingId = thingData.getThingId();

   rpc.saveThingStuffData(cp.getAccessToken(), filter, thingStuffData, new AsyncCallback<ThingStuffsData>() {
     public void onSuccess(ThingStuffsData thingStuffsData) {
       process(thingStuffsData);
       cp.showLoading(false);
     }
     public void onFailure(Throwable caught) {
     }
   });
   
 }



}
