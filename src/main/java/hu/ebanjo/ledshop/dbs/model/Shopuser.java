package hu.ebanjo.ledshop.dbs.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Shopuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @Column(unique=true)
    String email;
    String password;
    @Column(unique=true)
    String username;

    String firstname;
    String lastname;
    
    boolean active;
    boolean blocked;

    LocalDateTime modifiedAt ;
    LocalDateTime createdAt ;

    String resetPasswordToken;
    String registrationToken;

}
