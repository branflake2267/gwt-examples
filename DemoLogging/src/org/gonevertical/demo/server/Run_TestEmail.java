package org.gonevertical.demo.server;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SMTPAppender;

public class Run_TestEmail {

	public static Logger logger = Logger.getLogger(Run_TestEmail.class);
	
	private SMTPAppender appender = new SMTPAppender(); 
	
	public static void main(String args[]) {
		Run_TestEmail test = new Run_TestEmail();		
	}
	
	public Run_TestEmail() {
		try {
			appender.setTo("");
			appender.setFrom("");
			appender.setSMTPHost("");
			appender.setLocationInfo(true);
			appender.setSubject("Test Mail From Log4J");
			appender.setLayout(new PatternLayout());
			appender.activateOptions();
		  
			logger.addAppender(appender);			
		  logger.error("Hello World");
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error("Printing ERROR Statements",e);
		}
	} 
	
	
	
}