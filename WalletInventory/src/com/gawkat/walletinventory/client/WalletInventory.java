package com.gawkat.walletinventory.client;

import com.gawkat.walletinventory.client.item.Items;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WalletInventory implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    VerticalPanel vp = new VerticalPanel();
    RootPanel.get().add(vp);
    vp.setWidth("100%");
    
    Items items = new Items();
    vp.add(items);
    items.draw();
    
    items.addStyleName("wi-home");
    
    vp.setCellHorizontalAlignment(items, HorizontalPanel.ALIGN_CENTER);
  }
  
  
}
