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
  
    TextBoxExpandWidget tb = new TextBoxExpandWidget();
    
    TextAreaExpandWidget ta = new TextAreaExpandWidget();
    
    HorizontalPanel hp1 = new HorizontalPanel();
    hp1.add(new HTML("TextBox"));
    hp1.add(tb);
    
    HorizontalPanel hp2 = new HorizontalPanel();
    hp2.add(new HTML("TextArea"));
    hp2.add(ta);
    
    VerticalPanel vp2 = new VerticalPanel();
    vp2.add(new HTML("Type in the input boxes continously to see the inputs expand."));
    vp2.add(hp1);
    vp2.add(new HTML("&nbsp;"));
    vp2.add(hp2);
    
    
    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    vp.add(vp2);
    vp.setCellHorizontalAlignment(vp2, HorizontalPanel.ALIGN_CENTER);
    
    // add to root panel
    RootPanel.get().add(vp);
  }

}