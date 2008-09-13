package com.tribling.gwt.test.feedback.server.email;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * from
 * http://timarcher.com/?q=node/53
 * 
 * Create a multipart message with the second block of the 
 * message being the given file for an attachment.
 * 
 * Has support for SMTP authentication to authenticate to an 
 * SMTP server to send the message.
 * Utilizes the apache commons codec library to Base64 encode the file attachment.
 *
 * @author  Tim Archer 09/30/2003
 * @version $Revision: 1.1 $
 */
public class EmailDelivery {
    static Logger log = Logger.getLogger(EmailDelivery.class);
    
    /** Static variable representing a high priority message. */
    public final static String HIGH_PRIORITY = "1";
    /** Static variable representing a normal priority message. */
    public final static String NORMAL_PRIORITY = "3";
    /** Static variable representing a low priority message. */
    public final static String LOW_PRIORITY = "5";
    
    EmailMimeMessage msg = null;
    Session session = null;
    Properties props = null;
    Multipart mp = null;
        
    /** The SMTP host used to send the message. */
    String smtp_host = new String("");
    /** If using SMTP authentication, then this is the username to login to the SMTP server as. */
    String smtp_username = null;
    /** If using SMTP authentication, then this is the password for the username to login to the SMTP server as. */
    String smtp_password = null;
    /** The port the SMTP server is listening on. */
    int smtp_port = 25;
    
    /** If we are to use SMTP authentication when sending the message. */
    boolean usingAuthentication = false;

    //getting rid of the multipart message for now
	private String htmlText = null;

	//set the date to there timezone
	private Date emailDate = null;
    
    
    /**
     * Create a new object to send email messages.
     *
     */      
    public EmailDelivery() {
        props = System.getProperties();
        session = Session.getDefaultInstance(props, null);
        session.setDebug(false);
        msg = new EmailMimeMessage(session);
        mp = new MimeMultipart();
    }
    
    /**
     * Set the SMTP host used for sending the mail message.
     *
     * @param p_host The SMTP host to use for sending the messgae.
     * @throws MessagingException If an error occurs.
     *
     */      
    public void setSMTPHost(String p_host) throws MessagingException {
        this.setSMTPHost(p_host, null, null);
    }
    
    /**
     * Set the SMTP host used for sending the mail message.
     * This method allows a username and password to be specified for SMTP authentication.
     *
     * @param p_host The SMTP host to use for sending the messgae.
     * @param p_username The username to log into the SMTP server with.
     * @param p_password The password for the username to log into the SMTP server with.
     * @throws MessagingException If an error occurs.
     *
     */      
    public void setSMTPHost(String p_host, String p_username, String p_password) throws MessagingException {
        log.debug("EmailDelivery in setSMTPHost Method. Host: "+p_host+" Username: "+p_username);
        smtp_host = p_host;
        smtp_username = p_username;
        smtp_password = p_password;

        props.put("mail.smtp.host", smtp_host);
        props.setProperty("mail.smtp.localhost", smtp_host);
        //
        //Determine if we are using authentication or not
        //
        if ((smtp_username != null && smtp_username.length() > 0) ||
             (smtp_password != null && smtp_password.length() > 0)) {
            props.put("mail.smtp.auth", "true");
            usingAuthentication = true;
        }
        else {
            usingAuthentication = false;
        }
        log.debug("EmailDelivery in setSMTPHost Method. UsingAuthenticaton: "+usingAuthentication);
    }
 
    /**
     * Set the port that the SMTP server is listening on.
     *
     * @param p_port The port the SMTP server is listening on.
     * @throws Exception If an error occurs.
     *
     */      
    public void setSMTPPort(int p_port) throws Exception {
        smtp_port = p_port;
        props.put("mail.smtp.port", String.valueOf(smtp_port));
    }    

    /**
     * Set who the email message is to. 

     * The to email address may be a comma separated list of email addresses so the message will be sent to multiple recipients. 

     *
     * @param p_to The email address of who the message is to. e.g. "jdoe@test.com"
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */      
    public void setTo(String p_to) throws MessagingException, UnsupportedEncodingException {
        this.setTo(p_to, null);
    }
    
