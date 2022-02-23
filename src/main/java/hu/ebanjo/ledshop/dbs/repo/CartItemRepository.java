package hu.ebanjo.ledshop.dbs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.ebanjo.ledshop.dbs.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}