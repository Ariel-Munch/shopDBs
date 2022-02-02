package hu.ebanjo.ledshop.dbs.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//import lombok.Builder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    String username;
    String email;
    String password;

    boolean isActive;
    boolean blocked;

    LocalDateTime modifiedAt ;
    LocalDateTime createdAt ;

    String resetPasswordToken;
    String registrationToken;


}