    /**
     * Set who the email message is to. 

     * The to email address may be a comma separated list of email addresses so the message will be sent to multiple recipients. 

     * If the message is sent to multiple recipients, then the name parameter of who to send to is ignored, and the
     * displayed recipient name is the recipient email address.
     *
     * @param p_to The email address of who the message is to. e.g. "jdoe@test.com"
     * @param p_to_name The textual name of who the message is to. e.g. "John Doe". This parameter is ignored if the to email address
     *                  is a comma separated list of multiple email recipients.
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */      
    public void setTo(String p_to, String p_to_name) throws MessagingException, UnsupportedEncodingException {
        this.processRecipientList(Message.RecipientType.TO, p_to, p_to_name);
    }
    
    /**
     * Set who is CC'ed on the email message. 

     * The to email address may be a comma separated list of email addresses who will be CC'ed on the email.
     *
     * @param p_cc The email address of who the message is to. e.g. "jdoe@test.com"
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */      
    public void setCC(String p_cc) throws MessagingException, UnsupportedEncodingException {
        this.setCC(p_cc, null);
    }
    
    /**
     * Set who is CC'ed on the email message. 

     * The to email address may be a comma separated list of email addresses who will be CC'ed on the email.
     * If the message is sent to multiple recipients, then the name parameter of who to send to is ignored, and the
     * displayed recipient name is the recipient email address.
     *
     * @param p_cc The email address of who the message is to. e.g. "jdoe@test.com"
     * @param p_cc_name The textual name of who the message is to. e.g. "John Doe". This parameter is ignored if the to email address
     *                  is a comma separated list of multiple email recipients.
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */      
    public void setCC(String p_cc, String p_cc_name) throws MessagingException, UnsupportedEncodingException {
        this.processRecipientList(Message.RecipientType.CC, p_cc, p_cc_name);
    }

    /**
     * Set who is BCC'ed on the email message. 

     * The to email address may be a comma separated list of email addresses who will be BCC'ed on the email.
     *
     * @param p_bcc The email address of who the message is to. e.g. "jdoe@test.com"
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */      
    public void setBCC(String p_bcc) throws MessagingException, UnsupportedEncodingException {
        this.setBCC(p_bcc, null);
    }
    
    /**
     * Set who is BCC'ed on the email message. 

     * The to email address may be a comma separated list of email addresses who will be BCC'ed on the email.
     * If the message is sent to multiple recipients, then the name parameter of who to send to is ignored, and the
     * displayed recipient name is the recipient email address.
     *
     * @param p_bcc The email address of who the message is to. e.g. "jdoe@test.com"
     * @param p_bcc_name The textual name of who the message is to. e.g. "John Doe". This parameter is ignored if the to email address
     *                  is a comma separated list of multiple email recipients.
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */      
    public void setBCC(String p_bcc, String p_bcc_name) throws MessagingException, UnsupportedEncodingException {
        this.processRecipientList(Message.RecipientType.BCC, p_bcc, p_bcc_name);
    }
    
    /**
     * Process a recipient list (To, CC, BCC) and setup the appropriate internet addresses to send to. 
     * This method will parse a list of recipients which may be separated by commas.
     *
     * @param recipientType The type of recipient the recipient list is being parsed for and sent to.
     * @param p_to The email address of who the message is to. e.g. "jdoe@test.com"
     * @param p_to_name The textual name of who the message is to. e.g. "John Doe". This parameter is ignored if the to email address
     *                  is a comma separated list of multiple email recipients.
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */       
    private void processRecipientList (Message.RecipientType recipientType, String p_to, String p_to_name) throws MessagingException, UnsupportedEncodingException {
        //
        //If the to message address has a comma in it, then it must be a comma separated list of email recipients
        //
        StringTokenizer st = new StringTokenizer(p_to, ",");
        int tokenCount = st.countTokens();
        InternetAddress[] recipientList = new InternetAddress[tokenCount];
        
        //
        //Tokenize the recipient list, and create the Internet Address Array of Recipients
        //
        for (int i = 0; st.hasMoreTokens(); i++) {
            //Get the next token
            String msgTo = st.nextToken();

            //Ensure the token received is a valid address
            if (msgTo != null && msgTo.trim().length() > 0) {
                //If we only have one email address then we can display the to name
                if (tokenCount == 1 && p_to_name != null && p_to_name.length() > 0) {
                    recipientList[i] = new InternetAddress(msgTo, p_to_name);
                }
                //Otherwise just display the email address as the to name.
                else {
                    recipientList[i] = new InternetAddress(msgTo);
                }
            }
        }
        
        msg.setRecipients(recipientType, recipientList);        
    }
    

