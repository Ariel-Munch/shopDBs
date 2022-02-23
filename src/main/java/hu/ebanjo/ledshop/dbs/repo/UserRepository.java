package hu.ebanjo.ledshop.dbs.repo;

import hu.ebanjo.ledshop.dbs.model.Shopuser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<Shopuser, Long> {
    List<Shopuser> findByEmail(String email) ;
    List<Shopuser> findByEmailAndPassword(String email, String password ) ;
}