package com.gawkat.core.client.account.thingstuff;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.Row;
import com.gawkat.core.client.account.thing.ThingData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.gawkat.core.client.global.DeleteDialog;
import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.Global_ListBox;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.server.rpc.RPC;

public class ThingStuff extends Composite implements ClickHandler, ChangeHandler {
  
  private ClientPersistence cp = null;
  
  private RpcCoreServiceAsync rpc;
  
  private int changeEvent = 0;
  
  private Row pWidget = new Row();

  private DeleteDialog wdelete = new DeleteDialog();
  
  private ListBox lbTypes = new ListBox();
  
  // inputs container
  private FlowPanel pInput = new FlowPanel();
  
  // about stuff container
  private FlowPanel pAbout = new FlowPanel();
  
  private TextBox tbValue = new TextBox();
  private CheckBox cbValue = new CheckBox();
  private TextArea taValue = new TextArea();

  // owner
  private ThingData thingData = new ThingData();
  
  private ThingStuffData thingStuffData = null;
  
  private PushButton bDelete = new PushButton("X");

  private ThingStuffTypesData thingStuffTypesData = null;

  /**
   * constructor - setup the widget
   * 
   * @param cp
   */
  public ThingStuff(ClientPersistence cp) {
    this.cp = cp;
    
    // inputs of stuff, and then add another group of stuffs
    VerticalPanel vpInput = new VerticalPanel();
    vpInput.add(pInput);
    vpInput.add(pAbout);
    
    HorizontalPanel hpButtons = new HorizontalPanel();
    hpButtons.add(bDelete);
    
    pWidget.add(lbTypes);
    pWidget.add(vpInput);
    pWidget.add(hpButtons);
    
    initWidget(pWidget);
    
    bDelete.addClickHandler(this);
    lbTypes.addChangeHandler(this);
    
    rpc = RpcCore.initRpc();
  }
  
  /**
   * set up the stuff data
   * 
   * @param thingData - owner
   * @param thingStuffTypesData - choices
   * @param thingStuffData - fill in saved data
   */
  public void setData(
  		ThingData thingData, 
  		ThingStuffTypesData thingStuffTypesData, 
  		ThingStuffData thingStuffData) {
  	
    this.thingData = thingData;
    this.thingStuffData = thingStuffData;
    this.thingStuffTypesData = thingStuffTypesData;
    
    // draw choices
    drawListBoxTypes();
    
    // draw input type - textbox, checkbox...
    drawInput();
  }
  
  /**
   * draw choices of types
   */
  private void drawListBoxTypes() {
    lbTypes.clear();
    
    if (thingStuffTypesData == null) {
      return;
    }
    
    lbTypes.addItem("Select", "0");
    
    for (int i=0; i < thingStuffTypesData.thingStuffTypeData.length; i++) {
      String item = thingStuffTypesData.thingStuffTypeData[i].getName();
      String value = Long.toString(thingStuffTypesData.thingStuffTypeData[i].getStuffTypeId());
      lbTypes.addItem(item, value);
      
      //debug
      System.out.println("item: " + item + " value: " + value);
    }
        
    long sel = thingStuffData.getThingStuffTypeId();
    Global_ListBox.setSelected(lbTypes, (int) sel);
  }
  
