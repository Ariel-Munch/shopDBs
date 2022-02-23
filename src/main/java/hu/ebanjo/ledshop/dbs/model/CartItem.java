package hu.ebanjo.ledshop.dbs.model;

import java.math.BigDecimal;
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
//import javax.persistence.JoinColumn;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)    
    public Long id;
    public String molla_product_name;

    LocalDateTime until ;
    LocalDateTime createdAt ;

    Long molla_product_id;
    
    Integer picture_width;
    Integer picture_height;
    String picture_url;

    BigDecimal qty;
    BigDecimal price;
    BigDecimal sum;
}
