package org.gonevertical.core.client.ui.widgets;

import org.gonevertical.core.client.global.HorizontalPanelEvent;
import org.gonevertical.core.client.global.Style;

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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

public class Row extends Composite implements MouseOverHandler, MouseOutHandler {
  
  private HorizontalPanelEvent pWidget = new HorizontalPanelEvent();
  
  private long row = 0;
  
  private int changeEvent = 0;
  
  // set a max width to constrain the row to, either by growing or maybe shrinking?
  // shrinking may need a way to move back and forth to see the data correctly
  private int maxWidth = 0;
  
  public Row() {
    initWidget(pWidget);
  
    pWidget.addMouseOverHandler(this);
    pWidget.addMouseOutHandler(this);
    
    //hp.setStyleName("core-row-cell");
    pWidget.setSpacing(3);
   
  }
  
  public void setMaxWidth(int maxWidth) {
    this.maxWidth = maxWidth;
  }
  
  public void setRow(int row) {
    this.row = row;
    setStyle();
  }
  
  public void setSpacing(int pixels) {
    pWidget.setSpacing(pixels);
  }
  
  public void add(Widget w) {
    FlowPanel fp = new FlowPanel();
    fp.add(w);
    pWidget.add(fp);
    pWidget.setCellVerticalAlignment(fp, VerticalPanel.ALIGN_MIDDLE);
    
    //fp.addStyleName("test1");
  }
  
  public void add(Widget w, HorizontalAlignmentConstant align) {
    FlowPanel fp = new FlowPanel();
    fp.add(w);
    pWidget.add(fp);
    pWidget.setCellVerticalAlignment(fp, VerticalPanel.ALIGN_MIDDLE);
    pWidget.setCellHorizontalAlignment(fp, align);
    
    //fp.addStyleName("test2");
  }
  
  public void add(Widget w, HorizontalAlignmentConstant align, String style) {
    FlowPanel fp = new FlowPanel();
    fp.add(w);
    pWidget.add(fp);
    pWidget.setCellVerticalAlignment(fp, VerticalPanel.ALIGN_MIDDLE);
    pWidget.setCellHorizontalAlignment(fp, align);
    
    if (style != null && style.length() > 0) {
    	fp.addStyleName(style);
    }
    
    //fp.addStyleName("test3");
  }
  
  public void add(Widget w, HorizontalAlignmentConstant align, int width) {
    FlowPanel fp = new FlowPanel();
    fp.add(w);
    pWidget.add(fp);
    pWidget.setCellVerticalAlignment(fp, VerticalPanel.ALIGN_MIDDLE);
    pWidget.setCellHorizontalAlignment(fp, align);
    fp.setWidth(width + "px");
    
    //fp.addStyleName("test4");
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
  	if (widths == null) {
  		return;
  	}
    for (int i=0; i < pWidget.getWidgetCount(); i++) {
    	if (i < widths.length) {
    		pWidget.getWidget(i).setWidth(widths[i] + "px");
    	}
    }
  }
  
  public int[] getWidths() {
    int[] r = new int[pWidget.getWidgetCount()];
    for (int i=0; i < pWidget.getWidgetCount(); i++) {
      r[i] = pWidget.getWidget(i).getOffsetWidth();
    }
    return r;
  }
  
  /**
   * get widths with the growth to fix the max width set
   * 
   * @return
   */
  public int[] getWidths_With_MaxGrowth() {
    int currentWidth = pWidget.getOffsetWidth();
    int[] r = new int[pWidget.getWidgetCount()];
    for (int i=0; i < pWidget.getWidgetCount(); i++) {
      int width = pWidget.getWidget(i).getOffsetWidth();
      if (currentWidth > 0) {
        r[i] = maxWidth * (width / currentWidth);
      } else {
        r[i] = 0;
      }
    }
    return r;
  }
  
  /**
   * grow the widths bigger, but keep the rations of the columns the same
   * 
   * @param widths
   * @param maxGrowWidth
   * @return
   */
  public static int[] getGrowthWidths(int[] widths, int maxGrowWidth) {
  	if (widths == null) {
  		return null;
  	}
    int currentWidth = 0;
    for (int i=0; i < widths.length; i++) {
      currentWidth += widths[i];
    }
    if (currentWidth > maxGrowWidth) {
      return widths;
    }
    for (int i=0; i < widths.length; i++) {
      if (currentWidth > 0) {
        widths[i] =  (int) (maxGrowWidth * ((double) widths[i]  / (double) currentWidth));
      } else {
        widths[i] = 0;
      }
    }
    return widths;
  }
  
  public static int[] getMaxWidths(int[] a, int[] b) {
    if (b == null) {
      return a;
    } else if (a.length != b.length) {
      return a;
    }
    
    try {
      for (int i=0; i < a.length; i++) {
        if (b[i] > a[i]) {
          a[i] = b[i];
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return a;
  }
  
  public void setWidthOnColumn(int columnIndex, int width) {
    int wc = pWidget.getWidgetCount();
    if (columnIndex > wc) {
      return;
    }
    Widget w = (HorizontalPanel) pWidget.getWidget(columnIndex);
    w.setWidth(width + "px");
  }
  
  public Widget getWidgetIn(int index) {
    FlowPanel fp = (FlowPanel) pWidget.getWidget(index);
    return fp.getWidget(0);
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
  
  public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
  	return addDomHandler(handler, MouseOverEvent.getType());
  }
  
  public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
  	return addDomHandler(handler, MouseOutEvent.getType());
  }
  
}
