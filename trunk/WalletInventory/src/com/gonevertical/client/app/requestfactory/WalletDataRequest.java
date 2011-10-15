package com.gonevertical.client.app.requestfactory;

import java.util.List;

import com.gonevertical.client.app.requestfactory.dto.WalletDataProxy;
import com.gonevertical.server.domain.WalletData;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(WalletData.class)
public interface WalletDataRequest extends RequestContext {

  Request<WalletDataProxy> findWalletData(Long id);
  
  Request<Long> countAll();
  
  Request<Long> countWalletDataByUser();
  
  Request<List<WalletDataProxy>> findWalletDataByUser();
  
  InstanceRequest<WalletDataProxy, WalletDataProxy> persist();
  
  InstanceRequest<WalletDataProxy, Void> remove();
}
