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

import hu.ebanjo.ledshop.dbs.model.Product;
import hu.ebanjo.ledshop.dbs.repo.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {
    
        private final ProductRepository productRepository;
    
        public ProductController(ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        @GetMapping
        public List<Product> getProducts() {
            return productRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Product getProduct(@PathVariable Long id) {
            return productRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Product> createProduct(@RequestBody Product product) throws URISyntaxException {
            Product savedProduct = productRepository.save(product);
            return ResponseEntity.created(new URI("/products/" + Long.toString( savedProduct.getId() ))).body(savedProduct);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
            Product currentProduct = productRepository.findById(id).orElseThrow(RuntimeException::new);
            currentProduct.builder()
                .name(product.getName())
                .build();
            // currentProduct.setName( product.getName() );
            // currentProduct.setUrlDbApi( product.getUrlDbApi()  );
            currentProduct = productRepository.save(product);
    
            return ResponseEntity.ok(currentProduct);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
