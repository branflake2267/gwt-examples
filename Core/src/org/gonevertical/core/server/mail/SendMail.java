package org.gonevertical.core.server.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.gonevertical.core.server.ServerPersistence;

public class SendMail {
	
	private static final Logger log = Logger.getLogger(SendMail.class.getName());
 
	private ServerPersistence sp;

	public SendMail(ServerPersistence sp) {
		this.sp = sp;
	}

	public boolean sendMail(String to, String from, String subject, String msgBody) {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		boolean b = false;
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from, from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to, to));
			msg.setSubject(subject);
			msg.setText(msgBody);
			Transport.send(msg);
			b = true;
		} catch (AddressException e) {
			// TODO 
			e.printStackTrace();
			b = false;
		} catch (MessagingException e) {
			// TODO 
			e.printStackTrace();
			b = false;
		} catch (UnsupportedEncodingException e) {
			// TODO 
			e.printStackTrace();
			b = false;
		}

		return b;
	}


}
