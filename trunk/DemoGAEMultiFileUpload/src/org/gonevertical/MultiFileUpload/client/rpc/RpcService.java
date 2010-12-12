package org.gonevertical.MultiFileUpload.client.rpc;

import org.gonevertical.MultiFileUpload.client.BlobData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpcService")
public interface RpcService extends RemoteService {

  public String getBlobStoreUrl();
  
}
