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

import hu.ebanjo.ledshop.dbs.model.CodeGroup;
import hu.ebanjo.ledshop.dbs.repo.CodeGroupRepository;

@RestController
@RequestMapping("/codegroups")
public class CodeGroupController {
    
        private final CodeGroupRepository codegroupRepository;
    
        public CodeGroupController(CodeGroupRepository codegroupRepository) {
            this.codegroupRepository = codegroupRepository;
        }

        @GetMapping
        public List<CodeGroup> getCodeGroups() {
            return codegroupRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public CodeGroup getCodeGroup(@PathVariable Long id) {
            return codegroupRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<CodeGroup> createCodeGroup(@RequestBody CodeGroup codegroup) throws URISyntaxException {
            CodeGroup savedCodeGroup = codegroupRepository.save(codegroup);
            return ResponseEntity.created(new URI("/codegroups/" + Long.toString( savedCodeGroup.getId() ))).body(savedCodeGroup);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<CodeGroup> updateCodeGroup(@PathVariable Long id, @RequestBody CodeGroup codegroup) {
            CodeGroup currentCodeGroup = codegroupRepository.findById(id).orElseThrow(RuntimeException::new);
            currentCodeGroup.builder()
                .name(codegroup.getName())
                .build();
            // currentCodeGroup.setName( codegroup.getName() );
            // currentCodeGroup.setUrlDbApi( codegroup.getUrlDbApi()  );
            currentCodeGroup = codegroupRepository.save(codegroup);
    
            return ResponseEntity.ok(currentCodeGroup);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<CodeGroup> deleteCodeGroup(@PathVariable Long id) {
            codegroupRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
