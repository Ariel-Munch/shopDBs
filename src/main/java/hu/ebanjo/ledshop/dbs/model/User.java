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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String firstname;
    String lastname;
    String username;
    
    @Column(unique=true)
    String email;
    String password;

    boolean active;
    boolean blocked;

    LocalDateTime modifiedAt ;
    LocalDateTime createdAt ;

    String resetPasswordToken;
    String registrationToken;

}
