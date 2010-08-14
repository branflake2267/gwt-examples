package org.gonevertical.core.client.ui.admin.thing;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.DeleteDialog;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.Ui;
import org.gonevertical.core.client.ui.admin.thingstuff.ThingStuffsData;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeData;
import org.gonevertical.core.client.ui.widgets.Row;

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

public class Thing extends Ui implements ChangeHandler, ClickHandler {
  
  private Row pWidget = new Row();
  
  private FlowPanel pCount = new FlowPanel();
  private FlowPanel pType = new FlowPanel();
  private FlowPanel pUserName = new FlowPanel();
  private FlowPanel pName = new FlowPanel();
  private HorizontalPanel pModify = new HorizontalPanel();
  
  private PushButton bDelete = new PushButton("X");
  private PushButton bEdit = new PushButton("Edit");
  private PushButton bView = new PushButton("View"); // TODO - use this later
  private PushButton bSelect = new PushButton("Select");
   
  private ThingData thingData = null;

  private long row = 0;
  
  private int changeEvent = 0;
  
  private ThingTypeData thingTypeData = null;
  
  public Thing(ClientPersistence cp) {
    super(cp);
    
    pModify.add(bDelete);
    pModify.add(new HTML("&nbsp;"));   
    pModify.add(bEdit);
    pModify.add(bSelect);
    
    initWidget(pWidget);
    
    pWidget.addChangeHandler(this);
    bDelete.addClickHandler(this);
    bEdit.addClickHandler(this);
    bView.addClickHandler(this);
    bSelect.addClickHandler(this);
    
    bSelect.setVisible(false);
    
  }

  public void setData(long row, ThingData thingData, ThingTypeData thingTypeData) {
  	//if (thingData == null || thingTypeData == null) {
  		//return;
  	//}
    this.row = row;
    this.thingData = thingData;
    this.thingTypeData = thingTypeData;
    pWidget.setRow((int)row);
    draw();
  }
  
  private void draw() {
    
    pWidget.clear();
    pUserName.clear();
    pName.clear();
    
    // C1: count
    pCount.add(new HTML(Long.toString(row)));
   
    // C2: Id
    HTML pId = new HTML(Long.toString(thingData.getThingId()));
   
    // C3: thing type - app, user, group...
    if (thingTypeData != null) {
    	pType.add(new HTML(thingTypeData.getName())); 
    }
    
    // C4: username
    pUserName.add(new HTML(thingData.getKey()));
    
    // c5: name
    pName.add(getName());
    
    // C6: buttons
    
    pWidget.add(pCount);
    pWidget.add(pId);
    pWidget.add(pType);
    pWidget.add(pUserName);
    pWidget.add(pName);
    pWidget.add(pModify);
  }
  
  private Widget getName() {
  	ThingStuffsData tsds = thingData.getThingStuffsData();
  	if (tsds == null || tsds.getThingStuffData() == null || tsds.getThingStuffData().length == 0) {
  		return new HTML("&nbsp;");
  	}
  	
  	String n = null;
  	for (int i=0; i < tsds.getThingStuffData().length; i++) {
  		if (tsds.getThingStuffData()[i].getStuffTypeId() == 1) {
  			n = tsds.getThingStuffData()[i].getValue();
  		}
  	}
	  
  	if (n == null) {
  		n = "&nbsp;";
  	}
  	
  	return new HTML(n);
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
      
    } else if (sender == bSelect) {
    	fireChange(EventManager.THING_SELECT);
    }
    
  }
  
  private void deleteRpc() {
    rpc.deleteThing(cp.getAccessToken(), thingData, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean b) {
        deleteIt(b);
      }
      public void onFailure(Throwable caught) {
      	cp.setRpcFailure(caught);
      }
    });
  }

	public void setSelectionOn(boolean selectionOn) {
		if (selectionOn == true) {
			bSelect.setVisible(true);
		} else {
			bSelect.setVisible(false);
		}
  }

	public ThingData getThingData() {
	  return thingData;
  }
  
  
}
