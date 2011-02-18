package org.gonevertical.textboxexpand.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DemoTextBoxExpand implements EntryPoint {

  
  public void onModuleLoad() {
  
    TextBoxExpandWidget w = new TextBoxExpandWidget();
    
    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    vp.add(w);
    vp.setCellHorizontalAlignment(w, HorizontalPanel.ALIGN_CENTER);
    
    // add to root panel
    RootPanel.get().add(vp);
    
    
  }

}