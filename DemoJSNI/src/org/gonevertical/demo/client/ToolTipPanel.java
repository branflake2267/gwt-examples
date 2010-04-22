package org.gonevertical.demo.client;

import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class ToolTipPanel extends AbsolutePanel implements HasMouseOverHandlers, HasMouseOutHandlers {

	public ToolTipPanel() {

		sinkEvents(Event.MOUSEEVENTS);
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addHandler(handler, MouseOutEvent.getType());
	}

	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addHandler(handler, MouseOverEvent.getType());
	}

}
