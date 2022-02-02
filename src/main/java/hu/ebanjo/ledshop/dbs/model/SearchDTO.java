package hu.ebanjo.ledshop.dbs.model;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class SearchDTO {
    String name;
    Long[] categories;
    PropertyFilter[] properties;
    PropertyTextFilter[] textProperties;
    BigDecimal priceMin;
    BigDecimal priceMax;
    
}
