package org.gonevertical.core.client.ui.admin.thingstuff;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.global.DeleteDialog;
import org.gonevertical.core.client.global.EventManager;
import org.gonevertical.core.client.global.Global_Date;
import org.gonevertical.core.client.global.Global_ListBox;
import org.gonevertical.core.client.ui.Ui;
import org.gonevertical.core.client.ui.account.AccountData;
import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypeData;
import org.gonevertical.core.client.ui.admin.thingstufftype.ThingStuffTypesData;
import org.gonevertical.core.client.ui.widgets.Row;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class ThingStuff extends Ui implements ClickHandler, ChangeHandler, MouseOverHandler, MouseOutHandler {
  	  
  private int changeEvent = 0;
  
  private Row pWidget = new Row();
  
  private FlowPanel pId = new FlowPanel();
  private FlowPanel pParentId = new FlowPanel();
  
  private ListBox lbTypes = new ListBox();
  
  // inputs container
  private FlowPanel pInput = new FlowPanel();
  
  private FlowPanel pThingStuffCount = new FlowPanel();
  
  private TextBox tbValue = new TextBox();
  private CheckBox cbValue = new CheckBox();
  private TextArea taValue = new TextArea();
  
  private DateBox tbStartDt = new DateBox();
  private DateBox tbEndDt = new DateBox();
  private TextBox tbRank = new TextBox();
  
  private FlowPanel pCreatedBy = new FlowPanel();
  private FlowPanel pCreatedDt = new FlowPanel();
  private FlowPanel pUpdatedBy = new FlowPanel();
  private FlowPanel pUpdatedDt = new FlowPanel();
  private FlowPanel pOwner = new FlowPanel();
  
  private FlowPanel pThingLinkName = new FlowPanel();
  
  // parent owner
  private ThingData thingData = new ThingData();
  
  private ThingStuffData thingStuffData = null;
  
  // delete button
  private PushButton bDelete = new PushButton("X");

  // types choices - what kind of types, questions, attributes to choose from and then give them a value
  private ThingStuffTypesData thingStuffTypesData = null;

  // what is the rank/order/index of this widget in comparison to the to the others in the list
	private int index = 0;
	
	private int widgetType = 0;

	private SearchForThingLink wSearch;
	
	private PushButton bSearch = new PushButton("S");
	
	/**
	 * consturctor - init widget
	 * 
	 * @param cp
	 * @param widgetType - what type of widget, thingstuff or thingabout stuff
	 */
  public ThingStuff(ClientPersistence cp, int widgetType) {
    super(cp);
    this.widgetType = widgetType;
    
    wSearch = new SearchForThingLink(cp);
    
    HorizontalPanel hpSearch = new HorizontalPanel();
    hpSearch.add(bSearch);
    hpSearch.add(new HTML("&nbsp;"));
    hpSearch.add(pThingLinkName);
    
    
    // inputs of stuff, and then add another group of stuffs
    VerticalPanel vpInput = new VerticalPanel();
    vpInput.add(pInput);
    vpInput.add(hpSearch);
    
    
    HorizontalPanel hpButtons = new HorizontalPanel();
    hpButtons.add(bDelete);
    
    pWidget.add(pId, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(pParentId, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(lbTypes, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(vpInput, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(tbStartDt, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(tbEndDt, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(tbRank, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(pCreatedBy, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(pCreatedDt, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(pUpdatedBy, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(pUpdatedDt, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(pOwner, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(hpButtons, HorizontalPanel.ALIGN_CENTER);
    pWidget.add(pThingStuffCount, HorizontalPanel.ALIGN_CENTER);
    
    initWidget(pWidget);
    
    pWidget.addMouseOverHandler(this);
    pWidget.addMouseOutHandler(this);
    bDelete.addClickHandler(this);
    lbTypes.addChangeHandler(this);
    bSearch.addClickHandler(this);
    wSearch.addChangeHandler(this);
    
    pThingLinkName.setVisible(false);
    bSearch.setVisible(false);
    
    tbStartDt.setWidth("75px");
    tbEndDt.setWidth("75px");
    tbRank.setWidth("30px");
  }
  
  /**
   * set up the stuff data
   * 
   * @param thingData - owner
   * @param thingStuffTypesData - choices
   * @param thingStuffData - fill in saved data
   */
  public void setData(
  		int index,
  		ThingData thingData, 
  		ThingStuffTypesData thingStuffTypesData, 
  		ThingStuffData thingStuffData, int count) {
  	this.index = index;
    this.thingData = thingData;
    this.thingStuffData = thingStuffData;
    this.thingStuffTypesData = thingStuffTypesData;
    
    // use an empty object to store data in - TODO - maybe not do this
    if (thingStuffData == null) {
    	thingStuffData = new ThingStuffData();
    }
    
    drawId();
    
    drawParentId();
    
    // draw choices
    drawListBoxTypes();
    
    // draw input type - textbox, checkbox...
    drawInput();
    
    drawStuffCount();
    
    drawStartDt();
    
    drawEndDt();
    
    drawRank();
    
    drawCreatedBy();
    
    drawCreatedDt();
    
    drawUpdatedBy();
    
    drawUpdatedDt();
    
    drawOwners();
  }

	private void drawCreatedBy() {
		pCreatedBy.clear();
		String s = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		if (thingStuffData.getCreatedBy() > 0) {
			s = Long.toString(thingStuffData.getCreatedBy());
		}
		HTML h = new HTML(s);
		pCreatedBy.add(h);
  }

	private void drawCreatedDt() {
		pCreatedDt.clear();
	  String s = Global_Date.getDate_Eng(thingStuffData.getDateCreated());
	  HTML h = new HTML(s);
	  pCreatedDt.clear();
	  pCreatedDt.add(h);
  }

	private void drawUpdatedBy() {
		pUpdatedBy.clear();
		String s = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		if (thingStuffData.getUpdatedBy() > 0) {
			s = Long.toString(thingStuffData.getUpdatedBy());
		}
		HTML h = new HTML(s);
		pUpdatedBy.add(h);
  }

	private void drawUpdatedDt() {
		pUpdatedDt.clear();
	  String s = Global_Date.getDate_Eng(thingStuffData.getDateUpdated());
	  HTML h = new HTML(s);
	  pUpdatedDt.clear();
	  pUpdatedDt.add(h);
  }

	private void drawOwners() {
		pOwner.clear();
		String s = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		if (thingStuffData.getOwners() == null || thingStuffData.getOwners().length == 0) {
			s = "";
		} else {
			for (int i=0; i < thingStuffData.getOwners().length; i++) {
				s += Long.toString(thingStuffData.getOwners()[i]);
				if (i < thingStuffData.getOwners().length -1) {
					s += ",";
				}
			}
		}
		HTML h = new HTML(s);
		pOwner.add(h);
  }

	private void drawStartDt() {
		tbStartDt.setValue(thingStuffData.getStartOf());
  }
  
  private void drawEndDt() {
  	tbEndDt.setValue(thingStuffData.getEndOf());
  }

	private void drawRank() {
  	String s = "0";
  	if (thingStuffData.getRank() != null) {
  		s = Double.toString(thingStuffData.getRank());
  	} 
  	tbRank.setText(s);
  }

	private void drawStuffCount() {
  	pThingStuffCount.clear();
  	String s = "&nbsp;";
    if (widgetType == ThingStuffs.WIDGETTYPE_THINGSTUFF) {
    	if (thingStuffData.getChildStuffs() != null && 	thingStuffData.getChildStuffs().getThingStuffData() != null) {
    		s = Long.toString(thingStuffData.getChildStuffs().getThingStuffData().length);
    	}
    }
    pThingStuffCount.add(new HTML(s));
  }

	private void drawId() {
  	pId.clear();
  	long id = thingStuffData.getStuffId();
  	String s = "";
  	if (id != 0) {
  		s = Long.toString(id);
  	} else {
  		s = "&nbsp;&nbsp;";
  	}
  	HTML h = new HTML(s);
  	pId.add(h);
  }
	
	private void drawParentId() {
  	pParentId.clear();
  	long id = thingStuffData.getParentStuffId();
  	String s = "";
  	if (id != 0) {
  		s = Long.toString(id);
  	} else {
  		s = "&nbsp;&nbsp;";
  	}
  	HTML h = new HTML(s);
  	pParentId.add(h);
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
    
    ThingStuffTypeData[] tstds = thingStuffTypesData.getThingStuffTypeData();
    if (tstds == null || tstds.length == 0) {
    	return;
    }
    
    for (int i=0; i < tstds.length; i++) {
      String item = tstds[i].getName();
      String value = Long.toString(tstds[i].getStuffTypeId());
      lbTypes.addItem(item, value);
      
      //debug
      //System.out.println("item: " + item + " value: " + value);
    }
        
    long sel = thingStuffData.getStuffTypeId();
    Global_ListBox.setSelected(lbTypes, (int) sel);
  }
  
  private void drawInput() {
    pInput.clear();
    pThingLinkName.clear();
      
    long typeId = getDataTypeId();
    if (typeId == ThingStuffTypeData.VALUETYPE_STRING) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_BOOLEAN) {
      drawInput(thingStuffData.getValueBol());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_DOUBLE) {
      drawInput(thingStuffData.getValueDouble());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_INT) {
      drawInput(thingStuffData.getValueLong());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_LARGE) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_CASE) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_LARGE_CASE) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_HTML) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_URL) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_EMAIL) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_PHONE) {
      drawInput(typeId, thingStuffData.getValue());
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_LINK) {
    	drawInput(thingStuffData.getValueLong());
    	bSearch.setVisible(true);
    	if (thingStuffData != null) {
    		drawThingLinkName(thingStuffData.getValueLong());
    	}
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_FILE_BLOB) { 
    	
    	
    } else {
      drawInputBlank();
    }

    
  }
  
  private void drawInputBlank() {
    pInput.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
  }
  
  private void drawInput(long typeId, String value) {
    Widget w = null;
    if (typeId == ThingStuffTypeData.VALUETYPE_STRING) {
      w = tbValue;
      tbValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_LARGE) {
      w = taValue;
      taValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_CASE) {
      w = tbValue;
      tbValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_LARGE_CASE) {
      w = taValue;
      taValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VALUETYPE_HTML) {
      w = taValue;
      taValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VALUETYPE_URL) {
      w = tbValue;
      tbValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VALUETYPE_EMAIL) {
      w = tbValue;
      tbValue.setText(value);
    } else if (typeId == ThingStuffTypeData.VALUETYPE_PHONE) {
      w = tbValue;
      tbValue.setText(value);
      
    } else if (typeId == ThingStuffTypeData.VALUETYPE_LINK) {
      w = tbValue;
      tbValue.setText(value);
      
    }
    
    if (w != null) {
      pInput.add(w);
    }
    
    
  }
  
  private void drawThingLinkName(Long l) {
  	if (l == null) {
  		System.out.println("hmmm drawThingLinkName has null");
  		return;
  	}
  	if (l > 0) {
  		getThingDataRpc(l);	
  	}
  }

	public ThingStuffData getData() {
    
    String value = null;
    Boolean valueBol = false;
    Double valueDouble = null; 
    Long valueLong = null;
    
    long typeId = getDataTypeId();
    if (typeId == ThingStuffTypeData.VALUETYPE_STRING) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_BOOLEAN) {
      value = null;
      valueBol = cbValue.getValue();
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_DOUBLE) {
      value = null;
      valueBol = false;
      valueDouble = getTextBox_Double();
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_INT) {
      value = null;
      valueBol = false;
      valueDouble = null;
      valueLong = getTextBox_Long();
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_LARGE) {
      value = getTextArea_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_CASE) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_STRING_LARGE_CASE) {
      value = getTextArea_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_HTML) {
      value = getTextArea_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_URL) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_EMAIL) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_PHONE) {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    } else if (typeId == ThingStuffTypeData.VALUETYPE_LINK) {
      value = null;
      valueBol = false;
      valueDouble = null;
      valueLong = getTextBox_Long();
    } else {
      value = getTextBox_String();
      valueBol = false;
      valueDouble = null;
      valueLong = null;
    }
    
    long stuffTypeId = Global_ListBox.getSelectedValue(lbTypes);
    thingStuffData.setStuffTypeId(stuffTypeId);
    thingStuffData.setValue(value);
    thingStuffData.setValue(valueBol);
    
    // TODO - doubleVal still comes through
    // not able to use the setters for this b/c it won't carry the variables values through the cast 2/7/2010
    //thingStuffData.setValue(valueDouble);
    //thingStuffData.setValue(valueInt);
    thingStuffData.setValue(getTextBox_Double());
    thingStuffData.setValue(getTextBox_Long());
    
    thingStuffData.setParentThingId(thingData.getThingId());
    
    // TODO no need, already sets on mousing
    // multi dem format for stuff having stuff
    //thingStuffData.setThingStuffsAbout(thingStuffData.getThingStuffsAbout());
    
    thingStuffData.setStartOf(tbStartDt.getValue());
    thingStuffData.setEndOf(tbEndDt.getValue());
    thingStuffData.setRank(getRank());
    
    return thingStuffData;
  }

  private Double getRank() {
	  String s = tbRank.getValue().trim();
	  Double d = null;
	  if (s.length() != 0) {
	  	try {
	      d = Double.parseDouble(s);
      } catch (NumberFormatException e) {
      	d = null;
      }
	  }
	  return d;
  }

	private String getTextBox_String() {
  	String s = null;
  	if (tbValue.getText().trim().length() > 0) {
  		s = tbValue.getText().trim();
  	}
    return s;
  }
  
  private String getTextArea_String() {
  	String s = null;
  	if (tbValue.getText().trim().length() > 0) {
  		s = tbValue.getText().trim();
  	}
    return s;
  }
  
  private Long getTextBox_Long() {
    String s = tbValue.getText().trim();
    Long l = null;
    try {
      l = Long.parseLong(s);
    } catch (NumberFormatException e) {
      l = null;
    }
    return l;
  }
  
  private Double getTextBox_Double() {
    String s = tbValue.getText().trim();
    Double d = null;
    try {
      d = Double.parseDouble(s);
    } catch (NumberFormatException e) {
      d = null;
    }
    return d;
  }

  private void drawInput(Boolean valueBol) {
  	if (valueBol == null) {
  		valueBol = new Boolean(false);
  	}
    pInput.add(cbValue);
    cbValue.setValue(valueBol);
  }

  private void drawInput(Double valueDouble) {
  	if (valueDouble == null) {
  		valueDouble = new Double(0.0);
  	}
    pInput.add(tbValue);
    tbValue.setText(Double.toString(valueDouble));
  }

  private void drawInput(Long valueLong) {
  	if (valueLong == null) {
  		valueLong = new Long(0);
  	}
    pInput.add(tbValue);
    tbValue.setText(Long.toString(valueLong));
  }

  private long getDataTypeId() {
  	if (lbTypes == null) {
  		return -1;
  	}
    long thingStuffId = Global_ListBox.getSelectedValue(lbTypes);
    if (thingStuffId == -1) {
    	return -1;
    }
    ThingStuffTypeData type = thingStuffTypesData.getStuffTypeData(thingStuffId);
    long typeId = 0;
    if (type == null) {
      typeId = 0;
    } else {
      typeId = type.getValueTypeId();
    }
    return typeId;
  }
  
  private void changeType() {
    long dataTypeId = getDataTypeId();
    drawInput();
    resizeInput(dataTypeId);
    fireChange(EventManager.THINGSTUFF_TYPECHANGE);
  }
  
  public Row getRow() {
    return pWidget;
  }
  
  private void resizeInput(long dataTypeId) {

    int wt = tbValue.getOffsetWidth();
    int wtc = cbValue.getOffsetWidth();
    int wta = taValue.getOffsetWidth();
    int width = 0;
    if (dataTypeId == ThingStuffTypeData.VALUETYPE_STRING) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_BOOLEAN) {
      width = wtc;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_DOUBLE) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_INT) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_STRING_LARGE) {
      width = wta;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_STRING_CASE) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_STRING_LARGE_CASE) {
      width = wta;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_HTML) {
      width = wta;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_URL) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_EMAIL) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_PHONE) {
      width = wt;
    } else if (dataTypeId == ThingStuffTypeData.VALUETYPE_LINK) {
      width = wt;
    } else {
      width = wt;
    } 
    
    // adjust input sizes too
    if (width > 50) {
    	pInput.setWidth(width + "px");
    	tbValue.setWidth(width + "px");
    	taValue.setWidth(width + "px");
    }
    
    //System.out.println("resize: " + width);
  }
  
  private void delete() {
    wdelete.center();
    wdelete.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        
      	DeleteDialog dd = (DeleteDialog) event.getSource();
        
        int changeEvent = dd.getChangeEvent();
        
        if (changeEvent == EventManager.DELETE_YES && thingStuffData.getStuffId() > 0) {
        
        	deleteRpcThingStuff(thingStuffData.getStuffId());
        		
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
    
    // redraw so the array is correct in list
    fireChange(EventManager.THINGSTUFF_REDRAW);
  }
  
  private void setLink() {
  	long thingId = wSearch.getSelectedThingId();
  	tbValue.setValue(Long.toString(thingId));
  	getThingDataRpc(thingId);
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
      
    } else if (sender == bSearch) {
    	wSearch.center();
    	wSearch.draw();
    }
  }

  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == lbTypes) {
      changeType();
    } else if (sender == wSearch) {
    	setLink();
    }
  }

  public void onMouseOver(MouseOverEvent event) {
  	Widget sender = (Widget) event.getSource();
  	
  	if (sender == pWidget) {
  		fireChange(EventManager.THINGSTUFFABOUT_MOUSEOVER);
  	}
  }

  public void onMouseOut(MouseOutEvent event) {
  	Widget sender = (Widget) event.getSource();
	  
  	if (sender == pWidget) {
  		fireChange(EventManager.THINGSTUFFABOUT_HIDE);
  	}
  }
  
	public ThingStuffData getThingStuffData() {
	  return thingStuffData;
  }

	public int getIndex() {
		return index;
	}

	public void setAboutStuff(ThingStuffData[] tsd) {
		
		ThingStuffsData tssd = new ThingStuffsData();
		tssd.setThingStuffData(tsd);
		
		thingStuffData.setThingStuffChilds(tssd);
  }
	
  public void setWidgetType(int widgetType) {
  	this.widgetType = widgetType;
  }
  
  private void processLink(AccountData ad) {
  	pThingLinkName.clear();
  	pThingLinkName.setVisible(true);
  	
  	
  	String n = "";
  	if (ad == null || ad.getThingData() == null) {
  		n = "Couldn't find";
  		
  	} else {
  		
  		ThingStuffData[] t = ad.getNames();
  		if (t != null) {
    		for (int i=0; i < t.length; i++) {
    			if (t[i] != null) { 
      			n += t[i].getValue();
      			if (i < n.length()-1) {
      				n += ", ";
      			}
    			}
    		}
  		}
  		
  		if (ad != null && ad.getThingData() != null) {
  			String un = ad.getThingData().getKey();
  			if (un != null) {
  				n += un;
  			}
  		}
  		
  	}
		HTML h = new HTML(n);
		pThingLinkName.add(h);
  }

  public void deleteRpcThingStuff(long stuffId) {
    rpc.deleteThingStuffData(cp.getAccessToken(), stuffId, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean b) {
        deleteIt(b);
      }
      public void onFailure(Throwable caught) {
      	cp.setRpcFailure(caught);
      }
    });
  }
  
  private void getThingDataRpc(long thingId) {
  	cp.showLoading(true);
  	rpc.getAccountData(cp.getAccessToken(), thingId, new AsyncCallback<AccountData>() {
			public void onSuccess(AccountData result) {
				processLink(result);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
				cp.setRpcFailure(caught);
			}
		});
  }
  
}