    /**
     * Set who the email message is from.
     *
     * @param p_from The email address of who the message is from. e.g. "jdoe@test.com"
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */       
    public void setFrom(String p_from) throws MessagingException, UnsupportedEncodingException {
        this.setFrom(p_from, null);
    }
    
    /**
     * Set who the email message is from.
     *
     * @param p_from The email address of who the message is from. e.g. "jdoe@test.com"
     * @param p_from_name The textual name of who the message is from. e.g. "John Doe"
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */       
    public void setFrom(String p_from, String p_from_name) throws MessagingException, UnsupportedEncodingException {
        String msg_from = p_from;
        String msg_from_name = p_from_name;
        
        if (p_from_name != null && p_from_name.length() > 0) {
            msg.setFrom(new InternetAddress(msg_from, msg_from_name));
        } 
        else {
            msg.setFrom(new InternetAddress(msg_from));
        }
    }
    
    /**
     * Set the reply to address for the email message.
     *
     * @param p_reply_to The email address for the reply to of the message. e.g. "jdoe@test.com"
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */  
    public void setReplyTo(String p_reply_to) throws MessagingException, UnsupportedEncodingException {
        this.setReplyTo(p_reply_to, null);
    }
    
    /**
     * Set the reply to address for the email message.
     *
     * @param p_reply_to The email address for the reply to of the message. e.g. "jdoe@test.com"
     * @param p_reply_to_name The textual name for the reply to of the message. e.g. "John Doe"
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */  
    public void setReplyTo(String p_reply_to, String p_reply_to_name) throws MessagingException, UnsupportedEncodingException {
        String msg_reply_to = p_reply_to;
        String msg_reply_to_name = p_reply_to_name;

        if (msg_reply_to_name != null && msg_reply_to_name.length() > 0) {
            InternetAddress[] address = {new InternetAddress(msg_reply_to, msg_reply_to_name)};
            msg.setReplyTo(address);
        }
        else {
            InternetAddress[] address = {new InternetAddress(msg_reply_to)};
            msg.setReplyTo(address);
        }
    }
    
    /**
     * Set the subject of the email message.
     *
     * @param p_subject The subject of the email message.
     * @throws MessagingException If an error occurs.
     *
     */ 
    public void setSubject(String p_subject) throws MessagingException {
        msg.setSubject(p_subject);
    }
    
    /**
     * Set the body of the email message.
     *
     * @param p_body The body of the email message.
     * @throws MessagingException If an error occurs.
     *
     */ 
    public void setBody(String p_body) throws MessagingException {
        MimeBodyPart mbp_msgtext = new MimeBodyPart();
        
        mbp_msgtext.setText(p_body);
        mp.addBodyPart(mbp_msgtext);
        
        //getting rid of the multipart for now
        htmlText  = p_body;
    }
    
    /**
     * Set a header tag in the email message. Replaces all existing header values 
     * with the same name with this new value. Note that RFC 822 headers must 
     * contain only US-ASCII characters, so a header that contains non 
     * US-ASCII characters must have been encoded by the caller as per 
     * the rules of RFC 2047.
     *
     * @param p_name The name of the header tag.
     * @param p_value The value of the header tag.
     * @throws MessagingException If an error occurs.
     *
     */    
    public void setHeader(String p_name, String p_value) throws MessagingException {
        msg.setHeader(p_name, p_value);
    }
    


