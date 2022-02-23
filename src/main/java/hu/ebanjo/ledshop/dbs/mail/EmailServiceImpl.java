package hu.ebanjo.ledshop.dbs.mail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import hu.ebanjo.ledshop.dbs.control.MailDTO;
import hu.ebanjo.ledshop.dbs.mail.EmailService;
import hu.ebanjo.ledshop.dbs.model.Shopuser;
import hu.ebanjo.ledshop.dbs.model.MailTemplate;
import hu.ebanjo.ledshop.dbs.repo.MailTemplateRepository;
import hu.ebanjo.ledshop.dbs.repo.UserRepository;

@Component
public class EmailServiceImpl implements EmailService {

  @Value("${shopmailer.from:noreply@ledsound.hu}") String REGEMAIL_FROMADDR ;
  @Value("${shopmailer.regemail.fromname:LEDSOUND WEBSHOP mailer agent}")  private String REGEMAIL_FROMNAME;
  @Value("${shopmailer.regemail.regsrvurl}") String REGEMAIL_REGSRVURL ;
  @Value("${shopmailer.regemail.shopsiteurl}") String REGEMAIL_SHOPSITEURL ;
  @Value("${shopmailer.regemail.subject}")  private String REGEMAIL_SUBJECT;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {
        // ...
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@ledsound.hu");
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

    public void sendEmail(MailDTO dto) {

      SimpleMailMessage msg = new SimpleMailMessage();
      msg.setTo(dto.getTo());
      msg.setFrom(REGEMAIL_FROMADDR);

      msg.setSubject(dto.getSubject());
      msg.setText(dto.getText());

      emailSender.send(msg);

    }

    public void sendEmailWithAttachment() throws MessagingException, IOException {

      MimeMessage msg = emailSender.createMimeMessage();

      // true = multipart message
      MimeMessageHelper helper = new MimeMessageHelper(msg, true);
      helper.setTo("1@gmail.com");

      helper.setSubject(REGEMAIL_SUBJECT);

      // default = text/plain
      //helper.setText("Check attachment for image!");

      // true = text/html
      helper.setText("<h1>Check attachment for image!</h1>", true);

      helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

      emailSender.send(msg);

    }    

    @Override
    public void sendPrepedRegMail(Shopuser cus, MailTemplate tpl, String txt)
    {
      String mail_to   = cus.getEmail();
      String mail_subj = tpl.getSubject();
      String mail_text = tpl.getText();
      // Replace ShopUserEmail
      String body = mkMailModel(cus, mail_text, txt);
      this.sendSimpleMessage(mail_to, mail_subj, body);
   }

   @Override
   public void sendHtmlMail(Shopuser cus, MailTemplate tpl, String txt) throws MessagingException, UnsupportedEncodingException {
      String mail_to   = cus.getEmail();
      String mail_subj = tpl.getSubject();
      String mail_text = tpl.getText();
       //

     MimeMessage mail = emailSender.createMimeMessage();
     String body = mkMailModel(cus, mail_text, txt);
     MimeMessageHelper helper = new MimeMessageHelper(mail, true);

      helper.setFrom(REGEMAIL_FROMADDR, REGEMAIL_FROMNAME) ;
     helper.setTo(mail_to);
     helper.setSubject(mail_subj);
     helper.setText(body, true);
     emailSender.send(mail);
   }

   private String mkMailModel(Shopuser cus, String mail_text, String otherText) {
    return mail_text.replace("[[ShopUserEmail]]", cus.getEmail()  )
      .replace("[[ShopUserId]]", Long.toString( cus.getId()) )
      .replace("[[ShopServerUrl]]", REGEMAIL_REGSRVURL )
      .replace("[[ShopSiteUrl]]", REGEMAIL_SHOPSITEURL )
      .replace("[[ShopUserFirst]]", cus.getFirstname() == null ? "": cus.getFirstname() )
      .replace("[[ShopUserLast]]", cus.getLastname() == null ? "" : cus.getLastname())
      .replace("[[ShopReggToken]]", cus.getRegistrationToken() == null ? "" : cus.getRegistrationToken()  )
      .replace("[[ShopAddText]]", otherText )
      .replace("[[ShopUserName]]", StringUtils.hasText(cus.getUsername()) ? cus.getUsername() : "Customer" )
    ;
}

}