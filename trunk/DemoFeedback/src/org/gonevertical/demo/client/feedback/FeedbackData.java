package org.gonevertical.demo.client.feedback;

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

	// string
	public String fromEmail;
	public String fromName;
	public String subject;
	public String message;
	
	// checkboxes
	public boolean suggestion;
	public boolean comment;
	public boolean problem;
	public boolean other;
	public boolean post;
	
	
}
