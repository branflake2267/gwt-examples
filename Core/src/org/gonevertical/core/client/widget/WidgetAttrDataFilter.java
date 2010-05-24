package org.gonevertical.core.client.widget;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WidgetAttrDataFilter implements IsSerializable {

	private long thingId_Widget;

	public WidgetAttrDataFilter() {
	}
	
	public void setData(long thingIdWidget) {
		this.thingId_Widget = thingIdWidget;
	}
	
	public long getThingId_Widget() {
		return thingId_Widget;
	}
	
}
