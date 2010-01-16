package com.gawkat.core.client.account.thing.hierarchy;

import com.gawkat.core.client.ClientPersistence;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HierarchyView extends Composite {
  
  private ClientPersistence cp = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  public HierarchyView(ClientPersistence cp) {
    this.cp = cp;
    
    initWidget(pWidget);
  }

  public void draw() {
    getHierarchyRpc();
  }
  
  private void getHierarchyRpc() {
    
  }
  
}
