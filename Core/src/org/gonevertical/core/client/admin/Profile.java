package org.gonevertical.core.client.admin;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Profile extends Composite {
  
  private ClientPersistence cp = null;
  
  private RpcCoreServiceAsync rpc = null;
  
  private VerticalPanel pWidget = new VerticalPanel();

  public Profile(ClientPersistence cp) {
    this.cp = cp;
    
    initWidget(pWidget);
    
    pWidget.setWidth("100%");
  }
  
  public void draw() {
    
    pWidget.clear();
    pWidget.add(new HTML("not setup yet"));
    
  }
}
