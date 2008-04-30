package com.tribling.gwt.test.feedback.client;

import com.google.gwt.user.client.rpc.RemoteService;




/**
 * The interface for the RPC server endpoint to get GoneVertical
 * information.
 */
public interface FeedbackService extends RemoteService {

	
	/**
	 * save feedback via rpc
	 * 
	 * @param SessionID
	 * @param feedbackData
	 * @return
	 */
	public boolean saveFeedBack(String SessionID, FeedbackData feedbackData);
}
