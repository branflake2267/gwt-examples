package org.gonevertical.bubbleup.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

public class LeftSideWidget extends Composite {
  private VerticalPanel pList;
  private ClientPersistence cp;

  public LeftSideWidget(ClientPersistence cp) {
    this.cp = cp;
    
    VerticalPanel verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    
    HTML htmlLeftSideWidget = new HTML("Left Side Widget: Show Events That Happen In other Widgets. &nbsp;&nbsp;&nbsp;&nbsp;", true);
    verticalPanel.add(htmlLeftSideWidget);
    
    pList = new VerticalPanel();
    verticalPanel.add(pList);
    
    setup();
  }

  private void setup() {
    
    if (cp != null) {
      
      // global app observation
      cp.addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {
          int e = cp.getChangeEvent();
          if (e == EventManager.GLOBALBUTTONCLICK) {
            setText("Global button clicked");
          }
        }
      });
      
    }
    
  }

  public void setText(String s) {
    pList.add(new HTML(s));
  }

  public VerticalPanel getPList() {
    return pList;
  }
}
