package org.gonevertical.loc.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;

public class Padels extends Composite {

  public static final int LEFT = 1;
  public static final int RIGHT = 2;
  
  private AbsolutePanel pWidget = new AbsolutePanel();
  
  private Padel pA = new Padel(Padel.A);
  private Padel pB = new Padel(Padel.B);
  
  private int width = 600;
  private int height = 50;
  
  
  public Padels() {
    initWidget(pWidget);
    
    pWidget.setStyleName("Padels");
    pWidget.setSize(Integer.toString(width), Integer.toString(height));
    
    drawA();
    drawB();
  }
 
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public void move(int keyCode) {
    // player 1 move
    if (keyCode == "a".charAt(0)) {
      moveA(LEFT);
    } else if (keyCode == "d".charAt(0)) {
      moveA(RIGHT);
    }
    
    // player 2 move
    if (keyCode == KeyCodes.KEY_LEFT | keyCode == "j".charAt(0)) {
      moveB(LEFT);
    } else if (keyCode == KeyCodes.KEY_RIGHT | keyCode == "l".charAt(0)) {
      moveB(RIGHT);
    }
  }
  
  public int getPositionA() {
   return pA.getPosition();
  }
  
  public int getPositionB() {
    return pB.getPosition();
  }
  
  public void setScorePos(int index) {
    pA.setScorePos(index);
    pB.setScorePos(index);
  }
  
  private void moveA(int direction) {
    if (direction == LEFT) {
      pA.move(LEFT);
    } else if (direction == RIGHT) {
      pA.move(RIGHT);
    }
  }
  
  private void moveB(int direction) {
    if (direction == LEFT) {
      pB.move(LEFT);
    } else if (direction == RIGHT) {
      pB.move(RIGHT);
    }
  }
  
  private void drawA() {
    pWidget.add(pA, 0, 0);
    pA.drawPadelStart(7);
    
  }
  
  private void drawB() {
    pWidget.add(pB, 0, 20);
    pB.drawPadelStart(20);
  }

  public void displayAll(boolean b) {
    pA.setDisplayAll(b);
    pB.setDisplayAll(b);
  }

  public boolean[] getMap() {
    boolean[] p1 = pA.getMap();
    boolean[] p2 = pB.getMap();
    
    boolean[] m = new boolean[60];
    m[0] = p1[0];
    m[1] = p1[1];
    m[2] = p1[2];
    m[3] = p1[3];
    m[4] = p1[4];
    m[5] = p1[5];
    m[6] = p1[6];
    m[7] = p1[7];
    m[8] = p1[8];
    m[9] = p1[9];
    m[10] = p1[10];
    m[11] = p1[11];
    m[12] = p1[12];
    m[13] = p1[13];
    m[14] = p1[14];
    m[15] = p1[15];
    m[16] = p1[16];
    m[17] = p1[17];
    m[18] = p1[18];
    m[19] = p1[19];
    m[20] = p1[20];
    m[21] = p1[21];
    m[22] = p1[22];
    m[23] = p1[23];
    m[24] = p1[24];
    m[25] = p1[25];
    m[26] = p1[26];
    m[27] = p1[27];
    m[28] = p1[28];
    m[29] = p1[29];
    
    m[30] = p2[0];
    m[31] = p2[1];
    m[32] = p2[2];
    m[33] = p2[3];
    m[34] = p2[4];
    m[35] = p2[5];
    m[36] = p2[6];
    m[37] = p2[7];
    m[38] = p2[8];
    m[39] = p2[9];
    m[40] = p2[10];
    m[41] = p2[11];
    m[42] = p2[12];
    m[43] = p2[13];
    m[44] = p2[14];
    m[45] = p2[15];
    m[46] = p2[16];
    m[47] = p2[17];
    m[48] = p2[18];
    m[49] = p2[19];
    m[50] = p2[20];
    m[51] = p2[21];
    m[52] = p2[22];
    m[53] = p2[23];
    m[54] = p2[24];
    m[55] = p2[25];
    m[56] = p2[26];
    m[57] = p2[27];
    m[58] = p2[28];
    m[59] = p2[29];
    
    return m;
  }
 
  
}
