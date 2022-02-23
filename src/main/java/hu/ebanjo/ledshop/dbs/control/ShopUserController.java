package hu.ebanjo.ledshop.dbs.control;

import hu.ebanjo.ledshop.dbs.mail.EmailService;
import hu.ebanjo.ledshop.dbs.model.Shopuser;
import hu.ebanjo.ledshop.dbs.repo.MailTemplateRepository;
import hu.ebanjo.ledshop.dbs.repo.UserRepository;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.ebanjo.ledshop.dbs.model.MailTemplate;
import hu.ebanjo.ledshop.dbs.model.StatusDTO;


@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/users")
public class ShopUserController {
    
    @Value("${shopmailer.regemail.templatecode}")
    private String REGEMAIL_TPLCODE;
    @Value("${shopmailer.lostpass.templatecode}")
    private String REGEMAIL_LOSTPASS;
    @Value("${shopmailer.lang.fallback:ENG}")
    private String LANGNAME_FALLBACK;

    private final MailTemplateRepository mailTemplateRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    
        public ShopUserController(UserRepository userRepository,EmailService es, MailTemplateRepository ms) {
            this.userRepository = userRepository;
            this.emailService = es;
            this.mailTemplateRepository = ms;
        }

        @GetMapping
        public List<Shopuser> getUsers() {
            return userRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Shopuser getUser(@PathVariable Long id) {
            return userRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @GetMapping("/hasemail")
        public ResponseEntity<StatusDTO> hasEmailgetUser(@RequestParam("m") String email) {
             List<Shopuser> cus = userRepository.findByEmail(email);
             return ResponseEntity.ok( StatusDTO.builder().code(200L).status( cus.size()>0 ? "exists" : null ). build()) ;
        }
    
        @GetMapping("/lostpass/{id}")
        public ResponseEntity<StatusDTO> lostPassgetUser( @PathVariable("id") String langName, @RequestParam("m") String email) {
            List<Shopuser> cus = userRepository.findByEmail(email);
            String token = null;
            if (cus.size() > 0) {
                Shopuser user = cus.get(0);
                if ( ! StringUtils.hasText(user.getUsername()) ) user.setUsername(null);
                user.setRegistrationToken( (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-","")  );
                user.setModifiedAt(LocalDateTime.now());
                Shopuser savedUser = userRepository.save(user);
                //
                // List<MailTemplate> mailTpl = this.mailTemplateRepository.findByCodeNameAndLangId(REGEMAIL_LOSTPASS, langId);
                List<MailTemplate> mailTpl = this.mailTemplateRepository.getMailTpl(REGEMAIL_LOSTPASS, langName, LANGNAME_FALLBACK);
                if ( mailTpl.size() > 0 ) {
                    try {
                        this.emailService.sendHtmlMail(user, mailTpl.get(0), ""  );
                        return ResponseEntity.ok( StatusDTO.builder().code(200L).status("OK").msg(token). build()) ;
                    } catch (UnsupportedEncodingException | MessagingException e) {
                        e.printStackTrace();
                        return ResponseEntity.badRequest().header("X-ledsound-apierror", e.getMessage()).build();
                    }
                } else {
                    return ResponseEntity.badRequest()
                        .body( StatusDTO.builder().code(477L).status("Email template not found with lang:"+langName).msg(token). build()) ;
                }
            }
            return ResponseEntity.notFound().build();
        }

        @PostMapping("/repass/{t}")
        public ResponseEntity<String> lostPassRecover( @PathVariable("t") String token,  @RequestBody Shopuser user ) {
             Shopuser cus = userRepository.findById(user.getId()).orElseThrow(RuntimeException::new);
             if ( cus.getModifiedAt().isBefore( LocalDateTime.now().plusHours(48)) && cus.getRegistrationToken() != null) {
                if ( token.equals( cus.getRegistrationToken())) {
                    cus.setPassword( user.getPassword()  );
                    cus.setRegistrationToken(null);
                    cus.setModifiedAt(LocalDateTime.now());
                    userRepository.save(cus);
                    return ResponseEntity.ok(token);
                }
             }
             return ResponseEntity.notFound().build();
        }

        @PostMapping()
        public ResponseEntity<Shopuser> createUser(@RequestBody Shopuser user) throws URISyntaxException {
            Shopuser savedUser = userRepository.save(user);
            return ResponseEntity.created(new URI("/users/" + Long.toString( savedUser.getId() ))).body(savedUser);
        }

        @PostMapping("/register/{lang}")
        public ResponseEntity<Shopuser> register( @PathVariable("lang") String langName, @RequestBody Shopuser user) throws URISyntaxException {
            user.setRegistrationToken( (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-","")  );
            if ( ! StringUtils.hasText(user.getUsername()) ) user.setUsername(null);
            Shopuser savedUser = userRepository.save(user);
            // this.emailService.sendSimpleMessage(user.getEmail(), REGEMAIL_SUBJECT, null);
            // List<MailTemplate> mailTpl = this.mailTemplateRepository.findByCodeNameAndLangId(REGEMAIL_TPLCODE, langId);
            List<MailTemplate> mailTpl = this.mailTemplateRepository.getMailTpl(REGEMAIL_LOSTPASS, langName, LANGNAME_FALLBACK);
            if ( mailTpl.size() > 0 ) {
                // this.emailService.sendPrepedRegMail(user, mailTpl.get(0), ""  );
                try {
                    this.emailService.sendHtmlMail(user, mailTpl.get(0), ""  );
                } catch (UnsupportedEncodingException | MessagingException e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().header("X-ledsound-apierror", e.getMessage()).build();
                }
            } else {
                System.out.println("Mail template NOT FOUND and Regg MAIL Missed!!!!");
            }

            return ResponseEntity.created(new URI("/users/" + Long.toString( savedUser.getId() ))).body(savedUser);
        }
        // SignIn / SignUp
        @PostMapping("/login")
        public ResponseEntity<Shopuser> signInUser(@RequestBody Shopuser user) throws URISyntaxException {

            List<Shopuser> cus = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword() );
            if ( cus != null && cus.size() == 1 ) {
                return ResponseEntity.ok( cus.get(0) );
            }
            throw  new RuntimeException("credential not found for:" + user.getEmail()) ;
        }
    
        // SignIn / SignUp
        @PostMapping("/details/{id}")
        public ResponseEntity<Shopuser> signInUser( @PathVariable("id") Long userId,  @RequestBody Shopuser u) throws URISyntaxException {

            Shopuser cus = userRepository.findById(userId).orElseThrow(RuntimeException::new);
            String password = cus.getPassword(); // OLD
            if ( StringUtils.hasText( u.getPassword() ) && StringUtils.hasText( u.getResetPasswordToken()  ) 
            ) {
                if (!(u.getPassword().equals(password))) {
                    throw new  RuntimeException( "Old password nor matched!") ;
                }
                password = u.getResetPasswordToken() ; // NEW Psw holder !!!
            }
            Shopuser updater = Shopuser.builder()
            .id(userId)
            . username(u.getUsername())
            . email(u.getEmail())
            . password( password )
            . active(cus.isActive())
            . blocked(cus.isBlocked())
            . modifiedAt( LocalDateTime.now() )
            . createdAt(cus.getCreatedAt())
            . resetPasswordToken(cus.getResetPasswordToken())
            . registrationToken(cus.getRegistrationToken())
            .build();
            Shopuser updatedUser = userRepository.save(updater);
            return ResponseEntity.ok( updatedUser );
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Shopuser> updateUser(@PathVariable Long id, @RequestBody Shopuser user) {
            Shopuser currentUser = userRepository.findById(id).orElseThrow(RuntimeException::new);

            currentUser.setUsername( user.getUsername() );
            // currentUser.setShopCustomer( user.getShopCustomers() );

            currentUser = userRepository.save(user);
            return ResponseEntity.ok(currentUser);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Shopuser> deleteUser(@PathVariable Long id) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    

}
