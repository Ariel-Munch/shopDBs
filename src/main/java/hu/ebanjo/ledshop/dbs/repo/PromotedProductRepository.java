package hu.ebanjo.ledshop.dbs.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import hu.ebanjo.ledshop.dbs.model.PromotedProduct;

@Repository
public interface PromotedProductRepository extends JpaRepository<PromotedProduct, Long> {
    
}