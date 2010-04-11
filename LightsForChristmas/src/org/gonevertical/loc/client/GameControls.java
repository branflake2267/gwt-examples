package org.gonevertical.loc.client;

import org.gonevertical.loc.client.rpc.Rpc;
import org.gonevertical.loc.client.rpc.RpcServiceAsync;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GameControls extends Composite implements ClickHandler {
  
  private RpcServiceAsync rpc = null;
  
  private int event = 0;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  private PushButton bStart = new PushButton("Start");
  private PushButton bStop = new PushButton("Stop");
  private PushButton bAllOn = new PushButton("All On/Off");
  private PushButton bRunTest = new PushButton("Run Test");
  private PushButton bLet = new PushButton("Run Letters");
  
  private boolean gameRunning = false;

  /**
   * constructor - init game controls
   */
  public GameControls() {
    
    HorizontalPanel hp = new HorizontalPanel();
    
    String s = "Move P1: a=L b=R, P2: j=L, l=R <br> 1=Start, 2=Stop, 3=TestLetters, 4=ThanksGoogle, 5=ThanksTo, 6=AllOn, 7=InitLights";
    
    hp.add(bAllOn);
    hp.add(new HTML("&nbsp;&nbsp;"));
    hp.add(bStart);
    hp.add(new HTML("&nbsp;&nbsp;"));
    hp.add(bStop);
    hp.add(new HTML("&nbsp;&nbsp;"));
    hp.add(bRunTest);
    hp.add(new HTML("&nbsp;&nbsp;" + s));
    hp.add(new HTML("&nbsp;&nbsp;"));
    hp.add(bLet);
    
    
    pWidget.add(hp);
    
    initWidget(pWidget);
    
    bStart.addClickHandler(this);
    bStop.addClickHandler(this);
    bAllOn.addClickHandler(this);
    bRunTest.addClickHandler(this);
    bLet.addClickHandler(this);
    
    rpc = Rpc.initRpc();
  }

  public void startGame() {
    if (gameRunning == false) {
      fire(EventManager.START);
      gameRunning = true;
    }
  }
  
  public void stopGame() {
    if (gameRunning == true) {
      fire(EventManager.STOP);
      gameRunning = false;
    }
  }
  
  public void stopGameNoEvent() {
    if (gameRunning == true) {
      gameRunning = false;
    }
  }
  
  public int getEvent() {
    return event;
  }
  
  public void onClick(ClickEvent event) {
    Widget sender = (Widget) event.getSource();
    if (sender == bStart) {
      startGame();
    } else if (sender == bStop) {
      stopGame();
    } else if (sender == bAllOn) {
      fire(EventManager.ALLON);
    } else if (sender == bRunTest) {
      runTest();
    } else if (sender == bLet) {
      fire(EventManager.PLAYLETTERS);
    }
  }
  
  private void fire(int event) {
    this.event = event;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  private void runTest() {
    
    rpc.runTest(new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean result) {
      }
      public void onFailure(Throwable caught) {
      }
    });
    
  }
}
