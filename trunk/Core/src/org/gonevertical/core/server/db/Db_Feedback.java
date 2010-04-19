package org.gonevertical.core.server.db;

import java.util.Date;

import org.gonevertical.core.client.ui.feedback.FeedbackData;
import org.gonevertical.core.server.ServerPersistence;
import org.gonevertical.core.server.mail.SendMail;

public class Db_Feedback {
 
	private ServerPersistence sp;
	
	public Db_Feedback(ServerPersistence sp) {
		this.sp = sp;
	}

	public boolean sendFeedback(FeedbackData feedbackData) {

		String from = feedbackData.fromEmail;
		String fromName = feedbackData.fromName;
		String sub = feedbackData.subject;
		String mess = feedbackData.message;

		String to = "branflake2267@gmail.com";
		String subject = "Feedback sent from Core";
		Date date = new Date();

		//convert booleans into text
		String sChecked = getChecked(feedbackData);

		String body = "Feedback submitted on Core<br><br>\n";
		body += "";
		body += "<b>From Email:</b> " + from + "<br>\n";
		body += "<b>From Name:</b> " + fromName + "<br><br>\n";
		body += "";
		body += "<b>Checked:</b> " + sChecked + "<br><br>\n";
		body += "";
		body += "<b>Subject:</b> " + sub + "<br>\n";
		body += "";
		body += "<b>Message:</b> " + mess + "<br>\n";
		body += "";
  
  	boolean b = false;
  	
  	SendMail sm = new SendMail(sp);
		b = sm.sendMail(to, from, subject, body);
		
  	return b;
  }
	
	private String getChecked(FeedbackData feedbackData) {

		String s = "";

		if (feedbackData.suggestion == true) {
			s += "Suggestion, ";
		}

		if (feedbackData.comment == true) {
			s += "Comment, ";
		}

		if (feedbackData.problem == true) {
			s += "Problem, ";
		}

		if (feedbackData.other == true) {
			s += "Other, ";
		}

		if (feedbackData.post == true) {
			s += "Post";
		}

		return s;
	}

}
