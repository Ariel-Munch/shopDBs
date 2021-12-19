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

import hu.ebanjo.ledshop.dbs.model.CodeValue;
import hu.ebanjo.ledshop.dbs.repo.CodeValueRepository;

@RestController
@RequestMapping("/codevalues")
public class CodeValueController {
    
        private final CodeValueRepository codevalueRepository;
    
        public CodeValueController(CodeValueRepository codevalueRepository) {
            this.codevalueRepository = codevalueRepository;
        }

        @GetMapping
        public List<CodeValue> getCodeValues() {
            return codevalueRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public CodeValue getCodeValue(@PathVariable Long id) {
            return codevalueRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<CodeValue> createCodeValue(@RequestBody CodeValue codevalue) throws URISyntaxException {
            CodeValue savedCodeValue = codevalueRepository.save(codevalue);
            return ResponseEntity.created(new URI("/codevalues/" + Long.toString( savedCodeValue.getId() ))).body(savedCodeValue);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<CodeValue> updateCodeValue(@PathVariable Long id, @RequestBody CodeValue codevalue) {
            CodeValue currentCodeValue = codevalueRepository.findById(id).orElseThrow(RuntimeException::new);
            currentCodeValue.builder()
                .name(codevalue.getName())
                .build();
            // currentCodeValue.setName( codevalue.getName() );
            // currentCodeValue.setUrlDbApi( codevalue.getUrlDbApi()  );
            currentCodeValue = codevalueRepository.save(codevalue);
    
            return ResponseEntity.ok(currentCodeValue);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<CodeValue> deleteCodeValue(@PathVariable Long id) {
            codevalueRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
