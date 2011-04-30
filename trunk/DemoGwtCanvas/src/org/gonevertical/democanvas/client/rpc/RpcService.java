package org.gonevertical.democanvas.client.rpc;

import org.gonevertical.democanvas.client.BlobDataFilter;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpc")
public interface RpcService extends RemoteService {
  
  public String getBlobStoreUrl(BlobDataFilter filter);
  
}
