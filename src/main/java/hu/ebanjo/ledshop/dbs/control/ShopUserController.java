package hu.ebanjo.ledshop.dbs.control;

import hu.ebanjo.ledshop.dbs.model.User;
import hu.ebanjo.ledshop.dbs.repo.UserRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/users")
public class ShopUserController {
    
        private final UserRepository userRepository;
    
        public ShopUserController(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @GetMapping
        public List<User> getUsers() {
            return userRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public User getUser(@PathVariable Long id) {
            return userRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    

        @PostMapping()
        public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
            User savedUser = userRepository.save(user);
            return ResponseEntity.created(new URI("/users/" + Long.toString( savedUser.getId() ))).body(savedUser);
        }

        @PostMapping("/register")
        public ResponseEntity<User> register(@RequestBody User user) throws URISyntaxException {
            User savedUser = userRepository.save(user);
            return ResponseEntity.created(new URI("/users/" + Long.toString( savedUser.getId() ))).body(savedUser);
        }
        // SignIn / SignUp
        @PostMapping("/login")
        public ResponseEntity<User> signInUser(@RequestBody User user) throws URISyntaxException {

            List<User> cus = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword() );
            if ( cus != null && cus.size() == 1 ) {
                return ResponseEntity.ok( cus.get(0) );
            }
            throw  new RuntimeException("credential not found for:" + user.getEmail()) ;
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
            User currentUser = userRepository.findById(id).orElseThrow(RuntimeException::new);

            currentUser.setUsername( user.getUsername() );
            // currentUser.setShopCustomer( user.getShopCustomers() );

            currentUser = userRepository.save(user);
            return ResponseEntity.ok(currentUser);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<User> deleteUser(@PathVariable Long id) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    

}
