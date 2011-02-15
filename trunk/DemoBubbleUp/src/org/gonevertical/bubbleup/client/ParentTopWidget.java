package org.gonevertical.bubbleup.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ParentTopWidget extends Composite {

  private ClientPersistence cp;
  private LeftSideWidget leftSideWidget;

  public ParentTopWidget(ClientPersistence cp) {
    this.cp = cp;
    
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    verticalPanel.add(horizontalPanel);
    
    leftSideWidget = new LeftSideWidget(cp);
    horizontalPanel.add(leftSideWidget);
    
    ChildInputTextWidget childInputTextWidget = new ChildInputTextWidget(cp);
    childInputTextWidget.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        ChildInputTextWidget citw = (ChildInputTextWidget) event.getSource();
        int e = citw.getChangeEvent();
        if (e == EventManager.TEXTBOXFOCUSINCHILDWIDGET) {
          setText("Text box focus");
        } else if (e== EventManager.BUTTONCLICKEDINCHILDWIDGET) {
          setText("Button clicked");
        }
      }
    });
    horizontalPanel.add(childInputTextWidget);
    
    setup();
  }

  private void setText(String s) {
   leftSideWidget.setText(s); 
  }

  private void setup() {
    if (cp != null) {
      // global app observation
      cp.addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {

        }
      });
    }
  }

  public LeftSideWidget getLeftSideWidget() {
    return leftSideWidget;
  }
}
