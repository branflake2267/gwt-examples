package com.gawkat.walletinventory.client.rpc;

import com.gawkat.walletinventory.client.item.FilterData;
import com.gawkat.walletinventory.client.item.ItemData;
import com.gawkat.walletinventory.client.login.LoginData;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart
 */
public interface RpcServiceAsync {

  public void getLoginData(String requestUri, AsyncCallback<LoginData> callback);
  
  public void getItemData(FilterData filter, AsyncCallback<ItemData[]> callback);
  
  public void saveItemData(FilterData filter, ItemData[] itemData, AsyncCallback<ItemData[]> callback);
  
}
