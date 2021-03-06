package hu.ebanjo.ledshop.dbs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)    
    Long id;

    String firstName;
    String lastName;
    String email   ;
    String company ;
    String taxId   ;
    String country ;
    String street1 ;
    String street2 ;
    String city    ;
    String postcode;
    String phone   ;

    /*
    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    */
    Long customerId;
}
