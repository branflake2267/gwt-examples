package org.gonevertical.upload.client.rpc;

import org.gonevertical.upload.client.blobs.BlobData;
import org.gonevertical.upload.client.blobs.BlobDataFilter;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpc")
public interface RpcService extends RemoteService {
	
	public String getBlobStoreUrl();
	
	 public BlobData[] getBlobs(BlobDataFilter filter);
}
