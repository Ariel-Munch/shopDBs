package hu.ebanjo.ledshop.dbs.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//import javax.persistence.JoinColumn;





@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)    
    public Long id;
    public String name;
    String articleNumber;
    
    @ManyToOne
    Category category ;

    
    @OneToMany (cascade = CascadeType.ALL,orphanRemoval = true)
    Set<ProductPicture> pictures ;

    @OneToMany (cascade = CascadeType.ALL,orphanRemoval = true)
    Set<ProductPicture> smPictures ;
    
    @OneToMany (cascade = CascadeType.ALL,orphanRemoval = true)
    Set<Property> properties ;
    
    @OneToMany (cascade = CascadeType.ALL,orphanRemoval = true)
    Set<PropertyIntl> textProperties ;

    BigDecimal price;
    LocalDateTime createdAt ;
///
    String  slug;
    String  short_desc;
    BigDecimal sale_price;
    String  vendor;
    Integer review;
    Integer ratings;

    LocalDateTime until ;
    @ManyToOne
    Shopuser createdBy;
    @ManyToOne
    Shopuser modifiedBy;
    LocalDateTime modifiedAt ;

    @OneToMany (cascade = CascadeType.ALL,orphanRemoval = true)
    Set<Product> variants;
    
    @OneToMany (cascade = CascadeType.ALL,orphanRemoval = true)
    Set<Brand>   brands;
    
}
