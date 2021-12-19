package hu.ebanjo.ledshop.dbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PropertyIntl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    
    @ManyToOne
    Lang lang;

    @ManyToOne
    Product product;

    @ManyToOne
    CodeValue propertyType;

    @Column(columnDefinition="TEXT")
    String propertyValue;

    Integer ord;
    LocalDateTime createdAt ;
}