  private void drawInput() {
    pInput.clear();
      
    long typeId = getDataTypeId();
    if (typeId == ThingStuffTypeData.VT_STRING) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VT_BOOLEAN) {
      drawInput(thingStuffData.getValueBol());
      
    } else if (typeId == ThingStuffTypeData.VT_DOUBLE) {
      drawInput(thingStuffData.getValueDouble());
      
    } else if (typeId == ThingStuffTypeData.VT_INT) {
      drawInput(thingStuffData.getValueInt());
      
    } else if (typeId == ThingStuffTypeData.VT_STRING_LARGE) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VT_STRING_CASE) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VT_STRING_LARGE_CASE) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VT_HTML) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VT_URL) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VT_EMAIL) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VT_PHONE) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VT_LINK) {
    	drawInput(thingStuffData.getValueInt());
      
    } else {
      drawInputBlank();
    }
  }
  
  private void drawInputBlank() {
    pInput.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
  }
  
  private void drawInput(long typeId, String value) {
    Widget w = null;
    if (typeId == ThingStuffTypeData.VT_STRING) {
      w = tbValue;
      tbValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VT_STRING_LARGE) {
      w = taValue;
      taValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VT_STRING_CASE) {
      w = tbValue;
      tbValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VT_STRING_LARGE_CASE) {
      w = taValue;
      taValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VT_HTML) {
      w = taValue;
      taValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VT_URL) {
      w = tbValue;
      tbValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VT_EMAIL) {
      w = tbValue;
      tbValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VT_PHONE) {
      w = tbValue;
      tbValue.setText(value);
      
    } else if (typeId == ThingStuffTypeData.VT_LINK) {
      w = tbValue;
      tbValue.setText(value);
    }
    
    if (w != null) {
      pInput.add(w);
    }
    
  }
  
  public ThingStuffData getData() {
    
    String value = null;
    boolean valueBol = false;
    double valueDouble = 0;
    long valueInt = 0;
    
    long typeId = getDataTypeId();
    if (typeId == ThingStuffTypeData.VT_STRING) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_BOOLEAN) {
      value = null;
      valueBol = cbValue.getValue();
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_DOUBLE) {
      value = null;
      valueBol = false;
      valueDouble = getTextBox_Double();
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_INT) {
      value = null;
      valueBol = false;
      valueDouble = 0;
      valueInt = getTextBox_Long();
    } else if (typeId == ThingStuffTypeData.VT_STRING_LARGE) {
      value = getTextArea_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_STRING_CASE) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_STRING_LARGE_CASE) {
      value = getTextArea_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_HTML) {
      value = getTextArea_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_URL) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_EMAIL) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_PHONE) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    } else if (typeId == ThingStuffTypeData.VT_LINK) {
      value = null;
      valueBol = false;
      valueDouble = 0;
      valueInt = getTextBox_Long();
    } else {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = 0;
      valueInt = 0;
    }
    
    System.out.println(valueInt);
    
    int stuffTypeId = Global_ListBox.getSelectedValue(lbTypes);
    thingStuffData.setThingStuffTypeId(stuffTypeId);
    thingStuffData.setValue(value);
    thingStuffData.setValue(valueBol);
    
    // TODO - doubleVal still comes through
    // not able to use the setters for this b/c it won't carry the variables values through the cast 2/7/2010
    //thingStuffData.setValue(valueDouble);
    //thingStuffData.setValue(valueInt);
    thingStuffData.valueDouble = getTextBox_Double();
    thingStuffData.valueInt = getTextBox_Long();
    
    thingStuffData.setThingId(thingData.getThingId());
    
    return thingStuffData;
  }

  private String getTextBox_String() {
    return tbValue.getText().trim();
  }
  
  private String getTextArea_String() {
    return taValue.getText().trim();
  }
  
  private long getTextBox_Long() {
    String s = tbValue.getText().trim();
    long l = 0;
    try {
      l = Long.parseLong(s);
    } catch (NumberFormatException e) {
      l = 0;
    }
    return l;
  }
  
  private double getTextBox_Double() {
    String s = tbValue.getText().trim();
    double d = 0.0;
    try {
      d = Double.parseDouble(s);
    } catch (NumberFormatException e) {
      d = 0;
    }
    return d;
  }

  private void drawInput(boolean valueBol) {
    pInput.add(cbValue);
    cbValue.setValue(valueBol);
  }

  private void drawInput(double valueDouble) {
    pInput.add(tbValue);
    tbValue.setText(Double.toString(valueDouble));
  }

  private void drawInput(long valueInt) {
    pInput.add(tbValue);
    tbValue.setText(Long.toString(valueInt));
  }

  private int getDataTypeId() {
    int thingStuffId = Global_ListBox.getSelectedValue(lbTypes);
    ThingStuffTypeData type = thingStuffTypesData.getStuffTypeData(thingStuffId);
    int typeId = 0;
    if (type == null) {
      typeId = 0;
    } else {
      typeId = type.getValueTypeId();
    }
    return typeId;
  }
  
  private void changeType() {
    int dataTypeId = getDataTypeId();
    drawInput();
    resizeInput(dataTypeId);
    fireChange(EventManager.THINGSTUFF_TYPECHANGE);
  }
  

  public Row getRow() {
    return pWidget;
  }
  
  private void resizeInput(int dataTypeId) {

    int wt = tbValue.getOffsetWidth();
    int wtc = cbValue.getOffsetWidth();
    int wta = taValue.getOffsetWidth();
    int width = 0;
    if (dataTypeId == ThingStuffTypeData.VT_STRING) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VT_BOOLEAN) {
      width = wtc;
    } else if (dataTypeId == ThingStuffTypeData.VT_DOUBLE) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VT_INT) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VT_STRING_LARGE) {
      width = wta;
    } else if (dataTypeId == ThingStuffTypeData.VT_STRING_CASE) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VT_STRING_LARGE_CASE) {
      width = wta;
    } else if (dataTypeId == ThingStuffTypeData.VT_HTML) {
      width = wta;
    } else if (dataTypeId == ThingStuffTypeData.VT_URL) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VT_EMAIL) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VT_PHONE) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VT_LINK) {
      width = wt;
    } else {
      width = wt;
    } 
    
    if (width > 50) {
    	pInput.setWidth(width + "px");
    	tbValue.setWidth(width + "px");
    	taValue.setWidth(width + "px");
    }
    
    System.out.println("resize: " + width);
  }
  
  private void delete() {
    wdelete.center();
    wdelete.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        DeleteDialog dd = (DeleteDialog) event.getSource();
        int changeEvent = dd.getChangeEvent();
        if (changeEvent == EventManager.DELETE_YES && thingStuffData.getId() > 0) {
          deleteRpc(thingStuffData.getId());
        } else {
          deleteIt(true);
        }
      }
    });
  }
  
  private void deleteIt(boolean b) {
    if (b == true) {
      this.removeFromParent();
    } else {
      Window.alert("Wasn't able to delete it. Please try again?");
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

  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == lbTypes) {
      changeType();
    }
  }
  
  private void deleteRpc(long thingStuffId) {
    
    rpc.deleteThingStuffData(cp.getAccessToken(), thingStuffId, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean b) {
        deleteIt(b);
      }
      public void onFailure(Throwable caught) {
        // TODO
      }
    });
  }
 
}
