package org.gonevertical.core.client.widget;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WidgetAttrDataFilter implements IsSerializable {

	private int thingId_Widget;

	public WidgetAttrDataFilter() {
	}
	
	public void setData(int thingId_Widget) {
		this.thingId_Widget = thingId_Widget;
	}
	
	public long getThingId_Widget() {
		return thingId_Widget;
	}
	
}
