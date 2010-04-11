package org.gonevertical.loc.client;

import org.gonevertical.loc.client.digit.Digits;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TimerSystem extends Composite {

  private VerticalPanel pWidget = new VerticalPanel();
  
  private Digits wDigits = new Digits();
  
  // how many play time seconds - indexStart
  private int indexStart = SnowBallGameFrame.TIMERLENGTH;
  private int totalSeconds = 0;
  
  private int event = 0;
  
  private boolean loop = false;
  
  public TimerSystem() {
    
    pWidget.add(wDigits);
    
    initWidget(pWidget);
    
    pWidget.setStyleName("TimerSystem");
    
    wDigits.reset();
    
    wDigits.setTitle("Timer");
  }
  
  public void start() {
    totalSeconds = indexStart;
    loop = true;
    countDown();
  }
  
  public void reset() {
    loop = false;
    wDigits.reset();
  }
  
  public int getEvent() {
    return event;
  }
  
  private void drawCount() {
    wDigits.drawTime(totalSeconds);
  }

  private void countDown() {
    Timer timer = new Timer() {
      public void run() {
        if (loop == true) {
          moveIndex();
          drawCount();
          countDown();
        }
      }
    };
    timer.schedule(996);
  }
  
  private void moveIndex() {
    totalSeconds--;
    
    if (totalSeconds <= 0) {
      reset();
      fire(EventManager.END);
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

  public void displayAll(boolean b) {
    wDigits.displayAll(b);
  }

  public boolean[] getMap() {
    boolean[] m = wDigits.getMap(); // 21
    return m;
  }
  
}
