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

import hu.ebanjo.ledshop.dbs.model.CodeType;
import hu.ebanjo.ledshop.dbs.repo.CodeTypeRepository;

@RestController
@RequestMapping("/codetypes")
public class CodeTypeController {
    
        private final CodeTypeRepository codetypeRepository;
    
        public CodeTypeController(CodeTypeRepository codetypeRepository) {
            this.codetypeRepository = codetypeRepository;
        }

        @GetMapping
        public List<CodeType> getCodeTypes() {
            return codetypeRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public CodeType getCodeType(@PathVariable Long id) {
            return codetypeRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<CodeType> createCodeType(@RequestBody CodeType codetype) throws URISyntaxException {
            CodeType savedCodeType = codetypeRepository.save(codetype);
            return ResponseEntity.created(new URI("/codetypes/" + Long.toString( savedCodeType.getId() ))).body(savedCodeType);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<CodeType> updateCodeType(@PathVariable Long id, @RequestBody CodeType codetype) {
            CodeType currentCodeType = codetypeRepository.findById(id).orElseThrow(RuntimeException::new);
            currentCodeType.builder()
                .name(codetype.getName())
                .build();
            // currentCodeType.setName( codetype.getName() );
            // currentCodeType.setUrlDbApi( codetype.getUrlDbApi()  );
            currentCodeType = codetypeRepository.save(codetype);
    
            return ResponseEntity.ok(currentCodeType);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<CodeType> deleteCodeType(@PathVariable Long id) {
            codetypeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
