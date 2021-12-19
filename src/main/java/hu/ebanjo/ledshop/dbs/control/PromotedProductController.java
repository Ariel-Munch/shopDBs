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

import hu.ebanjo.ledshop.dbs.model.PromotedProduct;
import hu.ebanjo.ledshop.dbs.repo.PromotedProductRepository;

@RestController
@RequestMapping("/promotedproducts")
public class PromotedProductController {
    
        private final PromotedProductRepository promotedproductRepository;
    
        public PromotedProductController(PromotedProductRepository promotedproductRepository) {
            this.promotedproductRepository = promotedproductRepository;
        }

        @GetMapping
        public List<PromotedProduct> getPromotedProducts() {
            return promotedproductRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public PromotedProduct getPromotedProduct(@PathVariable Long id) {
            return promotedproductRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<PromotedProduct> createPromotedProduct(@RequestBody PromotedProduct promotedproduct) throws URISyntaxException {
            PromotedProduct savedPromotedProduct = promotedproductRepository.save(promotedproduct);
            return ResponseEntity.created(new URI("/promotedproducts/" + Long.toString( savedPromotedProduct.getId() ))).body(savedPromotedProduct);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<PromotedProduct> updatePromotedProduct(@PathVariable Long id, @RequestBody PromotedProduct promotedproduct) {
            PromotedProduct currentPromotedProduct = promotedproductRepository.findById(id).orElseThrow(RuntimeException::new);
            currentPromotedProduct.builder()
                .name(promotedproduct.getName())
                .build();
            // currentPromotedProduct.setName( promotedproduct.getName() );
            // currentPromotedProduct.setUrlDbApi( promotedproduct.getUrlDbApi()  );
            currentPromotedProduct = promotedproductRepository.save(promotedproduct);
    
            return ResponseEntity.ok(currentPromotedProduct);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<PromotedProduct> deletePromotedProduct(@PathVariable Long id) {
            promotedproductRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
