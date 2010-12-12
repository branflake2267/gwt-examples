package org.gonevertical.MultiFileUpload.client.rpc;

import org.gonevertical.MultiFileUpload.client.BlobData;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {

  public void getBlobStoreUrl(AsyncCallback<String> callback);

}
