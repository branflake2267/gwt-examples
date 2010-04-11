package org.gonevertical.loc.client.digit;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Digits extends Composite {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private VerticalPanel pTitle = new VerticalPanel();
  
  private FlowPanel p = new FlowPanel();
  private FlowPanel p1 = new FlowPanel();
  private FlowPanel p2 = new FlowPanel();
  private FlowPanel p3 = new FlowPanel();
  
  private int number = 0;
  
  private Digit d1 = new Digit();
  private Digit d2 = new Digit();
  private Digit d3 = new Digit();
  
  private boolean[] state = new boolean[21];
  
  public Digits() {
    

    HorizontalPanel hp = new HorizontalPanel();
    hp.add(p1);
    hp.add(p2);
    hp.add(p3);
    
    HorizontalPanel hp2 = new HorizontalPanel();
    hp2.add(d1);
    hp2.add(d2);
    hp2.add(d3);
    
    pWidget.add(pTitle);
    pWidget.add(hp);
    pWidget.add(hp2);
    
    initWidget(pWidget);

    hp.setStyleName("Digits");
    p.setStyleName("Digit");
    p1.setStyleName("Digit");
    p2.setStyleName("Digit");
    p3.setStyleName("Digit");
  }
  
  public void reset() {
    number = 0;
    draw(1, 0);
    draw(2, 0);
    draw(3, 0);
    
    drawNumber(0);
  }
  
  public void setTitle(String title) {
    pTitle.clear();
    pTitle.add(new HTML(title));
    
    if (title.contains("1") == true) {
      pTitle.addStyleName("PlayerA");
      p1.addStyleName("PlayerA");
      p2.addStyleName("PlayerA");
      p3.addStyleName("PlayerA");
      d1.addStyleName("PlayerA");
      d2.addStyleName("PlayerA");
      d3.addStyleName("PlayerA");
      
    } else if (title.contains("2") == true) {
      pTitle.addStyleName("PlayerB");
      p1.addStyleName("PlayerB");
      p2.addStyleName("PlayerB");
      p3.addStyleName("PlayerB");
      d1.addStyleName("PlayerB");
      d2.addStyleName("PlayerB");
      d3.addStyleName("PlayerB");
    }
  }
  
  public void drawTime(int seconds) {
    int min = seconds / 60;
    int sec = seconds % 60;
    
    if (sec < 0) {
      return;
    }
    
    String s = "";
    if (min > 0) {
      String ss = "";
      if (sec < 10) {
        ss = "0" + sec;
      } else {
        ss = Integer.toString(sec);
      }
      s = "" + min + "" + ss + "";
    } else {
      s = Integer.toString(sec);
    }
    
    drawNum(s);
  }
  
  public void drawNumber(int number) {
    this.number = number;
    String s = Integer.toString(number);
    drawNum(s);
  }
  
  private void drawNum(String s) {
    if (s.length() == 0) {
      s = "000";
    } else if (s.length() == 1) {
      s = "00" + s;
    } else if (s.length() == 2) {
      s = "0" + s;
    }
    
    String ss = s.substring(0, 1);
    String sss = s.substring(1, 2);
    String ssss = s.substring(2, 3);

    draw(1, Integer.parseInt(ss));
    d1.draw(ss);

    draw(2, Integer.parseInt(sss));
    d2.draw(sss);

    draw(3, Integer.parseInt(ssss));
    d3.draw(ssss);
    
    state = getMap();
  }
  
  private void draw(int which, int value) {
    if (which == 1) {
      drawText(p1, value);
    } else if (which == 2) {
      drawText(p2, value);
    } else if (which == 3) {
      drawText(p3, value);
    }
  }
  
  private void drawText(FlowPanel p, int value) {
    p.clear();
    try {
      p.add(new HTML(Integer.toString(value)));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getScore() {
    return number;
  }

  public void displayAll(boolean b) {
    d1.displayAll(b);
    d2.displayAll(b);
    d3.displayAll(b);
  }

  public boolean[] getMap() {
    boolean[] dd1 = d1.getMap(); // 7
    boolean[] dd2 = d2.getMap(); // 7
    boolean[] dd3 = d3.getMap(); // 7
    
    boolean[] m = new boolean[21];
    m[0] = dd1[0];
    m[1] = dd1[1];
    m[2] = dd1[2];
    m[3] = dd1[3];
    m[4] = dd1[4];
    m[5] = dd1[5];
    m[6] = dd1[6];
    
    m[7] = dd2[0];
    m[8] = dd2[1];
    m[9] = dd2[2];
    m[10] = dd2[3];
    m[11] = dd2[4];
    m[12] = dd2[5];
    m[13] = dd2[6];
    
    m[14] = dd3[0];
    m[15] = dd3[1];
    m[16] = dd3[2];
    m[17] = dd3[3];
    m[18] = dd3[4];
    m[19] = dd3[5];
    m[20] = dd3[6];
    
    return m;
  }

  private void setBack() {
    boolean[] dd1 = new boolean[7];
    boolean[] dd2 = new boolean[7];
    boolean[] dd3 = new boolean[7];
    
    dd1[0] = state[0];
    dd1[1] = state[1];
    dd1[2] = state[2];
    dd1[3] = state[3];
    dd1[4] = state[4];
    dd1[5] = state[5];
    dd1[6] = state[6];
    
    dd2[0] = state[7];
    dd2[1] = state[8];
    dd2[2] = state[9];
    dd2[3] = state[10];
    dd2[4] = state[11];
    dd2[5] = state[12];
    dd2[6] = state[13];
    
    dd3[0] = state[14];
    dd3[1] = state[15];
    dd3[2] = state[16];
    dd3[3] = state[17];
    dd3[4] = state[18];
    dd3[5] = state[19];
    dd3[6] = state[20];
    
    d1.turnOnOnly(dd1);
    d2.turnOnOnly(dd2);
    d3.turnOnOnly(dd3);
  }
  
  public void displayOnly(boolean b) {
    if (b == true) {
      setBack();
    } else if (b == false) {
      displayAll(false);
    }
  }
  

}
