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

import hu.ebanjo.ledshop.dbs.model.Property;
import hu.ebanjo.ledshop.dbs.repo.PropertyRepository;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    
        private final PropertyRepository propertyRepository;
    
        public PropertyController(PropertyRepository propertyRepository) {
            this.propertyRepository = propertyRepository;
        }

        @GetMapping
        public List<Property> getProperties() {
            return propertyRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Property getProperty(@PathVariable Long id) {
            return propertyRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Property> createProperty(@RequestBody Property property) throws URISyntaxException {
            Property savedProperty = propertyRepository.save(property);
            return ResponseEntity.created(new URI("/properties/" + Long.toString( savedProperty.getId() ))).body(savedProperty);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property property) {
            Property currentProperty = propertyRepository.findById(id).orElseThrow(RuntimeException::new);
            currentProperty.builder()
                .name(property.getName())
                .build();
            // currentProperty.setName( property.getName() );
            // currentProperty.setUrlDbApi( property.getUrlDbApi()  );
            currentProperty = propertyRepository.save(property);
    
            return ResponseEntity.ok(currentProperty);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Property> deleteProperty(@PathVariable Long id) {
            propertyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
