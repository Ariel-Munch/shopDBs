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

import hu.ebanjo.ledshop.dbs.model.Documentum;
import hu.ebanjo.ledshop.dbs.repo.DocumentumRepository;

@RestController
@RequestMapping("/documentums")
public class DocumentumController {
    
        private final DocumentumRepository documentumRepository;
    
        public DocumentumController(DocumentumRepository documentumRepository) {
            this.documentumRepository = documentumRepository;
        }

        @GetMapping
        public List<Documentum> getDocumentums() {
            return documentumRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Documentum getDocumentum(@PathVariable Long id) {
            return documentumRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Documentum> createDocumentum(@RequestBody Documentum documentum) throws URISyntaxException {
            Documentum savedDocumentum = documentumRepository.save(documentum);
            return ResponseEntity.created(new URI("/documentums/" + Long.toString( savedDocumentum.getId() ))).body(savedDocumentum);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Documentum> updateDocumentum(@PathVariable Long id, @RequestBody Documentum documentum) {
            Documentum currentDocumentum = documentumRepository.findById(id).orElseThrow(RuntimeException::new);
            currentDocumentum.builder()
                .name(documentum.getName())
                .build();
            // currentDocumentum.setName( documentum.getName() );
            // currentDocumentum.setUrlDbApi( documentum.getUrlDbApi()  );
            currentDocumentum = documentumRepository.save(documentum);
    
            return ResponseEntity.ok(currentDocumentum);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Documentum> deleteDocumentum(@PathVariable Long id) {
            documentumRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
