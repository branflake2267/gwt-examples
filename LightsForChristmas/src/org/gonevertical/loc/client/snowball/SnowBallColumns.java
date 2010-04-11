package org.gonevertical.loc.client.snowball;

import java.util.Arrays;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SnowBallColumns extends Composite implements ChangeHandler {

  private AbsolutePanel pWidget = null;

  private SnowBallColumn sb1 = new SnowBallColumn();
  private SnowBallColumn sb2 = new SnowBallColumn();
  private SnowBallColumn sb3 = new SnowBallColumn();
  private SnowBallColumn sb4 = new SnowBallColumn();
  private SnowBallColumn sb5 = new SnowBallColumn();
  private SnowBallColumn sb6 = new SnowBallColumn();
  private SnowBallColumn sb7 = new SnowBallColumn();
  
  private int width = 600;
  private int height = 365;
  private int leftMargin = 10;
  private int rightMargin = 10;
  private int howManyColumns = 7;
 
  private int columnWidth = 0;
  
  private int ballHitBottom = -1;
  
  private int howManyColumnsToMove = 3;
  private int trackColumnsMoving[] = null;
  
  private boolean run = false;
  
  public SnowBallColumns() {
    
    pWidget = new AbsolutePanel();

    initWidget(pWidget);
    
    pWidget.setSize(Integer.toString(width), Integer.toString(height)); 
    pWidget.setStyleName("SnowBallColumns");
    
    getColumnSpacing();
    drawColumns();
  }
  
  public void start() {
    run = true;
    drawInitalBallsDropping();
  }
  
  public void stop() {
    run = false;
    sb1.stop();
    sb2.stop();
    sb3.stop();
    sb4.stop();
    sb5.stop();
    sb6.stop();
    sb7.stop();
    ballHitBottom = -1;
    trackColumnsMoving = null;
  }
  
  private void drawInitalBallsDropping() {
    
    if (run == false) {
      return;
    }
    
    trackColumnsMoving = new int[howManyColumnsToMove];
    
    for (int i=0; i < howManyColumnsToMove; i++) {
      dropBall(0);
    }
  }
  
  /**
   * start a ball dropping
   */
  private void dropBall(int ballHitBottom) {
    
    if (run == false) {
      return;
    }
    
    int column = getRandom();
    
    setTrackColumn(column, ballHitBottom);
    
    switch (column) {
    case 1:
      sb1.start();
      break;
    case 2:
      sb2.start();
      break;
    case 3:
      sb3.start();
      break;
    case 4:
      sb4.start();
      break;
    case 5:
      sb5.start();
      break;
    case 6:
      sb6.start();
      break;
    case 7:
      sb7.start();
      break;
    }
  }
  
  private void setTrackColumn(int column, int ballHitBottom) {
   Arrays.sort(trackColumnsMoving);
   
   int index = Arrays.binarySearch(trackColumnsMoving, ballHitBottom);
   
   if (ballHitBottom == 0) {
     trackColumnsMoving[findTrackWithZero()] = column;
   } else {
     trackColumnsMoving[index] = column;
   }
  }
  
  private int findTrackWithZero() {
    Arrays.sort(trackColumnsMoving);
    return Arrays.binarySearch(trackColumnsMoving, 0);
  }
  
  private boolean doesTrackingHaveZeros() {
    Arrays.sort(trackColumnsMoving);
    int i = Arrays.binarySearch(trackColumnsMoving, 0);
    boolean b = false;
    if (i >= 0) {
      b = true;
    }
    return b;
  }
  
  /**
   * get 1-9 random number
   */
  private int getRandom() {
    int r = Random.nextInt(7) + 1;
    if (isColumnRunningAlready(r) == true) {
      r = getRandom();
    }
    return r;
  }
  
  private boolean isColumnRunningAlready(int row) {
    Arrays.sort(trackColumnsMoving);
    int index = Arrays.binarySearch(trackColumnsMoving, row);
    boolean b = false;
    if (index >= 0) {
      b = true;
    }
    return b;
  }
  
  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }

  public int getBallHitBottom() {
    return this.ballHitBottom;
  }
  
  /**
   * initial draw of columns
   */
  private void drawColumns() {
    
    pWidget.add(sb1, getXPosition(0), 0);
    pWidget.add(sb2, getXPosition(1), 0);
    pWidget.add(sb3, getXPosition(2), 0);
    pWidget.add(sb4, getXPosition(3), 0);
    pWidget.add(sb5, getXPosition(4), 0);
    pWidget.add(sb6, getXPosition(5), 0);
    pWidget.add(sb7, getXPosition(6), 0);
    
    sb1.addChangeHandler(this);
    sb2.addChangeHandler(this);
    sb3.addChangeHandler(this);
    sb4.addChangeHandler(this);
    sb5.addChangeHandler(this);
    sb6.addChangeHandler(this);
    sb7.addChangeHandler(this);
  }
  
  private void getColumnSpacing() {
    columnWidth = (width - (leftMargin + rightMargin)) / howManyColumns;
  }
  
  private int getXPosition(int columnNumber) {
    double leftOfSnowBall = (columnWidth / 2) - (sb1.getColumnWidth() / 2);
    int left = 0;
    if (columnNumber == 0) {
      left = leftMargin;
    } else {
      left = leftMargin + (columnWidth * columnNumber-1);
    }
    double x = left + leftOfSnowBall;
    return (int) x;
  }

  public void onChange(ChangeEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == sb1) {
      ballHitBottom = 1;
    } else if (sender == sb2) {
      ballHitBottom = 2;
    } else if (sender == sb3) {
      ballHitBottom = 3;
    } else if (sender == sb4) {
      ballHitBottom = 4;
    } else if (sender == sb5) {
      ballHitBottom = 5;
    } else if (sender == sb6) {
      ballHitBottom = 6;
    } else if (sender == sb7) {
      ballHitBottom = 7;
    } 
    fire();
    
    // start another ball dropping in place of
    dropBall(ballHitBottom);
  }
  
  private void fire() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  public void displayAll(boolean b) {
    sb1.displayAll(b);
    sb2.displayAll(b);
    sb3.displayAll(b);
    sb4.displayAll(b);
    sb5.displayAll(b);
    sb6.displayAll(b);
    sb7.displayAll(b);
  }
  
  public boolean[] getMap() {
    boolean[] c1 = sb1.getMap();
    boolean[] c2 = sb2.getMap();
    boolean[] c3 = sb3.getMap();
    boolean[] c4 = sb4.getMap();
    boolean[] c5 = sb5.getMap();
    boolean[] c6 = sb6.getMap();
    boolean[] c7 = sb7.getMap();
    
    boolean[] m = new boolean[56];
    m[0] = c1[0];
    m[1] = c1[1];
    m[2] = c1[2];
    m[3] = c1[3];
    m[4] = c1[4];
    m[5] = c1[5];
    m[6] = c1[6];
    m[7] = c1[7];
    
    m[8] = c2[0];
    m[9] = c2[1];
    m[10] = c2[2];
    m[11] = c2[3];
    m[12] = c2[4];
    m[13] = c2[5];
    m[14] = c2[6];
    m[15] = c2[7];
    
    m[16] = c3[0];
    m[17] = c3[1];
    m[18] = c3[2];
    m[19] = c3[3];
    m[20] = c3[4];
    m[21] = c3[5];
    m[22] = c3[6];
    m[23] = c3[7];
    
    m[24] = c4[0];
    m[25] = c4[1];
    m[26] = c4[2];
    m[27] = c4[3];
    m[28] = c4[4];
    m[29] = c4[5];
    m[30] = c4[6];
    m[31] = c4[7];
    
    m[32] = c5[0];
    m[33] = c5[1];
    m[34] = c5[2];
    m[35] = c5[3];
    m[36] = c5[4];
    m[37] = c5[5];
    m[38] = c5[6];
    m[39] = c5[7];
    
    m[40] = c6[0];
    m[41] = c6[1];
    m[42] = c6[2];
    m[43] = c6[3];
    m[44] = c6[4];
    m[45] = c6[5];
    m[46] = c6[6];
    m[47] = c6[7];
    
    m[48] = c7[0];
    m[49] = c7[1];
    m[50] = c7[2];
    m[51] = c7[3];
    m[52] = c7[4];
    m[53] = c7[5];
    m[54] = c7[6];
    m[55] = c7[7];
    
    return m;
  }
  
  public void setBall(int r, int c, boolean b) {
    
    switch (c) {
    case 0:
      sb1.setBall(r, b);
      break;
    case 1:
      sb2.setBall(r, b);
      break;
    case 2:
      sb3.setBall(r, b);
      break;
    case 3:
      sb4.setBall(r, b);
      break;
    case 4:
      sb5.setBall(r, b);
      break;
    case 5:
      sb6.setBall(r, b);
      break;
    case 6:
      sb7.setBall(r, b);
      break;
    }
    
  }
}
