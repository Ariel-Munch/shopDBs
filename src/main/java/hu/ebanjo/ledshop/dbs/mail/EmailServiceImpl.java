package hu.ebanjo.ledshop.dbs.mail;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {
        // ...
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@baeldung.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        //
        emailSender.send(message);
        // ...
    }
    
    public void sendMessageWithAttachment(
      String to, String subject, String text, String pathToAttachment) throws MessagingException {
        // ...
        
        MimeMessage message = emailSender.createMimeMessage();
        message.addHeader("X-Joe-ctxheader", "Valamirecsak jo(e) lesz...");

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom("noreply@ledsound.eu");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
            
        FileSystemResource file 
          = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);
    
        emailSender.send(message);
        // ...
    }    
}