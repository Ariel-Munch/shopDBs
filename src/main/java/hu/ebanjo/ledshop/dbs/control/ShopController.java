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

import hu.ebanjo.ledshop.dbs.model.Shop;
import hu.ebanjo.ledshop.dbs.repo.ShopRepository;

@RestController
@RequestMapping("/shops")
public class ShopController {
    
        private final ShopRepository shopRepository;
    
        public ShopController(ShopRepository shopRepository) {
            this.shopRepository = shopRepository;
        }

        @GetMapping
        public List<Shop> getShops() {
            return shopRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Shop getShop(@PathVariable Long id) {
            return shopRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Shop> createShop(@RequestBody Shop shop) throws URISyntaxException {
            Shop savedShop = shopRepository.save(shop);
            return ResponseEntity.created(new URI("/shops/" + Long.toString( savedShop.getId() ))).body(savedShop);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Shop> updateShop(@PathVariable Long id, @RequestBody Shop shop) {
            Shop currentShop = shopRepository.findById(id).orElseThrow(RuntimeException::new);
            currentShop.setName(shop.getName()) ;
            // currentShop.setName( shop.getName() );
            // currentShop.setUrlDbApi( shop.getUrlDbApi()  );
            currentShop = shopRepository.save(shop);
    
            return ResponseEntity.ok(currentShop);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Shop> deleteShop(@PathVariable Long id) {
            shopRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
