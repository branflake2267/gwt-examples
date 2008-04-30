package com.tribling.gwt.test.feedback.client;

import com.google.gwt.user.client.rpc.AsyncCallback;




/**
 * The interface for the RPC server endpoint that provides GoneVertical
 * information for clients that will be calling aysychronously. 
 */
public interface FeedbackServiceAsync {

	/**
	 * Save Feedback via rpc
	 * 
	 * @param SessionID
	 * @param feedbackData
	 * @return
	 */
	public void saveFeedBack(String SessionID, FeedbackData feedbackData, AsyncCallback callback);
	
	
}
