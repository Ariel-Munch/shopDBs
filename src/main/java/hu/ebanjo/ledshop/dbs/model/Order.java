package hu.ebanjo.ledshop.dbs.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    Long id;
    String name;
    String articleNumber;
}