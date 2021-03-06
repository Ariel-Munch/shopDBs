package hu.ebanjo.ledshop.dbs.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import hu.ebanjo.ledshop.dbs.mail.EmailService;
import hu.ebanjo.ledshop.dbs.model.Shopuser;
import hu.ebanjo.ledshop.dbs.model.MailTemplate;
import hu.ebanjo.ledshop.dbs.repo.MailTemplateRepository;
import hu.ebanjo.ledshop.dbs.repo.UserRepository;


@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/sendmail")
public class MailerController {

    @Autowired EmailService  mailer;

    @Autowired private UserRepository userRepo ; 
    @Autowired private MailTemplateRepository mailTplRepo ; 


    @GetMapping
    public ResponseEntity<List<MailTemplate> > getAllTpl() {
        List<MailTemplate> tpls = mailTplRepo.findAll();
        return ResponseEntity.ok(tpls);
    }

    @GetMapping("/lang/{lang}/code/{code}")
    public ResponseEntity<MailTemplate> getByCodeTpl(@PathVariable("lang")  Long langId, @PathVariable("code")  String tplCode ) {
        List<MailTemplate> tpls = mailTplRepo.findByCodeNameAndLangId(tplCode, langId);
        return tpls.size() > 0 ? ResponseEntity.ok(tpls.get(0)) : ResponseEntity.notFound().build() ;
    }

    @PutMapping("/tpl/{id}") 
    public ResponseEntity updateMailBody(@PathVariable("tpl") Long templateId, @RequestBody String txt) {
        MailTemplate tpl = mailTplRepo.findById(templateId).orElseThrow(RuntimeException::new) ;
        tpl.setText(txt);
        mailTplRepo.save(tpl);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/reg/{id}/{lang}/{code}")
    public ResponseEntity sendReggMailByLnag(
                  @PathVariable("id") Long userId
                , @PathVariable("lang") Long langId
                , @PathVariable("code") String tplCode
                , @RequestParam("sr") String reggServerUrl
    ) {
        List<MailTemplate> tpls = mailTplRepo.findByCodeNameAndLangId(tplCode, langId);
        if (tpls.size() > 0) {
            Shopuser cus = userRepo.findById(userId).orElseThrow(RuntimeException::new) ;

            String regToken = (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-", "") ;
    
            cus.setRegistrationToken(regToken);
            mailer.sendPrepedRegMail(cus, tpls.get(0), reggServerUrl);
    
            userRepo.save(cus);
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build() ;
        }
    }
    @GetMapping("/regg/{id}/{tpl}")
    public ResponseEntity sendReggMail(@PathVariable("id") Long userId,@PathVariable("tpl") Long templateId, @RequestParam("sr") String reggServerUrl ) {

        Shopuser cus = userRepo.findById(userId).orElseThrow(RuntimeException::new) ;
        MailTemplate tpl = mailTplRepo.findById(templateId).orElseThrow(RuntimeException::new) ;

        String regToken = (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-", "") ;

        cus.setRegistrationToken(regToken);
        mailer.sendPrepedRegMail(cus, tpl, reggServerUrl );

        userRepo.save(cus);

        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/regg")
    public ResponseEntity postReggMail( @RequestBody  MailDTO mail ) {

        mailer.sendEmail(mail);
        //sendEmailWithAttachment();

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
