package hu.ebanjo.ledshop.dbs.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// import hu.ebanjo.ledshop.dbs.model.Category;
import hu.ebanjo.ledshop.dbs.model.Product;
import hu.ebanjo.ledshop.dbs.model.SearchDTO;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    /*
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
    + " OR p.brand LIKE %?1%"
    + " OR p.madein LIKE %?1%"
    + " OR CONCAT(p.price, '') LIKE %?1%")    
    List<Product> filter(SearchDTO[] filter);*/

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 ")
    Optional<Product[]> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND p.name LIKE %?2%")
    Optional<Product[]> findByCategoryIdAndName(Long categoryId, String name);

    
    
}