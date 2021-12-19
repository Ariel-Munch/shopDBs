package hu.ebanjo.ledshop.dbs.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import hu.ebanjo.ledshop.dbs.model.CodeType;

@Repository
public interface CodeTypeRepository extends JpaRepository<CodeType, Long> {
    
}