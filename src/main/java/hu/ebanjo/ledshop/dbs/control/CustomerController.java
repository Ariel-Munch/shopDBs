package hu.ebanjo.ledshop.dbs.control;

import hu.ebanjo.ledshop.dbs.model.Customer;
import hu.ebanjo.ledshop.dbs.repo.CustomerRepository;
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



@RestController
@RequestMapping("/customers")
public class CustomerController {
    
        private final CustomerRepository customerRepository;
    
        public CustomerController(CustomerRepository customerRepository) {
            this.customerRepository = customerRepository;
        }

        @GetMapping
        public List<Customer> getCustomers() {
            return customerRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Customer getCustomer(@PathVariable Long id) {
            return customerRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws URISyntaxException {
            Customer savedCustomer = customerRepository.save(customer);
            return ResponseEntity.created(new URI("/customers/" + Long.toString( savedCustomer.getId() ))).body(savedCustomer);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
            Customer currentCustomer = customerRepository.findById(id).orElseThrow(RuntimeException::new);

            currentCustomer.setBillingAddress(customer.getBillingAddress());
            currentCustomer.setOtherAddress(customer.getOtherAddress());
            currentCustomer.setPhone(customer.getPhone());
            currentCustomer.setShippingAddress(customer.getShippingAddress());
            currentCustomer.setShop(customer.getShop());
            currentCustomer.setUser(customer.getUser());
            // currentCustomer.setName( customer.getName() );
            // currentCustomer.setUrlDbApi( customer.getUrlDbApi()  );
            currentCustomer = customerRepository.save(customer);
    
            return ResponseEntity.ok(currentCustomer);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
            customerRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
