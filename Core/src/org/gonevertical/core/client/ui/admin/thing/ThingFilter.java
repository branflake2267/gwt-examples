package org.gonevertical.core.client.ui.admin.thing;

import java.util.ArrayList;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeDataFilter;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypeDropDown;
import org.gonevertical.core.client.ui.admin.thingtype.ThingTypesData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ThingFilter extends Composite implements ClickHandler {

	private ClientPersistence cp;

	private RpcCoreServiceAsync rpc;

	private VerticalPanel pWidget = new VerticalPanel();
	
	private PushButton bAddType;
	
	private VerticalPanel pTypes;
	
	private ThingTypeDropDown thingTypeDropDown;

	private ThingTypesData thingTypesData;
	private HorizontalPanel horizontalPanel;

	/**
	 * filter by one thingId, for example for linkers
	 */
	private long thingIdLinker;
	
	/**
	 * constructor - init filter widget
	 * @param cp
	 */
	public ThingFilter(ClientPersistence cp) {
		this.cp = cp;

		initWidget(pWidget);
		pWidget.setWidth("100%");
		pWidget.add(getHorizontalPanel());

		rpc = RpcCore.initRpc();
	}
	
	public void setLinkers(long thingIdLinker) {
		this.thingIdLinker = thingIdLinker;
  }

	public void draw() {
		ThingTypeDataFilter filter = new ThingTypeDataFilter();
		filter.setLimit(0, 200);
		getThingTypesRpc(filter);
	}
	
	public long[] getThingTypeIds() {
	  
		int c = pTypes.getWidgetCount();
		if (c == 0) {
			return null;
		}
		
		ArrayList<Long> al = new ArrayList<Long>();
		for (int i=0; i < c; i++) {
			ThingTypeDropDown w = (ThingTypeDropDown) pTypes.getWidget(i);
			long l = w.getThingTypeId();
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

	protected void process(ThingTypesData thingTypesData) {
		this.thingTypesData = thingTypesData;

		if (thingTypeDropDown != null) {
			thingTypeDropDown.draw(thingTypesData);
		}
	}	

	private void getThingTypesRpc(ThingTypeDataFilter filter) {

		cp.showLoading(true);

		rpc.getThingTypes(cp.getAccessToken(), filter, new AsyncCallback<ThingTypesData>() {
			public void onSuccess(ThingTypesData thingTypesData) {
				process(thingTypesData);
				cp.showLoading(false);
			}
			public void onFailure(Throwable caught) {
			}
		});

	}
	
	private PushButton getPushButton() {
		if (bAddType == null) {
			bAddType = new PushButton("Add Filter By Type");
			bAddType.addClickHandler(this);
		}
		return bAddType;
	}
	
	private VerticalPanel getPTypes() {
		if (pTypes == null) {
			pTypes = new VerticalPanel();
			pTypes.add(getThingTypeDropDown());
		}
		return pTypes;
	}
	
	private ThingTypeDropDown getThingTypeDropDown() {
		if (thingTypeDropDown == null) {
			thingTypeDropDown = new ThingTypeDropDown();
			thingTypeDropDown.hideDelete();
		}
		return thingTypeDropDown;
	}

	private void addType() {

		ThingTypeDropDown dd = new ThingTypeDropDown();
		dd.draw(thingTypesData);
		pTypes.add(dd);

	}

	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();

		if (sender == bAddType) {
			addType();
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

	public long getThingIdLinker() {
	  return thingIdLinker;
  }




}
