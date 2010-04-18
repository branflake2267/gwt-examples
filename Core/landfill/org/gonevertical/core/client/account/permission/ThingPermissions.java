package org.gonevertical.core.client.account.permission;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

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
