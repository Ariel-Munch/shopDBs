package hu.ebanjo.ledshop.dbs.control;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDTO {
    String to;
    String subject;
    String text;
}
