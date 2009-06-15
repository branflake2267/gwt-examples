package com.gawkat.walletinventory.client.rpc;

import com.gawkat.walletinventory.client.item.FilterData;
import com.gawkat.walletinventory.client.item.ItemData;
import com.gawkat.walletinventory.client.login.LoginData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpcservice")
public interface RpcService extends RemoteService {
  
  public LoginData getLoginData(String requestUri);
  
  public ItemData[] getItemData(FilterData filter);
  
  public ItemData[] saveItemData(FilterData filter, ItemData[] itemData);
  
}
