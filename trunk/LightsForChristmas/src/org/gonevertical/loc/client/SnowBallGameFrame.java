package org.gonevertical.loc.client;

import org.gonevertical.loc.client.digit.Digits;
import org.gonevertical.loc.client.letters.PlayLetters;
import org.gonevertical.loc.client.snowball.SnowBallColumns;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class SnowBallGameFrame extends Composite implements ChangeHandler {

  private FocusPanel pWidget = new FocusPanel();
  
  // canvas
  private AbsolutePanel ap = new AbsolutePanel();
  
  // game elements
  private SnowBallColumns sb = new SnowBallColumns();
  private Padels wPadels = new Padels();
  private SideScore wScoreA = new SideScore(1);
  private SideScore wScoreB = new SideScore(2);
  private Digits wDigitsA = new Digits();
  private Digits wDigitsB = new Digits();
  private TimerSystem wTimerSystem = new TimerSystem();
  private Winner wWinner = new Winner();
  private GameControls wControls = new GameControls();
  private ButtonLights wButtonLights = new ButtonLights();
  private PlayLetters playLetters = new PlayLetters();
  private Tracker track = new Tracker();
  
  // dmx cordination utility
  private DmxSend dmx = new DmxSend();
  
  private int width = 800;
  private int height = 650;
  
  private int scoreA = 0;
  private int scoreB = 0;
  
  private boolean allOn = false;
  
  // how fast the letters play across
  public static final int PLAYLETTERDELAY = 250;
  
  // how long will the game last? 110 seconds
  public static final int TIMERLENGTH = 71;
  
  // what is the frame rate to send dmx to the controller
  public static final int SENDDMX_WAIT = 70;
  
  /**
   * init game
   */
  public SnowBallGameFrame() { 
  
    dmx.setElements(sb, wPadels, wScoreA, wScoreB, wDigitsA, 
        wDigitsB, wTimerSystem, wWinner, wControls, wButtonLights);
    
    playLetters.setSnowBalls(sb);
    
    pWidget.add(ap);
    initWidget(pWidget);
  
    ap.setStyleName("Frame");
    ap.setSize(Integer.toString(width), Integer.toString(height)); 
    
    sb.addChangeHandler(this);
    
    drawSnowBallColumns();
    drawPadels();
    drawScore();
    drawGameControls();
    drawTimerSystem();
    drawScorePos();
    drawWinner();
    drawButtonLights();
    
    wScoreA.setVisible(false);
    wScoreB.setVisible(false);
    
    wWinner.addChangeHandler(this);
    playLetters.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        //wControls.stopGame();
        //wControls.startGame();
      }
    });
    
    initLights();
  }
  
  private void start() {
    wDigitsA.reset();
    wDigitsB.reset();
    wControls.startGame();

  }
  
  
  /**
   * do what when inputs are pushed
   * 
   * @param keyCode
   */
  public void move(int keyCode) {
    if (keyCode == "1".charAt(0)) {
      start();
      
    } else if (keyCode == "2".charAt(0)) {
      wControls.stopGame();
      
    } else if (keyCode == "3".charAt(0)) { // play test
      playLetters.playTest();
      
    } else if (keyCode == "4".charAt(0)) { // play thanks to google
      playLetters.playThanks_Google();
      
    } else if (keyCode == "5".charAt(0)) { // play thanks to everyone
      playLetters.playThanks_To();
      
    } else if (keyCode == "6".charAt(0)) { // all/on off
      displayAll();
      
    } else if (keyCode == "7".charAt(0)) { // init lights
      initLights();
      
    } else if (keyCode == "8".charAt(0)) { // start repeat
      // ignore this b/c it set on
      //System.out.println("start sending");
      //dmx.startSending();
      
    } else {
      wPadels.move(keyCode);
    }
  }

  
  private void initLights() {
    wButtonLights.init();
    move("a".charAt(0)); // make the paddel go back on
    move("l".charAt(0)); // make the padel go back on
  }
  
  /**
   * draw snow ball columns
   */
  private void drawSnowBallColumns() {
    int center = width / 2;
    int sbcenter = sb.getWidth() / 2;
    int left = center - sbcenter;
    ap.add(sb, left, 0);
  }
  
  /**
   * draw padels to catch snow balls
   */
  private void drawPadels() {
    int center = width / 2;
    int pcenter = wPadels.getWidth() / 2;
    int left = center - pcenter;
    ap.add(wPadels, left, sb.getHeight() - 5); // Note -5 will over lap the number nine ball
  }
  
  private void drawScore() {
    ap.add(wScoreA, 0,0);
    ap.add(wScoreB, 750, 0);
    
    ap.add(wDigitsA, 0, 400);
    ap.add(wDigitsB, 700, 400);
    wDigitsA.reset();
    wDigitsB.reset();
    
    wDigitsA.setTitle("Player 1");
    wDigitsB.setTitle("Player 2");
  }
  
  private void drawGameControls() {
    ap.add(wControls, 0, 555);
    wControls.addChangeHandler(this);
  }
  
  private void drawTimerSystem() {
    
    int center = width / 2;
    int widgetCenter = wTimerSystem.getOffsetWidth() / 2;
    int left = center - widgetCenter;
    
    int height = 420;
    
    ap.add(wTimerSystem, left, height);
    
    wTimerSystem.addChangeHandler(this);
  }

  private void checkScore() {
    
    int ballHitBotton = sb.getBallHitBottom();
    
    // is a or b in the vicinity of ball hitting bottom?
    int posa = wPadels.getPositionA();
    int posb = wPadels.getPositionB();
    
    boolean aScore = checkScore(ballHitBotton, posa);
    boolean bScore = checkScore(ballHitBotton, posb);

    if (aScore == true) {
      wScoreA.addScore();
      scoreA++;
      wDigitsA.drawNumber(scoreA);
    }
    
    if (bScore == true) {
      wScoreB.addScore();
      scoreB++;
      wDigitsB.drawNumber(scoreB);
    }
  
  }
  
  private void drawScorePos() {
    wPadels.setScorePos(2);
    wPadels.setScorePos(7);
    wPadels.setScorePos(11);
    wPadels.setScorePos(15);
    wPadels.setScorePos(19);
    wPadels.setScorePos(23);
    wPadels.setScorePos(27);
  }
  
  /**
   * did teh ball and the padel cross paths? if so score
   * 
   * @param ball
   * @param position
   * @return
   */
  private boolean checkScore(int ball, int position) {
    
    boolean b = false;
   
    for (int p=position; p <= (position + 3); p++) {
      
      if (ball == 1 && p == 2)         { // 2 - old 1,2,3
        b = true;
      } else if (ball == 2 && p == 7)  { // 7 - old 6,7,8
        b = true;
      } else if (ball == 3 && p == 11) { // 11 - old 10,11,12
        b = true;
      } else if (ball == 4 && p == 15) { // 15 - old 14,15,16
        b = true;
      } else if (ball == 5 && p == 19) { // 19 - old 18,19,20
        b = true;
      } else if (ball == 6 && p == 23) { // 23 - old 22,23,24
        b = true;
      } else if (ball == 7 && p == 27) { // 27 - old  26,27,28
        b = true;
      }
      
      if (b == true) {
        break;
      }
      
    }
    
    return b; 
  }
  
  private void drawWinner() {
    wWinner.setDigitControl(wDigitsA, wDigitsB, wButtonLights);
    ap.add(wWinner, 0, 200);
  }
  
  private void drawButtonLights() {
    ap.add(wButtonLights, 0, 525);
  }
    
  private void stop() {
    sb.stop();
    wTimerSystem.reset();
    wControls.stopGameNoEvent(); // stop game after win
  }
  
  private void getTimerEvent() {
    int event = wTimerSystem.getEvent();
    if (event == EventManager.END) {
      sb.stop();
      showWinner();
    }
  }
  
  private void getGameControlEvent() {
    int event = wControls.getEvent();
    if (event == EventManager.START) {
      wTimerSystem.start();
      wScoreA.resetScore();
      wScoreB.resetScore();
      sb.start();
      track.setTrack(TrackData.TYPE_START);
    } else if (event == EventManager.STOP) {
      stop();
      track.setTrack(TrackData.TYPE_STOP);
    } else if (event == EventManager.ALLON) {
      displayAll();
    } else if (event == EventManager.PLAYLETTERS) {
      playLetters.playTest();
    }
  }
  
  private void showWinner() {
    int scoreA = wDigitsA.getScore();
    int scoreB = wDigitsB.getScore();
    if (scoreA == scoreB) {
      wWinner.showWinner(EventManager.A, true);
      playLetters.playWinnerTie();
      track.setTrack(TrackData.TYPE_WIN_TIE);
      
    } else if (scoreA > scoreB) {
      wWinner.showWinner(EventManager.A, false);
      playLetters.playWinnerA();
      track.setTrack(TrackData.TYPE_WIN_P1);
      
    } else if (scoreA < scoreB) {
      wWinner.showWinner(EventManager.B, false);
      playLetters.playWinnerB();
      track.setTrack(TrackData.TYPE_WIN_P2);
    }
  }
  
  private void displayAll() {
    
    if (allOn == true) {
      allOn = false;
    } else if (allOn == false) {
      allOn = true;
    }
    
    sb.displayAll(allOn);
    wPadels.displayAll(allOn);
    wDigitsA.displayAll(allOn);
    wDigitsB.displayAll(allOn);
    wTimerSystem.displayAll(allOn);
    wWinner.displayAll(allOn);
    wButtonLights.displayAll(allOn);
  }
  
  public void onChange(ChangeEvent event) {
    
    Widget sender = (Widget) event.getSource();
    
    if (sender == sb) {
      checkScore();
    
    } else if (sender == wControls) {
      getGameControlEvent();
      int e = wControls.getEvent();
      if(e == EventManager.START) {
        wDigitsA.reset();
        wDigitsB.reset();
        scoreB = 0;
        scoreA = 0;
      }
      
    } else if (sender == wTimerSystem) {
      getTimerEvent();
      
    } else if (sender == wWinner) {
      stop();
    }
  }
  
}
