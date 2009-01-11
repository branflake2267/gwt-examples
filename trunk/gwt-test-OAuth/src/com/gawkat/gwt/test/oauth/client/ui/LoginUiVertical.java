package com.gawkat.gwt.test.oauth.client.ui;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

//TODO
public class LoginUiVertical extends Composite {

  private ChangeListenerCollection changeListeners;
  private int changeEvent;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  /**
   * constructor - init widget
   */
  public LoginUiVertical() {
    
    initWidget(pWidget);
    
  }
  
  /**
   * draw the widget
   */
  public void draw() {
  }
  
  public int getChangeEvent() {
    return changeEvent;
  }
  
  private void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    
    if (changeListeners != null) {
      changeListeners.fireChange(this);
    }
  }
  
  public void addChangeListener(ChangeListener listener) {
    if (changeListeners == null)
      changeListeners = new ChangeListenerCollection();
    changeListeners.add(listener);
  }
  
  public void removeChangeListener(ChangeListener listener) {
    if (changeListeners != null)
      changeListeners.remove(listener);
  }

 
  
}
