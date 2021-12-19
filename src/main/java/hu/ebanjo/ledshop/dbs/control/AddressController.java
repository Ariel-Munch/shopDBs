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

import hu.ebanjo.ledshop.dbs.model.Address;
import hu.ebanjo.ledshop.dbs.repo.AddressRepository;

@RestController
@RequestMapping("/address")
public class AddressController {
    
        private final AddressRepository addressRepository;
    
        public AddressController(AddressRepository addressRepository) {
            this.addressRepository = addressRepository;
        }

        @GetMapping
        public List<Address> getAddresss() {
            return addressRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Address getAddress(@PathVariable Long id) {
            return addressRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Address> createAddress(@RequestBody Address address) throws URISyntaxException {
            Address savedAddress = addressRepository.save(address);
            return ResponseEntity.created(new URI("/addresss/" + Long.toString( savedAddress.getId() ))).body(savedAddress);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
            Address currentAddress = addressRepository.findById(id).orElseThrow(RuntimeException::new);
            currentAddress.builder()
                .name(address.getName())
                .build();
            // currentAddress.setName( address.getName() );
            // currentAddress.setUrlDbApi( address.getUrlDbApi()  );
            currentAddress = addressRepository.save(address);
    
            return ResponseEntity.ok(currentAddress);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Address> deleteAddress(@PathVariable Long id) {
            addressRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
