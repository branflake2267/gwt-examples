package org.gonevertical.democanvas.client.rpc;

import org.gonevertical.democanvas.client.BlobDataFilter;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {

  public void getBlobStoreUrl(BlobDataFilter filter, AsyncCallback<String> callback);
 
}
