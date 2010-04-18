package org.gonevertical.core.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class BreadCrumb extends Composite {

  private HorizontalPanel pWidget = new HorizontalPanel();
  
  public BreadCrumb(String name, String link) {
    
    Hyperlink h = new Hyperlink(name, link);
    
    pWidget.add(h);
    pWidget.add(new HTML("&nbsp;&#187;&nbsp;"));
    
    initWidget(pWidget);
  }
  
  public BreadCrumb(String name) {
    
    HTML h = new HTML("<a>" + name + "</a>");
    
    pWidget.add(h);
    pWidget.add(new HTML("&nbsp;&#187;&nbsp;"));
    
    initWidget(pWidget);
  }
}