    /**
     * Set the priority of a message. 

     * 1 or 2 are high priority. 

     * 3 is normal priority. 

     * 4 or 5 are low priority. 

     * Most mail readers don't differentiate 1-2 and 4-5.

     *
     * @param priorityValue The priority code of the message.
     * @throws Exception  If an error occurs.
     *
     */    
    public void setPriority(String priorityValue) throws Exception {
        //if (priorityValue < 1 || priorityValue > 5) {
        //    throw new Exception ("An invalid priority value of " + priorityValue + " has been specified for the email.");
        //}
        if (priorityValue != null && priorityValue.trim().length() > 0) {
            msg.setHeader("X-Priority", priorityValue);
        }
    }
    
    /**
     * Set the value stored in the Message-ID header tag for the message.
     * @param p_value The value of the Message-ID header tag.
     * @throws MessagingException If an error occurs.
     *
     */         
    public void setMessageID(String p_value) throws MessagingException {
        msg.setMessageID(p_value);
    }

    /**
     * Take an original MimeMessage object and forward it to a new destination.
     *
     * @param p_msgFrom Email address of who the message is from. e.g. "jdoe@test.com"
     * @param p_msgTo Email address of who the message is to. e.g. "jdoe@test.com"
     * @param p_msgSubject Subject for the forwarded email message.
     * @param p_msgBody Body of the forwarded email message.
     * @param msgOrig The original MimeMessage that is being forwarded.
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */      
    public void forwardMessage(String p_msgFrom, 
                               String p_msgTo, 
                               String p_msgSubject, 
                               String p_msgBody, 
                               MimeMessage msgOrig) throws MessagingException, UnsupportedEncodingException {
        forwardMessage(p_msgFrom, null, p_msgTo, null, p_msgSubject, p_msgBody, msgOrig);
    }
    
    /**
     * Take an original MimeMessage object and forward it to a new destination.
     *
     * @param p_msgFrom Email address of who the message is from. e.g. "jdoe@test.com"
     * @param p_msgFromText The textual name of who the message is from. e.g. "John Doe"
     * @param p_msgTo Email address of who the message is to. e.g. "jdoe@test.com"
     * @param p_msgToText The textual name of who the message is to. e.g. "John Doe"
     * @param p_msgSubject Subject for the forwarded email message.
     * @param p_msgBody Body of the forwarded email message.
     * @param msgOrig The original MimeMessage that is being forwarded.
     * @throws MessagingException If an error occurs.
     * @throws UnsupportedEncodingException If an error occurs.
     *
     */         
    public void forwardMessage(String p_msgFrom, 
                               String p_msgFromText, 
                               String p_msgTo, 
                               String p_msgToText, 
                               String p_msgSubject, 
                               String p_msgBody, 
                               MimeMessage msgOrig) throws MessagingException, UnsupportedEncodingException {
        this.setFrom(p_msgFrom, p_msgFromText);
        this.setTo(p_msgTo, p_msgToText);
        this.setSubject(p_msgSubject);
        this.setBody(p_msgBody);
        
        MimeBodyPart part = new MimeBodyPart();
        part.setDisposition(Part.ATTACHMENT);
        part.setFileName(msgOrig.getSubject());
        part.setContent(msgOrig, "message/rfc822");
        
        mp.addBodyPart(part);
    }
    
    /**
     * Add a file attachment to the email message.
     *
     * @param p_fileName The fully qualified name of the file to add to the message.
     * @throws MessagingException If an error occurs.
     */          
    public void addFileAttachment(String p_fileName) throws MessagingException {
        MimeBodyPart mbp_file = new MimeBodyPart();
        
        //
        //Attach the file to the message
        //
        FileDataSource fds = new FileDataSource(p_fileName);
        mbp_file.setDataHandler(new DataHandler(fds));
        mbp_file.setFileName(fds.getName());
        
        mp.addBodyPart(mbp_file);        
    }   

