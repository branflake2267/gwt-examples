package org.gonevertical.core.client.ui.admin.thingstufftype;

import org.gonevertical.core.client.global.Global_ListBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class ThingStuffTypeDropDown extends Composite implements ClickHandler {
	private HorizontalPanel horizontalPanel;
	private ListBox lbStuffTypes;
	private PushButton bDelete;
	
	public ThingStuffTypeDropDown() {
		initWidget(getHorizontalPanel());
	}
	
	public void draw(ThingStuffTypesData thingStuffTypesData) {
		
		lbStuffTypes.clear();
		lbStuffTypes.addItem("", "0");
		
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

		for (int i=0; i < thingStuffTypeData.length; i++){
			String item = thingStuffTypeData[i].getName();
			String value = Long.toString(thingStuffTypeData[i].getStuffTypeId());
			lbStuffTypes.addItem(item, value);
		}

	}
	
	public long getSelected() {
		return Global_ListBox.getSelectedValue(lbStuffTypes);
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
		if (lbStuffTypes == null) {
			lbStuffTypes = new ListBox();
			lbStuffTypes.setVisibleItemCount(1);
		}
		return lbStuffTypes;
	}
	
	private PushButton getPushButton() {
		if (bDelete == null) {
			bDelete = new PushButton("X");
			bDelete.addClickHandler(this);
		}
		return bDelete;
	}

	private void delete() {
		bDelete.removeFromParent();
	}
	
  public void onClick(ClickEvent event) {

  	Widget sender = (Widget) event.getSource();
	  
  	if (sender == bDelete) {
  		delete();
  	}
  	
  }
	
	
}
