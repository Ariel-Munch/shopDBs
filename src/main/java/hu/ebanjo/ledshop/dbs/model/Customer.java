package hu.ebanjo.ledshop.dbs.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name="shop_id", nullable = false)
    Shop shop;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    Shopuser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    Address billingAddress;


    @OneToOne(cascade = CascadeType.ALL,optional = true)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    @ManyToOne
    Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL,optional = true)
    @JoinColumn(name = "other_address_id", referencedColumnName = "id")
    @ManyToOne
    Address otherAddress;

    String phone;
    LocalDateTime createdAt ;
}
