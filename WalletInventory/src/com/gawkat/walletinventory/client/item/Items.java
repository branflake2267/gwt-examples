package com.gawkat.walletinventory.client.item;

import com.gawkat.walletinventory.client.rpc.Rpc;
import com.gawkat.walletinventory.client.rpc.RpcServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Items extends Composite {

  private RpcServiceAsync rpc = null;
  
  private VerticalPanel pWidget = new VerticalPanel();
  private VerticalPanel pMenu = new VerticalPanel();
  private VerticalPanel pItems = new VerticalPanel();
  private VerticalPanel pNotify = new VerticalPanel();
  
  private PushButton bSave = new PushButton("Save");
  
  private PushButton bAdd = new PushButton("Add");
  
  /**
   * contructor - init widget
   */
  public Items() {
  
    pWidget.add(pMenu);
    pWidget.add(pItems);
    
    initWidget(pWidget);
    
    pWidget.addStyleName("wi-items");
    
    rpc = Rpc.init();
    
    drawMenu();
  }
  
  public void draw() {
    getItemsRpc();
  }
  
  private void drawMenu() {
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bAdd);
    hp.add(new HTML("&nbsp;"));
    hp.add(bSave);
    hp.add(new HTML("&nbsp;"));
    hp.add(pNotify);
    
    pMenu.add(hp);
  }
  
  private void processGet(ItemData[] itemData) {
    if (itemData == null) {
      drawblank();
      return;
    }
    
    for (int i=0;i < itemData.length; i++) {
      Item item = new Item();
      pItems.add(item);
      item.setData(itemData[i]);
    }
    
  }
  
  private void drawblank() {
    Item item = new Item();
    pItems.add(item);
    item.setData(new ItemData());
  }

  public void getItemsRpc() {
    
    // TODO - later
    FilterData filter = new FilterData();
    
    rpc.getItemData(filter, new AsyncCallback<ItemData[]>( ) {
      public void onSuccess(ItemData[] itemData) {
        processGet(itemData);
      }
      public void onFailure(Throwable caught) {
        // TODO - later
      }
    });
    
  }
  
}
