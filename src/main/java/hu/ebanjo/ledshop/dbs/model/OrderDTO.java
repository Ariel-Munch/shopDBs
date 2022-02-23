package hu.ebanjo.ledshop.dbs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends Order {

    Long shop_id;
    Boolean checkoutCreateAcc;
}
