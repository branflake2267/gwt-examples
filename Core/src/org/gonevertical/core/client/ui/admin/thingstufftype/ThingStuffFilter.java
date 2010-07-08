package org.gonevertical.core.client.ui.admin.thingstufftype;

import java.util.ArrayList;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeDataFilter;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeDropDown;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypesData;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class ThingStuffFilter extends Composite implements ClickHandler {

	private ClientPersistence cp;

	private RpcCoreServiceAsync rpc;

	private VerticalPanel pWidget = new VerticalPanel();
	private PushButton bAddType;
	private VerticalPanel pTypes;
	
	private HorizontalPanel horizontalPanel;

	private ThingStuffValueTypeDropDown thingStuffValueTypeDropDown;

	public ThingStuffFilter(ClientPersistence cp) {
		this.cp = cp;

		initWidget(pWidget);
		pWidget.setWidth("100%");
		pWidget.add(getHorizontalPanel());

		rpc = RpcCore.initRpc();
	}

	public void draw() {
		
	}
	
	public long[] getThingStuffValueTypeIds() {
	  
		int c = pTypes.getWidgetCount();
		if (c == 0) {
			return null;
		}
		
		ArrayList<Long> al = new ArrayList<Long>();
		for (int i=0; i < c; i++) {
			ThingStuffValueTypeDropDown w = (ThingStuffValueTypeDropDown) pTypes.getWidget(i);
			long l = w.getThingStuffValueTypeId();
			if (l > 0) {
				al.add(l);
			}
		}
		
		if (al.size() == 0) {
			return null;
		}
		
		long[] aal = new long[al.size()];
		for (int i=0; i < al.size(); i++) {
			aal[i] = al.get(i);
		}
		
	  return aal;
  }
	
	private PushButton getPushButton() {
		if (bAddType == null) {
			bAddType = new PushButton("Add Filter by ValueType");
			bAddType.addClickHandler(this);
		}
		return bAddType;
	}
	
	private VerticalPanel getPTypes() {
		if (pTypes == null) {
			pTypes = new VerticalPanel();
			pTypes.add(getThingStuffValueTypeDropDown());
		}
		return pTypes;
	}
	
	private ThingStuffValueTypeDropDown getThingStuffValueTypeDropDown() {
		if (thingStuffValueTypeDropDown == null) {
			thingStuffValueTypeDropDown = new ThingStuffValueTypeDropDown();
			thingStuffValueTypeDropDown.hideDelete();
		}
		return thingStuffValueTypeDropDown;
	}

	private void addType(boolean hideDelete) {

		ThingStuffValueTypeDropDown dd = new ThingStuffValueTypeDropDown();
		dd.draw();
		if (hideDelete == true) {
			dd.hideDelete();
		}
		pTypes.add(dd);

	}

	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();

		if (sender == bAddType) {
			addType(false);
		}
	}
	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getPTypes());
			horizontalPanel.add(getPushButton());
			horizontalPanel.setCellVerticalAlignment(getPushButton(), HasVerticalAlignment.ALIGN_BOTTOM);
			horizontalPanel.setCellHorizontalAlignment(getPushButton(), HasHorizontalAlignment.ALIGN_CENTER);
		}
		return horizontalPanel;
	}


}
