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

import hu.ebanjo.ledshop.dbs.model.PurchasedProduct;
import hu.ebanjo.ledshop.dbs.repo.PurchasedProductRepository;

@RestController
@RequestMapping("/purchasedproducts")
public class PurchasedProductController {
    
        private final PurchasedProductRepository purchasedproductRepository;
    
        public PurchasedProductController(PurchasedProductRepository purchasedproductRepository) {
            this.purchasedproductRepository = purchasedproductRepository;
        }

        @GetMapping
        public List<PurchasedProduct> getPurchasedProducts() {
            return purchasedproductRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public PurchasedProduct getPurchasedProduct(@PathVariable Long id) {
            return purchasedproductRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<PurchasedProduct> createPurchasedProduct(@RequestBody PurchasedProduct purchasedproduct) throws URISyntaxException {
            PurchasedProduct savedPurchasedProduct = purchasedproductRepository.save(purchasedproduct);
            return ResponseEntity.created(new URI("/purchasedproducts/" + Long.toString( savedPurchasedProduct.getId() ))).body(savedPurchasedProduct);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<PurchasedProduct> updatePurchasedProduct(@PathVariable Long id, @RequestBody PurchasedProduct purchasedproduct) {
            PurchasedProduct currentPurchasedProduct = purchasedproductRepository.findById(id).orElseThrow(RuntimeException::new);
            currentPurchasedProduct.builder()
                .name(purchasedproduct.getName())
                .build();
            // currentPurchasedProduct.setName( purchasedproduct.getName() );
            // currentPurchasedProduct.setUrlDbApi( purchasedproduct.getUrlDbApi()  );
            currentPurchasedProduct = purchasedproductRepository.save(purchasedproduct);
    
            return ResponseEntity.ok(currentPurchasedProduct);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<PurchasedProduct> deletePurchasedProduct(@PathVariable Long id) {
            purchasedproductRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
