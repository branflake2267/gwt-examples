package com.gawkat.core.client;

import com.gawkat.core.client.global.EventManager;
import com.gawkat.core.client.global.Style;
import com.gawkat.core.server.db.Db_ThingType;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class Row extends Composite implements MouseOverHandler, MouseOutHandler {

  private FocusPanel pWidget = new FocusPanel();
  
  private HorizontalPanel hp = new HorizontalPanel();
  
  private int row = 0;
  
  private int changeEvent = 0;
  
  public Row() {
    pWidget.add(hp);
    initWidget(pWidget);
  
    pWidget.addMouseOverHandler(this);
    pWidget.addMouseOutHandler(this);
    
    //hp.setStyleName("core-row-cell");
    hp.setSpacing(3);
  }
  
  public void setRow(int row) {
    this.row = row;
    setStyle();
  }
  
  public void add(Widget w) {
    hp.add(w);
    hp.setCellVerticalAlignment(w, VerticalPanel.ALIGN_MIDDLE);
  }
  
  public void add(Widget w, HorizontalAlignmentConstant align) {
    hp.add(w);
    hp.setCellVerticalAlignment(w, VerticalPanel.ALIGN_MIDDLE);
    hp.setCellHorizontalAlignment(w, align);
  }
  
  private void setStyle() {
    String style = Style.getRowStyle(row);
    pWidget.addStyleName(style);
  }
  
  private void setStyleHover(boolean b) {
    if (b == true) {
      pWidget.addStyleName(Style.getRowStyleHover());
    } else if (b == false) {
      pWidget.removeStyleName(Style.getRowStyleHover());
    }
  }

  public void onMouseOver(MouseOverEvent event) {
    setStyleHover(true);
    //fireChange(EventManager.ROW_OVER);
  }

  public void onMouseOut(MouseOutEvent event) {
    setStyleHover(false);
    //fireChange(EventManager.ROW_OUT);
  }
  
  public void clear() {
    //this.clear();
  }
  
  public void setWidths(int[] widths) {
    for (int i=0; i < hp.getWidgetCount(); i++) {
      hp.getWidget(i).setWidth(widths[i] + "px");
    }
  }
  
  public int[] getWidths() {
    int[] r = new int[hp.getWidgetCount()];
    for (int i=0; i < hp.getWidgetCount(); i++) {
      r[i] = hp.getWidget(i).getOffsetWidth();
    }
    return r;
  }
  
  public static int[] getMaxWidths(int[] a, int[] b) {
    for (int i=0; i < a.length; i++) {
      if (b[i] > a[i]) {
        a[i] = b[i];
      }
    }
    return a;
  }

  public int getChangeEvent() {
    return changeEvent;
  }
  
  private void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }
}
