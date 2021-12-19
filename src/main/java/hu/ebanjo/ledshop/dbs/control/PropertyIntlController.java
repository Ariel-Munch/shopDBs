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

import hu.ebanjo.ledshop.dbs.model.PropertyIntl;
import hu.ebanjo.ledshop.dbs.repo.PropertyIntlRepository;

@RestController
@RequestMapping("/propertyintls")
public class PropertyIntlController {
    
        private final PropertyIntlRepository propertyintlRepository;
    
        public PropertyIntlController(PropertyIntlRepository propertyintlRepository) {
            this.propertyintlRepository = propertyintlRepository;
        }

        @GetMapping
        public List<PropertyIntl> getPropertyIntls() {
            return propertyintlRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public PropertyIntl getPropertyIntl(@PathVariable Long id) {
            return propertyintlRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<PropertyIntl> createPropertyIntl(@RequestBody PropertyIntl propertyintl) throws URISyntaxException {
            PropertyIntl savedPropertyIntl = propertyintlRepository.save(propertyintl);
            return ResponseEntity.created(new URI("/propertyintls/" + Long.toString( savedPropertyIntl.getId() ))).body(savedPropertyIntl);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<PropertyIntl> updatePropertyIntl(@PathVariable Long id, @RequestBody PropertyIntl propertyintl) {
            PropertyIntl currentPropertyIntl = propertyintlRepository.findById(id).orElseThrow(RuntimeException::new);
            currentPropertyIntl.builder()
                .name(propertyintl.getName())
                .build();
            // currentPropertyIntl.setName( propertyintl.getName() );
            // currentPropertyIntl.setUrlDbApi( propertyintl.getUrlDbApi()  );
            currentPropertyIntl = propertyintlRepository.save(propertyintl);
    
            return ResponseEntity.ok(currentPropertyIntl);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<PropertyIntl> deletePropertyIntl(@PathVariable Long id) {
            propertyintlRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
