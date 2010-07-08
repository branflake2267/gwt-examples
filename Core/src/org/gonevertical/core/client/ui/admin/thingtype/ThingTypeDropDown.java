package org.gonevertical.core.client.ui.admin.thingtype;

import org.gonevertical.core.client.global.Global_ListBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class ThingTypeDropDown extends Composite implements ClickHandler {
	private HorizontalPanel horizontalPanel;
	private ListBox lbStuffTypes;
	private PushButton bDelete;
	
	public ThingTypeDropDown() {
		initWidget(getHorizontalPanel());
	}
	
	public void draw(ThingTypesData thingTypesData) {
		
		lbStuffTypes.clear();
		lbStuffTypes.addItem("", "0");
		
		if (thingTypesData == null) {
			return;
		}

		if (thingTypesData == null) {
			return;
		}
		ThingTypeData[] thingTypeData = thingTypesData.getThingTypeData();

		if (thingTypeData.length == 0) {
			return;
		}

		for (int i=0; i < thingTypeData.length; i++){
			String item = thingTypeData[i].getName();
			String value = Long.toString(thingTypeData[i].getId());
			lbStuffTypes.addItem(item, value);
		}

	}
	
	public long getSelected() {
		return Global_ListBox.getSelectedValue(lbStuffTypes);
	}
	
	public long getThingTypeId() {
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
		removeFromParent();
	}
	
  public void onClick(ClickEvent event) {

  	Widget sender = (Widget) event.getSource();
	  
  	if (sender == bDelete) {
  		delete();
  	}
  	
  }

	
	
	
}
