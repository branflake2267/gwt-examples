package com.tribling.gwt.test.feedback.server.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * Extended MIME Message class with method to set the message ID 
 * header tag, and the updateHeaders method which ensures the 
 * Message ID gets set in the message.
 *
 * @author  Tim Archer 09/30/2003
 * @version $Revision: 1.1 $
 */
class EmailMimeMessage extends MimeMessage {
    static Logger log = Logger.getLogger(EmailMimeMessage.class);
    
    /** The value stored in the Message-ID header tag for the message.*/
    protected String messageID = "";
    
    /**
     * Create a new EmailMimeMessage object.
     * @param session The javax.mail.Session object the mail message is for.
     *
     */        
    public EmailMimeMessage(javax.mail.Session session) {
        super(session);
    }
    
    /**
     * Set the value stored in the Message-ID header tag for the message.
     * @param p_value The value of the Message-ID header tag.
     *
     */        
    void setMessageID(String p_value) {
        messageID = p_value;
    }
    
    /**
     * Calls the super.updateHeaders() method, and also ensures 
     * that the Message-ID header tag
     * gets set if the setMessageID method was called.
     *
     * @throws MessagingException If an error occurs.
     */        
    protected void updateHeaders() throws MessagingException {
        super.updateHeaders();
        if (messageID != null && messageID.length() > 0) {
            setHeader("Message-ID", messageID);
        }
    }
}

