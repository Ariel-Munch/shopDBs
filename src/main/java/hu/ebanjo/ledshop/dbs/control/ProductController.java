package hu.ebanjo.ledshop.dbs.control;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.ebanjo.ledshop.dbs.model.Product;
import hu.ebanjo.ledshop.dbs.model.TermekDTO;
import hu.ebanjo.ledshop.dbs.repo.ProductRepository;

/* @CrossOrigin(origins= "http://localhost:4200"
                , methods = {RequestMethod.GET
                    , RequestMethod.PUT
                    , RequestMethod.POST
                    , RequestMethod.DELETE
                    , RequestMethod.OPTIONS}  ) */
@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/products")
public class ProductController {
    
        private final ProductRepository productRepository;
    
        public ProductController(ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        @GetMapping
        public List<Product> getProducts( ) {
            return productRepository.findAll();
        }

        @GetMapping("/termek")
        public List<TermekDTO> getTermekek( ) {
            List<TermekDTO> lT = new ArrayList<TermekDTO>();
            List<Product> prodz = productRepository.findAll();
            prodz.forEach(p ->  lT.add(mkTermkDTO(p)) );
            return lT;
        }
        @GetMapping("/termek/{id}")
        public TermekDTO getTermekById( @PathVariable Long id) {
            Optional<Product> prodz = productRepository.findById(id);
            return mkTermkDTO( prodz.isPresent() ? prodz.get()  : null) ;

        }
        private TermekDTO mkTermkDTO(Product p) {
            return p == null ? null : TermekDTO.builder()
                .id(p.getId())
                .descriptionTitle(p.getName())
            .build();
        }
        @GetMapping("/name")
        public List<Product> getProducts( @RequestParam String nameFilter ) {
            return productRepository.findByNameContainingIgnoreCase(nameFilter);
        }
    
        @GetMapping("/{id}")
        public Product getProduct(@PathVariable Long id) {
            return productRepository.findById(id).orElseThrow(RuntimeException::new);
        }
        
        @GetMapping("/cat/{id}")
        public Product[] getProductByCat(@PathVariable Long categoryId, @RequestParam String nameFilter) {
            if ( Strings.isEmpty(nameFilter) ) {
                Optional<Product[]> xxx = productRepository.findByCategoryId(categoryId) ;
                if (xxx.isPresent())
                    return  xxx.get();
            } else {
                Optional<Product[]> xxx = productRepository.findByCategoryIdAndName(categoryId, nameFilter);
                if (xxx.isPresent())
                    return  xxx.get();
            }
            return new Product[]{};
            
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
}
