package org.gonevertical.bubbleup.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

public class ClientPersistence extends Composite {

  private int changeEvent;

  public ClientPersistence() {
  }
  
  /**
   * cordinate events with the top parent
   * @param changeEvent
   */
  public void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  public void resetEvent() {
    changeEvent = EventManager.ZERO;
  }

  public int getChangeEvent() {
    return changeEvent;
  }
  
  
}
