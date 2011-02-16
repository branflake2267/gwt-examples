package org.gonevertical.bubbleup.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class ChildInputTextWidget extends Composite {

  private int changeEvent;
  private ClientPersistence cp;

  public ChildInputTextWidget(ClientPersistence cp) {
    this.cp = cp;
    
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    verticalPanel.add(horizontalPanel);
    
    TextBox txtbxFocusOnMe = new TextBox();
    txtbxFocusOnMe.addFocusHandler(new FocusHandler() {
      public void onFocus(FocusEvent event) {
        fireChange(EventManager.TEXTBOXFOCUSINCHILDWIDGET);
      }
    });
    txtbxFocusOnMe.setText("Focus on me");
    horizontalPanel.add(txtbxFocusOnMe);
    
    PushButton pshbtnClickMe = new PushButton("Click Me");
    pshbtnClickMe.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        fireChange(EventManager.BUTTONCLICKEDINCHILDWIDGET);
      }
    });
    horizontalPanel.add(pshbtnClickMe);
    
    HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
    verticalPanel.add(horizontalPanel_1);
    
    PushButton pshbtnFireGlobalClick = new PushButton("Fire Global Click");
    pshbtnFireGlobalClick.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        setGlobalClick();
      }
    });
    horizontalPanel_1.add(pshbtnFireGlobalClick);
  }
  
  private void setGlobalClick() {
    cp.fireChange(EventManager.GLOBALBUTTONCLICK);
  }

  public int getChangeEvent() {
    return changeEvent;
  }
  
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

}
