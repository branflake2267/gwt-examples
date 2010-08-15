package org.gonevertical.core.client.ui.admin.thingstufftype;

import org.gonevertical.core.client.global.Global_ListBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class ThingStuffValueTypeDropDown extends Composite implements ClickHandler {
	private HorizontalPanel horizontalPanel;
	private ListBox lbStuffValueTypes;
	private PushButton bDelete;
	
	public ThingStuffValueTypeDropDown() {
		initWidget(getHorizontalPanel());
		draw();
	}
	
	public void draw() {
		
		lbStuffValueTypes.clear();
		lbStuffValueTypes.addItem("", "0");
		
		lbStuffValueTypes.addItem("String", Integer.toString(ThingStuffTypeData.VALUETYPE_STRING));
		lbStuffValueTypes.addItem("Boolean", Integer.toString(ThingStuffTypeData.VALUETYPE_BOOLEAN));
		lbStuffValueTypes.addItem("Double", Integer.toString(ThingStuffTypeData.VALUETYPE_DOUBLE));
		lbStuffValueTypes.addItem("Integer", Integer.toString(ThingStuffTypeData.VALUETYPE_INT));
		
		lbStuffValueTypes.addItem("String Large", Integer.toString(ThingStuffTypeData.VALUETYPE_STRING_LARGE));
		lbStuffValueTypes.addItem("String Case", Integer.toString(ThingStuffTypeData.VALUETYPE_STRING_CASE));
		lbStuffValueTypes.addItem("String Large Case", Integer.toString(ThingStuffTypeData.VALUETYPE_STRING_LARGE_CASE));
		
		lbStuffValueTypes.addItem("HTML", Integer.toString(ThingStuffTypeData.VALUETYPE_HTML));
		lbStuffValueTypes.addItem("URL", Integer.toString(ThingStuffTypeData.VALUETYPE_URL));
		lbStuffValueTypes.addItem("Email", Integer.toString(ThingStuffTypeData.VALUETYPE_EMAIL));
		lbStuffValueTypes.addItem("Phone", Integer.toString(ThingStuffTypeData.VALUETYPE_PHONE));
		lbStuffValueTypes.addItem("Link", Integer.toString(ThingStuffTypeData.VALUETYPE_LINK));
		lbStuffValueTypes.addItem("File", Integer.toString(ThingStuffTypeData.VALUETYPE_FILE));
		
	}
	
	public long getSelected() {
		return Global_ListBox.getSelectedValue(lbStuffValueTypes);
	}
	
	public long getThingStuffValueTypeId() {
	  return getSelected();
  }
	
	public void hideDelete() {
		bDelete.setVisible(false);
	}
	
	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getListBox());
			horizontalPanel.add(getPushButton());
		}
		return horizontalPanel;
	}
	
	private ListBox getListBox() {
		if (lbStuffValueTypes == null) {
			lbStuffValueTypes = new ListBox();
			lbStuffValueTypes.setVisibleItemCount(1);
		}
		return lbStuffValueTypes;
	}
	
	private PushButton getPushButton() {
		if (bDelete == null) {
			bDelete = new PushButton("X");
			bDelete.addClickHandler(this);
		}
		return bDelete;
	}

	private void delete() {
		removeFromParent();
	}
	
  public void onClick(ClickEvent event) {

  	Widget sender = (Widget) event.getSource();
	  
  	if (sender == bDelete) {
  		delete();
  	}
  	
  }

	
	
	
}
