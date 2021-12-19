package hu.ebanjo.ledshop.dbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;
import lombok.Builder;

@Data @Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    String name;
    String password;

    Integer status;

    @OneToMany
    Set<Customer> shopCustomer;
    
    LocalDateTime modifiedAt ;
    LocalDateTime createdAt ;

}
