package org.gonevertical.loc.client;

import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Padel extends Composite implements HasKeyPressHandlers {

  public static final int A = 1;
  public static final int B = 2;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private int width = 600;
  private int height = 20;
  
  private Grid grid = null;
  
  private int columns = 30;
  
  private int prevIndex = 0;
  
  private int ab = 0;
  
  boolean[] m = new boolean[30];
  
  public Padel(int ab) {
    this.ab = ab;
    
    int r = 1;

    grid = new Grid(r, columns);

    pWidget.add(grid);
    
    initWidget(pWidget);
    
    grid.setSize(Integer.toString(width), Integer.toString(height));
    grid.setCellSpacing(0);
    grid.setCellPadding(0);
    pWidget.setStyleName("Padel");
    pWidget.setWidth("100%");
    
    drawPadelCell();
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }

  public int getPosition() {
    return prevIndex;
  }
  
  public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
    return addDomHandler(handler, KeyPressEvent.getType());
  }

  public void drawPadelStart(int index) {
    drawPadel(index);
  }
  
  public void setA() {
    ab = A;
  }
  
  public void setB() {
    ab = B;
  }
  
  private void drawPadelCell() {
    int cellWidth = width / columns;
    for (int i=0; i < columns; i++) {
      String si = "" + i; // "&nbsp;"
      grid.setWidget(0, i, new HTML(si));
      grid.getCellFormatter().setWidth(0, i, Integer.toString(cellWidth) + "px");
      grid.getCellFormatter().addStyleName(0, i, "Padel-Cell");
      setOff(i);
    }
  }
  
  private void drawPadel(int index) {

    if (index < 0) {
      index = 0;
    }
    
    if (index > columns-3) {
      index = columns-3;
    }
    
    int a = index;
    int b = index + 1;
    int c = index + 2;
    
    setOn(a);
    setOn(b);
    setOn(c);
    
    this.prevIndex = index;
  }
  
  public void move(int way) {
    if (way == Padels.LEFT) {
      moveLeft();
    } else if (way == Padels.RIGHT) {
      moveRight();
    }
  }
  
  private void moveLeft() {
    int index = prevIndex - 1;
    setOff(index + 3);
    drawPadel(index);
  }
  
  private void moveRight() {
    int index = prevIndex + 1;
    setOff(index - 1);
    drawPadel(index);
  }
  
  private void setOff(int index) {
    if (index < 0) {
      return;
    }
    
    if (index > columns) {
      return;
    }
    
    String onoff = getStyle_OnOff();
    grid.getCellFormatter().removeStyleName(0, index, onoff);
    grid.getCellFormatter().addStyleName(0, index, "Padel-Cell-Off");
    
    m[index] = false;
  }
  
  private void setOn(int index) {
    if (index < 0) {
      return;
    }
    
    if (index > columns) {
      return;
    }
    
    String onoff = getStyle_OnOff();
    grid.getCellFormatter().addStyleName(0, index, onoff);
    grid.getCellFormatter().removeStyleName(0, index, "Padel-Cell-Off");
    if (onoff.contains("On") == true) {
      m[index] = true;
    } else if (onoff.contains("Off") == true) {
      m[index] = false;
    }
  }
  
  public void setScorePos(int index) {
    grid.getCellFormatter().addStyleName(0, index, "Padel-Cell-Score");
  }
  
  private String getStyle_OnOff() {
    String s = "";
    if (ab == A) {
      s = "Padel-Cell-On-A";
    } else if (ab == B) {
      s = "Padel-Cell-On-B";
    }
    return s;
  }

  public void setDisplayAll(boolean b) {
    for (int i=0; i < columns; i++) {
      if (b == true) {
        setOn(i);
      } else if (b == false) {
        setOff(i);
      }
    }
  }

  public boolean[] getMap() {
    return m;
  }
}
