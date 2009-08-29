package com.gawkat.core.client.account.permission;

import com.gawkat.core.client.ClientPersistence;
import com.gawkat.core.client.rpc.RpcCoreServiceAsync;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThingPermissions extends Composite {

  private ClientPersistence cp = null;
  
  private RpcCoreServiceAsync rpc = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  
  public ThingPermissions(ClientPersistence cp) {
    this.cp = cp;
    
    initWidget(pWidget);
  }
  
}
