package hu.ebanjo.ledshop.dbs.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import hu.ebanjo.ledshop.dbs.control.MailDTO;
import hu.ebanjo.ledshop.dbs.model.MailTemplate;
import hu.ebanjo.ledshop.dbs.model.Shopuser;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;

    void sendPrepedRegMail(Shopuser cus, MailTemplate tpl, String txt);
    void sendHtmlMail(Shopuser cus, MailTemplate tpl, String txt) throws MessagingException, UnsupportedEncodingException;
   
    void sendEmailWithAttachment() throws MessagingException, IOException ;
    void sendEmail(MailDTO dto) ;
}

