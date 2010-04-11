package org.gonevertical.loc.client.snowball;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class SnowBallColumn extends Composite {
 
  private AbsolutePanel pWidget = null;

  //private Image ball1 = new Image("/images/ball.png");
  private Image ball1 = new Image("images/ball.png");
  private Image ball2 = new Image("images/ball.png");
  private Image ball3 = new Image("images/ball.png");
  private Image ball4 = new Image("images/ball.png");
  private Image ball5 = new Image("images/ball.png");
  private Image ball6 = new Image("images/ball.png");
  private Image ball7 = new Image("images/ball.png");
  private Image ball8 = new Image("images/ball.png");

  private int index = 0;

  // wait this to before start of dropping
  // before the start of the ball dropping from top
  @Deprecated
  private int randomWaitStartFloor = 300;
  @Deprecated
  private int randomWaitStartCeiling = 750; 
  
  // wait this before moving to next pos
  private int randomWaitBallMoveFloor = 200;
  private int randomWaitBallMoveCeiling = 800;
  
  // current random wait setting 
  private int randomWaitBallMove = randomWaitBallMoveCeiling;

  private int columnWidth = 50;
  private int columnHieght = 800;
  
  // stops the recursion of ball dropping
  private boolean run = false;
  
  /**
   * constructor
   */
  public SnowBallColumn() {

    pWidget = new AbsolutePanel();

    pWidget.setSize(Integer.toString(columnWidth), Integer.toString(columnHieght));

    initWidget(pWidget);

    pWidget.setStyleName("SnowBallColumn");

    draw();
  }
  
  public int getColumnWidth() {
    return columnWidth;
  }

  /**
   * setup row
   */
  private void draw() {

    //pWidget.add(ball1, 10, 10);
    pWidget.add(ball1, 10, 50);
    pWidget.add(ball2, 10, 90);
    pWidget.add(ball3, 10, 130);
    pWidget.add(ball4, 10, 170);
    pWidget.add(ball5, 10, 210);
    pWidget.add(ball6, 10, 250);
    pWidget.add(ball7, 10, 290);
    pWidget.add(ball8, 10, 330);

    //ball1.setHeight("30px");
    ball1.setHeight("30px");
    ball2.setHeight("30px");
    ball3.setHeight("30px");
    ball4.setHeight("30px");
    ball5.setHeight("30px");
    ball6.setHeight("30px");
    ball7.setHeight("30px");
    ball8.setHeight("30px");

    //ball1.setVisible(false);
    ball1.setVisible(false);
    ball2.setVisible(false);
    ball3.setVisible(false);
    ball4.setVisible(false);
    ball5.setVisible(false);
    ball6.setVisible(false);
    ball7.setVisible(false);
    ball8.setVisible(false);
  }
  
  /**
   * start he ball dropping but wait for length of random time
   */
  public void start() {
    run = true;
    
    // change the rate of ball dropping
    changeBallMovingDownSpeed();
    
    // set pos to start at
    index = 1;
    
    /*
    // wait before drop
    Timer t = new Timer() {
      public void run() {
        
        // start moving
        move();
      }
    };
    t.schedule(getRandomStartTime());
    */
    move();
  }
  

  public void stop() {
    run = false;
  }
  
  /**
   * use floor is the base plus the random number generated to get the start time 
   * 
   * @return
   */
  private int getRandomStartTime() {
    int r = Random.nextInt(randomWaitStartCeiling) + randomWaitStartFloor;
    return r;
  }
  
  /**
   * uses the floor as the base to start from random int
   * @return
   */
  private int getRandomMoveTime() {
    int r = Random.nextInt(randomWaitBallMoveCeiling) + randomWaitBallMoveFloor;
    return r;
  }
  
  private void changeBallMovingDownSpeed() {
    randomWaitBallMove = getRandomMoveTime();
  }
  
  private void move() {
    
    // break out of recursion when stopped is pressed
    if (run == false) {
      hideAll();
      return;
    }
    
    // wait before drop (double wait if first wait is long?)
    Timer timer = new Timer() {
      public void run() {
        changePosition();
        move();
      }
    };
    timer.schedule(randomWaitBallMove);
  }

  private void changePosition() {
    
    // start over
    if (index > 8) { 
      index = 0;
      run = false;
      fire();
      ballSwitch(8, false);
      return;
    } 
    
    int before = index - 1;
    if (before == 0) {
      before = 8;
    }
    
    ballSwitch(before, false);
    ballSwitch(index, true);
    
    index++;
  }
  
  private void ballSwitch(int index, boolean visible) {
  
    switch (index) {
    case 0:
      System.out.println("Tried to put Ball in Pos 0");
      break;
    case 1:
      ball1.setVisible(visible);
      break;
    case 2:
      ball2.setVisible(visible);
      break;
    case 3:
      ball3.setVisible(visible);
      break;
    case 4:
      ball4.setVisible(visible);
      break;
    case 5:
      ball5.setVisible(visible);
      break;
    case 6:
      ball6.setVisible(visible);
      break;
    case 7:
      ball7.setVisible(visible);
      break;
    case 8:
      ball8.setVisible(visible);
      break;
    }
    
  }
  
  private void hideAll() {
    boolean visible = false;
    ballSwitch(1, visible);
    ballSwitch(2, visible);
    ballSwitch(3, visible);
    ballSwitch(4, visible);
    ballSwitch(5, visible);
    ballSwitch(6, visible);
    ballSwitch(7, visible);
    ballSwitch(8, visible);
  }
  
  public void displayAll(boolean b) {
    ballSwitch(1, b);
    ballSwitch(2, b);
    ballSwitch(3, b);
    ballSwitch(4, b);
    ballSwitch(5, b);
    ballSwitch(6, b);
    ballSwitch(7, b);
    ballSwitch(8, b);
  }
  
  private void fire() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  public boolean[] getMap() {
    boolean[] m = new boolean[8];
    m[0] = ball1.isVisible();
    m[1] = ball2.isVisible();
    m[2] = ball3.isVisible();
    m[3] = ball4.isVisible();
    m[4] = ball5.isVisible();
    m[5] = ball6.isVisible();
    m[6] = ball7.isVisible();
    m[7] = ball8.isVisible();
    return m;
  }

  public void setBall(int r, boolean b) {
    r++;
    ballSwitch(r, b);
  }



  
}
