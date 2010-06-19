package org.gonevertical.core.client.admin.thingtype;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.Row;
import org.gonevertical.core.client.global.DeleteDialog;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ThingType extends Composite implements ChangeHandler, ClickHandler {

  private ClientPersistence cp = null;
  
  private RpcCoreServiceAsync rpc = null;
  
  private DeleteDialog wdelete = new DeleteDialog();
  
  private Row pWidget = new Row();
  
  private FlowPanel pCount = new FlowPanel();
 
  private HorizontalPanel hpName = new HorizontalPanel();
  private FlowPanel fpName = new FlowPanel();
  private TextBox tbName = new TextBox();
  
  private HorizontalPanel hpDelete = new HorizontalPanel();
  private PushButton bDelete = new PushButton("X");
   
  private ThingTypeData thingTypeData = null;
  
  private boolean edit = false;
  
  private int row = 0;
  
  private int changeEvent = 0;
  
  public ThingType(ClientPersistence cp) {
    this.cp = cp;
    
    hpName.add(tbName);
    hpName.add(fpName);
    
    hpDelete.add(bDelete);
    
    initWidget(pWidget);
    
    tbName.setVisible(false);
    fpName.setVisible(true);
    
    pWidget.addChangeHandler(this);
    bDelete.addClickHandler(this);
    
    rpc = RpcCore.initRpc();
  }

  public void setData(int row, ThingTypeData thingTypeData) {
    this.row = row;
    this.thingTypeData = thingTypeData;
    pWidget.setRow(row);
    draw();
  }
  
  private void draw() {
    
    pWidget.clear();
    fpName.clear();
    
    // C0: count
    pCount.add(new HTML(Integer.toString(row)));
    
    // C1: id
    long id = thingTypeData.getId();
    HTML pId = new HTML(Long.toString(id));
    
    // C2: name
    String name = thingTypeData.getName();
    fpName.add(new HTML(name));
    
    // C3: delete button
    
    // Columns
    pWidget.add(pCount);
    pWidget.add(pId);
    pWidget.add(hpName);
    pWidget.add(hpDelete);
  }
  
  public Row getRow() {
    return pWidget;
  }
  
  public void edit(boolean b) {
    if (b == true) {
      tbName.setText(thingTypeData.getName());
      tbName.setVisible(true);
      fpName.setVisible(false);
    } else if (b == false) {
      String name = tbName.getText();
      thingTypeData.setName(name);
      fpName.clear();
      fpName.add(new HTML(name));
      tbName.setVisible(false);
    }
  }

  public ThingTypeData getData() {
    thingTypeData.setName(tbName.getText().trim());
    return thingTypeData;
  }
  
  private void delete() {
    wdelete.center();
    wdelete.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        DeleteDialog dd = (DeleteDialog) event.getSource();
        int changeEvent = dd.getChangeEvent();
        if (changeEvent == EventManager.DELETE_YES && thingTypeData.getId() > 0) {
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
      Window.alert("Wasn't able to delete thingType. Please try again?");
    }
  }
  
  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == pWidget) {
      int changeEvent = pWidget.getChangeEvent();
      if (changeEvent == EventManager.ROW_OVER) {
        edit(true);
        fireChange(changeEvent);
      } else if (changeEvent == EventManager.ROW_OUT) {
        edit(false);
        fireChange(changeEvent);
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
    }
    
  }
  
  private void deleteRpc() {
   
    rpc.deleteThingType(cp.getAccessToken(), thingTypeData, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean b) {
        deleteIt(b);
      }
      public void onFailure(Throwable caught) { 
      	cp.setRpcFailure(caught);
      }
    });
    
  }

}