    /**
     * Add a file attachment to the email message from an input stream.
     *
     * @param p_fileName The filename for the attachment displayed in the the message.
     * @param istrm The input stream containing the file attachment to add to the file.
     * @param stream_len The length of the data in the input stream in bytes.
     * @throws MessagingException If an error occurs.
     * @throws IOException If an error occurs.
     */          
    public void addFileAttachmentFromStream(String p_fileName, 
                                            InputStream istrm, 
                                            int stream_len) throws MessagingException, IOException {
        
        String logMsg = "";
        byte b[] = new byte [stream_len];
        byte enc_b[] = new byte [stream_len*3];
        String encoded_str = new String("");
        
        BufferedInputStream bistrm = new BufferedInputStream(istrm);
        int bytes_read = 0;
        bytes_read = bistrm.read(b, 0, stream_len);
        
        logMsg = "Bytes Read From Stream: "+Integer.toString(bytes_read);
        log.debug(logMsg);
        
        /*
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Base64Encoder b64 = new Base64Encoder(new ByteArrayInputStream(b), bos);
        b64.process();
        enc_b = bos.toByteArray();
        */
        enc_b = Base64.encodeBase64(b);
        
        logMsg = "Encoded Byte Count: "+Integer.toString(enc_b.length);
        log.debug(logMsg);
        
        InternetHeaders hdr = new InternetHeaders();
        hdr.addHeader("Content-Type", "application/octet-stream; name=\""+p_fileName+"\"");
        hdr.addHeader("Content-Transfer-Encoding", "base64");
        hdr.addHeader("Content-Disposition", "inline; filename=\""+p_fileName+"\"");
        
        MimeBodyPart mbp_file = new MimeBodyPart(hdr, enc_b);
        mp.addBodyPart(mbp_file);
        
    }
    
    /**
     * Send the email message.
     *
     * @throws MessagingException If an error occurs.
     */   
    public void sendMsg() throws MessagingException {
        
        //
        //add the Multipart to the message
        //
        //msg.setContent(mp);
    	msg.setContent(htmlText, "text/html");
        
        //
        // set the Date: header
        //
    	if (emailDate == null) {
    		msg.setSentDate(new Date());
    	} else {
    		msg.setSentDate(emailDate);
    	}
        
        msg.saveChanges();
        
        //TEA 05/13/03, New method for sending so that we 
        //can support SMTP authentication
        if (usingAuthentication) {
            Transport transport = session.getTransport("smtp");
            transport.connect (smtp_host, smtp_username, smtp_password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();            
        }
        else {
            Transport.send(msg);
        }            
        
    }
    
    /**
     * Main unit test method.
     * @param args Command line arguments.
     *
     */
    public static void main(String args[]) {    
        try {
            log.debug("Preparing to send email.");

            //
            //Prepare Our Mail Message
            //
            EmailDelivery ed = new EmailDelivery();

            //Update the to/cc/bcc/from email addresseses. Note that the to, cc, and bcc
            //methods can take a command separated list.
            ed.setTo("branflake2267@gmail.com");
            ed.setCC("tima@test.com");
            ed.setBCC("tima@anotheremailaddress.com");
            ed.setFrom("somebody@test.com", "Joe Smith");
            ed.setReplyTo("replytome@test.com", "Reply To Me");
            
            ed.setSubject("Test Message Subject");
            ed.setBody("Test message body.");
            //Add the file attachment. Make sure this file exists for your testing.
            ed.addFileAttachment("c:\\temp\\attachment.txt");
            
            //Set the SMTP host to use to relay the message, and if it requires
            //smtp authentication then also specify the username and password.
            ed.setSMTPHost("192.168.1.50", "tima", "mySMTPpassword");
            
            ed.sendMsg();
            
            log.info("Test message sent.");
        } catch (Exception e) {
            log.error("Error:"+e.toString(), e);
        }
    }
    
    private void setDate(Date date) {
    	this.emailDate  = date;
    }
}
