package com.tribling.gwt.test.feedback.server;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;

import com.tribling.gwt.test.feedback.client.FeedbackData;
import com.tribling.gwt.test.feedback.server.email.EmailDelivery;



public class DB_Feedback extends DB_Conn {

	/**
	 * constructor - init nothing
	 */
	public DB_Feedback() {
		
	}
	
	/**
	 * save feedback
	 * 
	 * @param SessionID
	 * @param feedbackData
	 * @return
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	public boolean saveFeedBack(String SessionID, FeedbackData feedbackData) {
		
		// I use this in my login system
		//int UserID = getUserID(SessionID);
		//String sUserID = Integer.toString(UserID);
		String sUserID = "0";
		
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
			
		String Query = "INSERT INTO `gv_feedback` SET " +
				"UserID='"+sUserID+"', FromEmail='"+sFromEmail+"', FromName='"+sFromName+"', Subject='"+sSubject+"', Message='"+sMessage+"', " +
				"Suggestion='"+sSuggestion+"', Comment='"+sComment+"', Problem='"+sProblem+"', Other='"+sOther+"', Post="+sPost+", " +
				"DateCreated=UNIX_TIMESTAMP();";
		
		//System.out.println("Query: " + Query);
		
		int id = 0;
		try {
        	Connection connection = getConnMaster();
            Statement update = connection.createStatement();
            update.executeUpdate(Query);
           
            //debug
            //System.out.println("Saved Email " + Query);

            //get last id
            ResultSet rs = update.getGeneratedKeys(); 

            if (rs != null && rs.next()) { 
            	id = rs.getInt(1);  //unique record to which the session is stored
            }
            
            connection.close();
        } catch(Exception e) { 
        	System.err.println("Mysql Statement Error: " + Query);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
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
		
		EmailDelivery ed = new EmailDelivery();
		ed.setTo(to);
		ed.setFrom("server@gawkat.com", "gawkat server");
		ed.setSubject(subject);
		ed.setBody(body);
		ed.setSMTPHost("192.168.12.80", "userName", "password");
		ed.sendMsg();

	}
}
