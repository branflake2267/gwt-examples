package com.tribling.gwt.test.feedback.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tribling.gwt.test.feedback.client.FeedbackData;
import com.tribling.gwt.test.feedback.client.FeedbackService;

 


/**
 * 
 * @author branflake2267
 *
 *
 */
public class FeedbackServiceImpl extends RemoteServiceServlet implements FeedbackService {

	
	/**
	 * constructor - nothing
	 */
	public FeedbackServiceImpl() {
	}
	
	/**
	 * save feedback 
	 * 
	 * @param SessionID
	 * @param feedbackData
	 * @return
	 */
	public boolean saveFeedBack(String SessionID, FeedbackData feedbackData) {
		
		DB_Feedback db = new DB_Feedback();
		boolean bol = db.saveFeedBack(SessionID, feedbackData);
		
		return bol;
	}
	
	/**
	 * debug output
	 * 
	 * Write the serialized response out to stdout. This is a very unusual thing
	 * to do, but it allows us to create a static file version of the response
	 * without deploying a servlet.
	 */
	protected void onAfterResponseSerialized(String serializedResponse) {
		System.out.println(serializedResponse);
	}




}
