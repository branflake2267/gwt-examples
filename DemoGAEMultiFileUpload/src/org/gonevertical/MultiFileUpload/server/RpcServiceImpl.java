package org.gonevertical.MultiFileUpload.server;

import org.gonevertical.MultiFileUpload.client.BlobData;
import org.gonevertical.MultiFileUpload.client.rpc.RpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  /**
   * store server persistence items
   */
  private ServerPersistence sp = new ServerPersistence();
  
  public String getBlobStoreUrl() {
    sp.start(getThreadLocalRequest());
    BlobInfoJdo bij = new BlobInfoJdo(sp);
    String url = bij.getBlobStoreUrl();
    sp.end();
    return url;
  }
  
}
