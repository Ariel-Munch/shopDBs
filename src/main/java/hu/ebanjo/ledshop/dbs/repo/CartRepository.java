package hu.ebanjo.ledshop.dbs.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hu.ebanjo.ledshop.dbs.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Transactional
    @Modifying
    @Query("delete from Cart c where c.customer.id = :customerId")
    void deleteByCustomerId(@Param("customerId") Long id);

    List<Cart> findByCustomer(Long id);

    Optional<Cart> findByCustomerIdAndItemId(Long customerId, Long pid);

    List<Cart> findByCustomerId(Long id);
    
}