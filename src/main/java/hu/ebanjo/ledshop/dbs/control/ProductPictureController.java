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

import hu.ebanjo.ledshop.dbs.model.ProductPicture;
import hu.ebanjo.ledshop.dbs.repo.ProductPictureRepository;

@RestController
@RequestMapping("/productpictures")
public class ProductPictureController {
    
        private final ProductPictureRepository productpictureRepository;
    
        public ProductPictureController(ProductPictureRepository productpictureRepository) {
            this.productpictureRepository = productpictureRepository;
        }

        @GetMapping
        public List<ProductPicture> getProductPictures() {
            return productpictureRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public ProductPicture getProductPicture(@PathVariable Long id) {
            return productpictureRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<ProductPicture> createProductPicture(@RequestBody ProductPicture productpicture) throws URISyntaxException {
            ProductPicture savedProductPicture = productpictureRepository.save(productpicture);
            return ResponseEntity.created(new URI("/productpictures/" + Long.toString( savedProductPicture.getId() ))).body(savedProductPicture);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<ProductPicture> updateProductPicture(@PathVariable Long id, @RequestBody ProductPicture productpicture) {
            ProductPicture currentProductPicture = productpictureRepository.findById(id).orElseThrow(RuntimeException::new);
            currentProductPicture.builder()
                .name(productpicture.getName())
                .build();
            // currentProductPicture.setName( productpicture.getName() );
            // currentProductPicture.setUrlDbApi( productpicture.getUrlDbApi()  );
            currentProductPicture = productpictureRepository.save(productpicture);
    
            return ResponseEntity.ok(currentProductPicture);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<ProductPicture> deleteProductPicture(@PathVariable Long id) {
            productpictureRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
