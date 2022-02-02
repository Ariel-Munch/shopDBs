package hu.ebanjo.ledshop.dbs.control;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.ebanjo.ledshop.dbs.model.Product;
import hu.ebanjo.ledshop.dbs.model.SearchDTO;
import hu.ebanjo.ledshop.dbs.repo.ProductRepository;

/* @CrossOrigin(origins= "http://localhost:4200"
                , methods = {RequestMethod.GET
                    , RequestMethod.PUT
                    , RequestMethod.POST
                    , RequestMethod.DELETE
                    , RequestMethod.OPTIONS}  ) */
@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/prodfilter")
public class SearchController {
    
        private final ProductRepository productRepository;
    
        public SearchController(ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        @GetMapping
        public List<Product> getProducts( @RequestBody SearchDTO fltr ) {
            return productRepository.findByNameContainingIgnoreCase( fltr.getName() );
            
        }
    /*
        @GetMapping("/{id}")
        public Product getProduct(@PathVariable Long id) {
            return productRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Product> createProduct(@RequestBody Product product) throws URISyntaxException {
            Product savedProduct = productRepository.save(product);
            String prod_ID = Long.toString( savedProduct.id );
            
            return ResponseEntity.created(new URI("/products/" + prod_ID )).body(savedProduct);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
            Product currentProduct = productRepository.findById(id).orElseThrow(RuntimeException::new);
            // currentProduct.builder()                .name(product.name)                .build();
            currentProduct.name = product.name;
            // currentProduct.setUrlDbApi( product.getUrlDbApi()  );
            currentProduct = productRepository.save(product);
    
            return ResponseEntity.ok(currentProduct);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    

        */
}
