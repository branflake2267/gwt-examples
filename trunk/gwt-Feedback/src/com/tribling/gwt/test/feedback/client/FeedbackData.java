package com.tribling.gwt.test.feedback.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * feedback data
 * 
 * transport the feedback data around with this container
 * 
 * @author branflake2267
 *
 */
public class FeedbackData implements IsSerializable {

	public String fromEmail;
	public String fromName;
	public String subject;
	public String message;
	
	//About
	public boolean suggestion;
	public boolean comment;
	public boolean problem;
	public boolean other;
	public boolean post;
	
	
}
