package org.gonevertical.demo.server;

import org.gonevertical.demo.client.RpcService;
import org.gonevertical.demo.client.feedback.FeedbackData;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	/**
	 * save feedback 
	 * 
	 * @param SessionID
	 * @param feedbackData
	 * @return
	 */
	public boolean saveFeedBack(FeedbackData feedbackData) {
		
		DB_Feedback db = new DB_Feedback();
		boolean bol = db.saveFeedBack(feedbackData);
		
		return bol;
	}
 
}
