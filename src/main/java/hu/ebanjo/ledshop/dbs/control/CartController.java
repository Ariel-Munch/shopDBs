package hu.ebanjo.ledshop.dbs.control;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

import hu.ebanjo.ledshop.dbs.model.Cart;
import hu.ebanjo.ledshop.dbs.model.Customer;
import hu.ebanjo.ledshop.dbs.model.Product;
import hu.ebanjo.ledshop.dbs.repo.CartRepository;
import hu.ebanjo.ledshop.dbs.repo.CustomerRepository;
import hu.ebanjo.ledshop.dbs.repo.ProductRepository;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/cart")
public class CartController {
    
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository ;

        public CartController(CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
            this.cartRepository = cartRepository;
            this.customerRepository = customerRepository;
            this.productRepository = productRepository ;
        }

        @GetMapping
        public List<Cart> getCart() {
            return cartRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Cart getCart(@PathVariable Long id) {
            return cartRepository.findById(id).orElseThrow(RuntimeException::new);
        }
        @GetMapping("/cust/{id}")
        public List<Cart> getCartByCustomer(@PathVariable Long id) {
            return cartRepository.findByCustomerId(id) ;
        }
        @GetMapping("/customer/{customerId}/item/{pid}/qty/{newQuantity}")
        public ResponseEntity<Cart> updItemQuantity(@PathVariable Long customerId, @PathVariable Long pid, @PathVariable Integer newQuantity )
        {
            Cart cart ;
            Optional<Cart> currentCartItem = cartRepository.findByCustomerIdAndItemId( customerId, pid) ;
            if (currentCartItem.isPresent()) {
                cart = currentCartItem.get();
                cart.setQuantity(newQuantity + cart.getQuantity());
                cart.setCreatedAt( LocalDateTime.now() )  ;
            } else {
                Customer cust = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer :" + customerId + ", doesn`t exists!"));
    System.out.println("Found Customer:" +  cust) ;
                Product prod = productRepository.findById(pid).orElseThrow(() -> new RuntimeException("Product :" + pid + ", doesn`t exists!"));
    System.out.println("Found Product:" +  prod) ;
                //
                cart = Cart.builder()
                //.id(null)
                .createdAt(LocalDateTime.now())
                .customer(cust)
                .item( prod )
                .quantity( newQuantity)
                .build();
            }
            Cart updatedCart = cartRepository.save(cart) ;
    System.out.println("updItemQuantity :" +  updatedCart) ;
            return ResponseEntity.ok(updatedCart);
        }
    
        @PostMapping
        public ResponseEntity<Cart> createCart(@RequestBody Cart cart) throws URISyntaxException {
            Cart savedCart = cartRepository.save(cart);
            return ResponseEntity.created(new URI("/cart/" + Long.toString( savedCart.getId() ))).body(savedCart);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
            Cart currentCart = cartRepository.findById(id).orElseThrow(RuntimeException::new);
            Cart updated = currentCart.builder()
                .id(id)
                .customer(cart.getCustomer())
                .item(cart.getItem())
                .quantity(cart.getQuantity())
                .createdAt(LocalDateTime.now())
                .build();
                currentCart = cartRepository.save(updated);
            System.out.println("Updated:" + currentCart);
            return ResponseEntity.ok(currentCart);
        }

        @DeleteMapping("/customer/{id}")
        public ResponseEntity<Cart> deleteCartByCustomerId(@PathVariable Long id) {
            cartRepository.deleteByCustomerId(id);
            return ResponseEntity.ok().build();
        }    
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Cart> deleteCart(@PathVariable Long id) {
            cartRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
