package com.gawkat.core.client.account.thing.ownership;

import com.gawkat.core.client.ClientPersistence;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OwnershipView extends Composite {
  
  private ClientPersistence cp = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  public OwnershipView(ClientPersistence cp) {
    this.cp = cp;
    
    initWidget(pWidget);
  }

  public void draw() {
    getHierarchyRpc();
  }
  
  private void getHierarchyRpc() {
    
  }
  
}
