package com.gawkat.gwt.example_handler.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BoxRight extends Composite implements ClickHandler {

  private VerticalPanel pWidget = new VerticalPanel();

  private PushButton bRight = new PushButton("RightBox");

  public BoxRight() {

    HorizontalPanel hp = new HorizontalPanel();
    hp.setSpacing(4);
    hp.add(bRight);

    pWidget.add(hp);

    initWidget(pWidget);

    // observe clicks on button
    bRight.addClickHandler(this);

    pWidget.addStyleName("right");
    pWidget.setCellHorizontalAlignment(hp, HorizontalPanel.ALIGN_CENTER);
  }

  public void setColor(String color) {
    DOM.setStyleAttribute(pWidget.getElement(), "background", color);
  }

  public void onClick(ClickEvent event) {

    Widget sender = (Widget) event.getSource();

    if (sender == bRight) {
      fireChange();
    }

    // this will fire the click event again, recursion happens if used here
    // this.fireEvent(event);
  }
  
  /**
   * fire a change event
   */
  private void fireChange() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }

  /**
   * when firing a change event, this handler is used
   */
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  /**
   * allows for observing click events on the entire widget
   */
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }

}
