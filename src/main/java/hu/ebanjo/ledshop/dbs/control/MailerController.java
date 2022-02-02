package hu.ebanjo.ledshop.dbs.control;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pix")
public class MailerController {
    public MailerController() {}

    
        @GetMapping
        public List<String> getPictures() {
            return new java.util.ArrayList<>();
        }
}
