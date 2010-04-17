package org.gonevertical.demo.client;

import org.gonevertical.demo.client.feedback.FeedbackData;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RpcServiceAsync {
 
	public void saveFeedBack(FeedbackData feedbackData, AsyncCallback<Boolean> callback);
	
}
