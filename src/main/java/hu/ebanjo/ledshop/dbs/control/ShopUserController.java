package hu.ebanjo.ledshop.dbs.control;

import hu.ebanjo.ledshop.dbs.model.User;
import hu.ebanjo.ledshop.dbs.repo.UserRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    
        // SignIn / SignUp
        @PostMapping("/details/{id}")
        public ResponseEntity<User> signInUser( @PathVariable("id") Long userId,  @RequestBody User u) throws URISyntaxException {

            User cus = userRepository.findById(userId).orElseThrow(RuntimeException::new);
            String newPassword = cus.getPassword(); // OLD
            if ( ! StringUtils.isEmpty( u.getPassword() )) {
                if (!(u.getPassword().equals(newPassword))) {
                    throw new  RuntimeException( "Old password nor matched!") ;
                }
                newPassword = u.getResetPasswordToken() ; // NEW Psw holder !!!
            }
            User updater = User.builder()
            .id(userId)
            . firstname(u.getFirstname())
            . lastname(u.getLastname())
            . username(u.getUsername())
            . email(u.getEmail())
            
            . password( newPassword )

            . active(cus.isActive())
            . blocked(cus.isBlocked())
            . modifiedAt( LocalDateTime.now() )
            . createdAt(cus.getCreatedAt())
            . resetPasswordToken(cus.getResetPasswordToken())
            . registrationToken(cus.getRegistrationToken())
            .build();
            User updatedUser = userRepository.save(updater);
            return ResponseEntity.ok( updatedUser );
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
