package hu.ebanjo.ledshop.dbs.control;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.ebanjo.ledshop.dbs.model.User;
import hu.ebanjo.ledshop.dbs.repo.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    
        private final UserRepository userRepository;
    
        public UserController(UserRepository userRepository) {
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
    
        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
            User savedUser = userRepository.save(user);
            return ResponseEntity.created(new URI("/users/" + Long.toString( savedUser.getId() ))).body(savedUser);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
            User currentUser = userRepository.findById(id).orElseThrow(RuntimeException::new);
            currentUser.builder()
                .name(user.getName())
                .build();
            // currentUser.setName( user.getName() );
            // currentUser.setUrlDbApi( user.getUrlDbApi()  );
            currentUser = userRepository.save(user);
    
            return ResponseEntity.ok(currentUser);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<User> deleteUser(@PathVariable Long id) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
