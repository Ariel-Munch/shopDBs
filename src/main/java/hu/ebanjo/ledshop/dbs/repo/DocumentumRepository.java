package hu.ebanjo.ledshop.dbs.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import hu.ebanjo.ledshop.dbs.model.Documentum;

@Repository
public interface DocumentumRepository extends JpaRepository<Documentum, Long> {
    
}