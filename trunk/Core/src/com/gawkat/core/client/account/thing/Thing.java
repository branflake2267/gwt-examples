package com.gawkat.core.client.account.thing;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.Row;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.global.DeleteDialog;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.rpc.RpcCore;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class Thing extends Composite implements ChangeHandler, ClickHandler {

  private ClientPersistence cp = null;
  
  private RpcCoreServiceAsync rpc = null;
  
  private DeleteDialog wdelete = new DeleteDialog();
  
  private Row pWidget = new Row();
  
  private FlowPanel pCount = new FlowPanel();
  private FlowPanel pType = new FlowPanel();
  private FlowPanel pName = new FlowPanel();
  private HorizontalPanel pModify = new HorizontalPanel();
  
  private PushButton bDelete = new PushButton("X");
  private PushButton bEdit = new PushButton("Edit");
  private PushButton bView = new PushButton("View"); // TODO - use this later
   
  private ThingData thingData = null;

  private int row = 0;
  
  private int changeEvent = 0;
  
  private ThingTypeData thingTypeData = null;
  
  public Thing(ClientPersistence cp) {
    this.cp = cp;
    
    pModify.add(bDelete);
    pModify.add(new HTML("&nbsp;"));   
    pModify.add(bEdit);
    
    initWidget(pWidget);
    
    pWidget.addChangeHandler(this);
    bDelete.addClickHandler(this);
    bEdit.addClickHandler(this);
    bView.addClickHandler(this);
    
    rpc = RpcCore.initRpc();
  }

  public void setData(int row, ThingData thingData, ThingTypeData thingTypeData) {
  	//if (thingData == null || thingTypeData == null) {
  		//return;
  	//}
    this.row = row;
    this.thingData = thingData;
    this.thingTypeData = thingTypeData;
    pWidget.setRow(row);
    draw();
  }
  
  private void draw() {
    
    pWidget.clear();
    pName.clear();
    
    // C0: count
    pCount.add(new HTML(Integer.toString(row)));
   
    // C1: Id
    HTML pId = new HTML(Long.toString(thingData.getThingId()));
   
    // C2: thing type - app, user, group...
    //pType.add(new HTML(thingTypeData.getName())); 
    
    // C2: name
    pName.add(new HTML(thingData.getKey()));
    
    // C3: buttons
    
    pWidget.add(pCount);
    pWidget.add(pId);
    pWidget.add(pType);
    pWidget.add(pName);
    pWidget.add(pModify);
  }
  
  public Row getRow() {
    return pWidget;
  }
  

  public ThingData getData() {
    return thingData;
  }
  
  private void delete() {
    wdelete.center();
    wdelete.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        DeleteDialog dd = (DeleteDialog) event.getSource();
        int changeEvent = dd.getChangeEvent();
        if (changeEvent == EventManager.DELETE_YES && thingData.getThingId() > 0) {
          deleteRpc();
        } else if (changeEvent == EventManager.DELETE_YES) {
          deleteIt(true);
        }
      }
    });
  }
  
  private void deleteIt(boolean b) {
    if (b == true) {
      this.removeFromParent();
    } else {
      Window.alert("Wasn't able to delete thing. Please try again?");
    }
  }
  
  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == pWidget) {
      int changeEvent = pWidget.getChangeEvent();
      if (changeEvent == EventManager.ROW_OVER) {
       
      } else if (changeEvent == EventManager.ROW_OUT) {
        
      }
    }
    
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
    if (sender == bDelete) {
      delete();
    } else if (sender == bEdit) {
      fireChange(EventManager.THING_EDIT);
    } else if (sender == bView) {
      fireChange(EventManager.THING_VIEW);
    }
    
  }
  
  private void deleteRpc() {
    rpc.deleteThing(cp.getAccessToken(), thingData, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean b) {
        deleteIt(b);
      }
      public void onFailure(Throwable caught) {
      }
    });
  }
  
  
}
