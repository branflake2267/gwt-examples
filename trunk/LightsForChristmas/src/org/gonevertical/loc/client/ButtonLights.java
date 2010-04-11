package org.gonevertical.loc.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ButtonLights extends Composite {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private HTML p1a = new HTML("L");
  private HTML p1b = new HTML("R");
  
  private HTML ca = new HTML("Start");
  private HTML cb = new HTML("Stop");
  
  private HTML p2a = new HTML("L");
  private HTML p2b = new HTML("R");
  
  public ButtonLights() {
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(p1a);
    hp.add(new HTML("&nbsp;"));
    hp.add(p1b);
    hp.add(new HTML("&nbsp;&nbsp;&nbsp;"));
    hp.add(ca);
    hp.add(new HTML("&nbsp;"));
    hp.add(cb);
    hp.add(new HTML("&nbsp;&nbsp;&nbsp;"));
    hp.add(p2a);
    hp.add(new HTML("&nbsp;"));
    hp.add(p2b);
    
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    p1a.addStyleName("Buttons");
    p1b.addStyleName("Buttons");
    ca.addStyleName("Buttons");
    cb.addStyleName("Buttons");
    p2a.addStyleName("Buttons");
    p2b.addStyleName("Buttons");
  }
  
  private boolean isOn(HTML h) {
    boolean b = false;
    if (h.getStyleName().contains("On")== true) {
      b = true;
    } 
    return b;
  }
  
  public boolean[] getMap() {
    boolean[] m = new boolean[6];
    m[0] = isOn(p1a);
    m[1] = isOn(p1b);
    m[2] = isOn(ca);
    m[3] = isOn(cb);
    m[4] = isOn(p2a);
    m[5] = isOn(p2b);
    return m;
  }
  
  public void init() {
    displayAll(true);
  }
  
  public void displayAll(boolean b) {
    turnOn(1, b);
    turnOn(2, b);
    turnOn(3, b);
    turnOn(4, b);
    turnOn(5, b);
    turnOn(6, b);
  }
  
  public void turnOnPlayer(int player, boolean b) {
    if (player == 1) {
      turnOn(1, b);
      turnOn(2, b);
    } else if (player == 2) {
      turnOn(5, b);
      turnOn(6, b);
    } else if (player == 3) {
      turnOn(1, b);
      turnOn(2, b);
      turnOn(5, b);
      turnOn(6, b);
    }
  }
  
  private void turnOn(int t, boolean b) {
    
    if (t == 1) {
      if (b == true) {
        p1a.addStyleName("Buttons_PA_On");
      } else {
        p1a.removeStyleName("Buttons_PA_On");
      }
    } else if (t == 2) {
      if (b == true) {
        p1b.addStyleName("Buttons_PA_On");
      } else {
        p1b.removeStyleName("Buttons_PA_On");
      }
    } else if (t == 3) {
      if (b == true) {
        ca.addStyleName("Buttons_GC_On"); 
      } else {
        ca.removeStyleName("Buttons_GC_On");
      }
    } else if (t == 4) {
      if (b == true) {
        cb.addStyleName("Buttons_GC_On");
      } else {
        cb.removeStyleName("Buttons_GC_On"); 
      }
    } else if (t == 5 ) {
      if (b == true) {
        p2a.addStyleName("Buttons_PB_On");
      } else {
        p2a.removeStyleName("Buttons_PB_On");
      }
    } else if (t == 6) {
      if (b == true) {
        p2b.addStyleName("Buttons_PB_On");
      } else {
        p2b.removeStyleName("Buttons_PB_On");
      }
    }
    
  }
  
}
