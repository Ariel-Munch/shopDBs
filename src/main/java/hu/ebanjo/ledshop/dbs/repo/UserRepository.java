package hu.ebanjo.ledshop.dbs.repo;

import hu.ebanjo.ledshop.dbs.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmailAndPassword(String email, String password ) ;
}