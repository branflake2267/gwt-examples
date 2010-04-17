package org.gonevertical.demo.client;

import org.gonevertical.demo.client.feedback.FeedbackData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpcService")
public interface RpcService extends RemoteService {
 
	public boolean saveFeedBack(FeedbackData feedbackData);
	
}
