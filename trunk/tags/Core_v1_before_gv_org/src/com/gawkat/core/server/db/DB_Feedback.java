package com.gawkat.core.server.db;

import java.util.Date;

import com.gawkat.core.client.ui.feedback.FeedbackData;
import com.gawkat.core.server.ServerPersistence;

public class DB_Feedback {

	private ServerPersistence sp;
	
	public DB_Feedback(ServerPersistence sp) {
		this.sp = sp;
	}

	public boolean sendFeedback(FeedbackData feedbackData) {

		String fromEmail = feedbackData.fromEmail;
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
		body += "<b>From Email:</b> " + fromEmail + "<br>\n";
		body += "<b>From Name:</b> " + fromName + "<br><br>\n";
		body += "";
		body += "<b>Checked:</b> " + sChecked + "<br><br>\n";
		body += "";
		body += "<b>Subject:</b> " + sub + "<br>\n";
		body += "";
		body += "<b>Message:</b> " + mess + "<br>\n";
		body += "";
  
  	boolean b = false;
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
