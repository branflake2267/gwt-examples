package com.gawkat.core.client.global;

import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class HorizontalPanelEvent extends HorizontalPanel implements HasAllMouseHandlers{

	public HorizontalPanelEvent() {
		sinkEvents(Event.MOUSEEVENTS);
	}

  public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
	  return addHandler(handler, MouseDownEvent.getType());
  }

  public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
	  return addHandler(handler, MouseUpEvent.getType());
  }

  public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
	  return addHandler(handler, MouseOutEvent.getType());
  }

  public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
	  return addHandler(handler, MouseOverEvent.getType());
  }

  public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
	  return addHandler(handler, MouseMoveEvent.getType());
  }

  public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
	  return addHandler(handler, MouseWheelEvent.getType());
  }
	
}
