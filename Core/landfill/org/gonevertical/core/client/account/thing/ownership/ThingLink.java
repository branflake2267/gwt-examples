package org.gonevertical.core.client.account.thing.ownership;

import org.gonevertical.core.client.ClientPersistence;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThingLink extends Composite {
  
  private ClientPersistence cp = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  public ThingLink(ClientPersistence cp) {
    this.cp = cp;
    
    initWidget(pWidget);
  }

  public void draw() {
    getHierarchyRpc();
  }
  
  private void getHierarchyRpc() {
    
  }
  
}
