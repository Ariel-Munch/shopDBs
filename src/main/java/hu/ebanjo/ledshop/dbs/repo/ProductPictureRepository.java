package hu.ebanjo.ledshop.dbs.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import hu.ebanjo.ledshop.dbs.model.ProductPicture;

@Repository
public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {
    
}