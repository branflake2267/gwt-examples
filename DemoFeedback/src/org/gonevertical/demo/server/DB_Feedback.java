package org.gonevertical.demo.server;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.gonevertical.demo.client.feedback.FeedbackData;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.InternetHeaders;



public class DB_Feedback extends DB_Conn {

	public DB_Feedback() {
	}

	/**
	 * save feed back
	 * @param feedbackData
	 * @return
	 */
	public boolean saveFeedBack(FeedbackData feedbackData) {

		String fromEmail = feedbackData.fromEmail;
		String fromName = feedbackData.fromName;
		String subject = feedbackData.subject;
		String message = feedbackData.message;

		//prepare for mysql
		String sFromEmail = escapeForSql(fromEmail);
		String sFromName = escapeForSql(fromName);
		String sSubject = escapeForSql(subject);
		String sMessage = escapeForSql(message);

		String sSuggestion = "0";
		if (feedbackData.suggestion == true) {
			sSuggestion = "1";
		}

		String sComment = "0";
		if (feedbackData.comment == true) {
			sComment = "1";
		}

		String sProblem = "0";
		if (feedbackData.problem == true) {
			sProblem = "1";
		}

		String sOther = "0";
		if (feedbackData.other == true) {
			sOther = "1";
		}

		String sPost = "0";
		if (feedbackData.post == true) {
			sPost = "1";
		}

		String sql = "INSERT INTO `test`.`gwt_feedback` SET " +
			"FromEmail='" + sFromEmail + "', " +
			"FromName='" + sFromName + "', " +
			"Subject='" + sSubject + "', " +
			"Message='" + sMessage + "', " +
		  "Suggestion='" + sSuggestion + "', " +
		  "Comment='" + sComment + "', " +
		  "Problem='" + sProblem + "', " +
		  "Other='" + sOther + "', " +
		  "Post=" + sPost + ", " +
		  "DateCreated=NOW();";

		System.out.println(sql);
			
		try {
			Connection connection = getConn();
			Statement update = connection.createStatement();
			update.executeUpdate(sql);
			connection.close();
		} catch(Exception e) { 
			System.err.println("Mysql Statement Error: " + sql);
			e.printStackTrace();
			return false;
		}

		sendEmailNotification(feedbackData);

		return true;
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

	/**
	 * send email notification to joel and brandon
	 * 
	 * @param sub - feedback subject
	 * @param mess - feedback message
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	private void sendEmailNotification(FeedbackData feedbackData) {

		String fromEmail = feedbackData.fromEmail;
		String fromName = feedbackData.fromName;
		String sub = feedbackData.subject;
		String mess = feedbackData.message;

		String to = "branflake2267@gmail.com";
		String subject = "Feedback sent from gawkat.com";
		Date date = new Date();

		//convert booleans into text
		String sChecked = getChecked(feedbackData);

		String body = "Feedback submitted on gawkat.com<br><br>\n";
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


		try {
			sendMail(to, subject, body, date);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}


	/**
	 * send email
	 * 
	 * @param args
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	private void sendMail(String to, String subject, String body, Date date)
	throws UnsupportedEncodingException, MessagingException {

		InternetHeaders hdr = new InternetHeaders();
		hdr.addHeader("Content-Type", "text/html; charset=iso-8859-1");

		//EmailDelivery ed = new EmailDelivery();
		//ed.setTo(to);
		//ed.setFrom("server@gawkat.com", "gawkat server");
		//ed.setSubject(subject);
		//ed.setBody(body);
		//ed.setSMTPHost("192.168.12.80", "userName", "password");
		//ed.sendMsg();

	}
}
