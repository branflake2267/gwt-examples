package org.gonevertical.loc.client.digit;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;

public class Digit extends Composite {

  private FlowPanel pWidget = new FlowPanel();
  
  private FlowPanel p0 = new FlowPanel();
  private FlowPanel p1 = new FlowPanel();
  private FlowPanel p2 = new FlowPanel();
  private FlowPanel p3 = new FlowPanel();
  private FlowPanel p4 = new FlowPanel();
  private FlowPanel p5 = new FlowPanel();
  private FlowPanel p6 = new FlowPanel();
  
  private DigitDef digitDef = new DigitDef();
  
  /**
    _
   |_|
   |_|
  
    0_
   1|_|4 5(mid)
   2|_|6
     3

  */
  public Digit() {
    
    p0.add(new HTML("_"));
    p1.add(new HTML("|"));
    p2.add(new HTML("|"));
    p3.add(new HTML("_"));
    p4.add(new HTML("|"));
    p5.add(new HTML("_"));
    p6.add(new HTML("|"));

    int r = 4;
    int c = 3;
    Grid g = new Grid(r,c);
    
    g.setWidget(0, 1, p0);
    g.setWidget(1, 0, p1);
    g.setWidget(2, 0, p2);
    g.setWidget(2, 1, p3);
    g.setWidget(1, 2, p4);
    g.setWidget(1, 1, p5);
    g.setWidget(2, 2, p6);

    pWidget.add(g);
    
    initWidget(pWidget);
    
    p0.setVisible(false);
    p1.setVisible(false);
    p2.setVisible(false);
    p3.setVisible(false);
    p4.setVisible(false);
    p5.setVisible(false);
    p6.setVisible(false);
  }
  
  public void draw(String s) {
    int[] seg = digitDef.getDef(s);
    
    if (seg[0] == 1) {
      p0.setVisible(true);
    } else {
      p0.setVisible(false);
    }
    
    if (seg[1] == 1) {
      p1.setVisible(true);
    } else {
      p1.setVisible(false);
    }
    
    if (seg[2] == 1) {
      p2.setVisible(true);
    } else {
      p2.setVisible(false);
    }
    
    if (seg[3] == 1) {
      p3.setVisible(true);
    } else {
      p3.setVisible(false);
    }
    
    if (seg[4] == 1) {
      p4.setVisible(true);
    } else {
      p4.setVisible(false);
    }
   
    if (seg[5] == 1) {
      p5.setVisible(true);
    } else {
      p5.setVisible(false);
    }
    
    if (seg[6] == 1) {
      p6.setVisible(true);
    } else {
      p6.setVisible(false);
    }

  }

  public void displayAll(boolean b) {
    p0.setVisible(b);
    p1.setVisible(b);
    p2.setVisible(b);
    p3.setVisible(b);
    p4.setVisible(b);
    p5.setVisible(b);
    p6.setVisible(b);
  }

  public boolean[] getMap() {
    boolean[] m = new boolean[7];
    m[0] = p0.isVisible();
    m[1] = p1.isVisible();
    m[2] = p2.isVisible();
    m[3] = p3.isVisible();
    m[4] = p4.isVisible();
    m[5] = p5.isVisible();
    m[6] = p6.isVisible();
    return m;
  }
  
  public void turnOnOnly(boolean[] b) {
    p0.setVisible(b[0]);
    p1.setVisible(b[1]);
    p2.setVisible(b[2]);
    p3.setVisible(b[3]);
    p4.setVisible(b[4]);
    p5.setVisible(b[5]);
    p6.setVisible(b[6]);
  }
  
}
