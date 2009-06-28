package com.gawkat.walletinventory.server;

import com.gawkat.walletinventory.client.item.FilterData;
import com.gawkat.walletinventory.client.item.ItemData;
import com.gawkat.walletinventory.client.login.LoginData;
import com.gawkat.walletinventory.client.rpc.RpcService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {


  public LoginData getLoginData(String requestUri) {
    LoginServer login = new LoginServer();
    return login.getLoginData(requestUri);
  }

  public ItemData[] getItemData(FilterData filter) {
    ItemDataServer ids = new ItemDataServer();
    return ids.getItemData(filter);
  }
  
  public ItemData[] saveItemData(FilterData filter, ItemData[] itemData) {
    ItemDataServer ids = new ItemDataServer();
    return ids.saveItemData(filter, itemData);
  }
  
}
