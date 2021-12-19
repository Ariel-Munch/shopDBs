package hu.ebanjo.ledshop.dbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)    
    Long id;
    String name;
    String articleNumber;
    
    @ManyToOne
    Category category ;

    @OneToMany (
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "product")
    Set<ProductPicture> pictures ;

    @OneToMany
    Set<Property> properties ;
    
    @OneToMany
    Set<PropertyIntl> textProperties ;

    LocalDateTime createdAt ;
    
}
