package org.gonevertical.MultiFileUpload.client.rpc;

import org.gonevertical.MultiFileUpload.client.blobs.BlobData;
import org.gonevertical.MultiFileUpload.client.blobs.BlobDataFilter;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {

  public void getBlobStoreUrl(AsyncCallback<String> callback);

  public void getBlobs(BlobDataFilter filter, AsyncCallback<BlobData[]> callback);

  public void deleteBlob(BlobDataFilter filter, AsyncCallback<Boolean> callback);

  
}
