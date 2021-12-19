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

import hu.ebanjo.ledshop.dbs.model.Lang;
import hu.ebanjo.ledshop.dbs.repo.LangRepository;

@RestController
@RequestMapping("/langs")
public class LangController {
    
        private final LangRepository langRepository;
    
        public LangController(LangRepository langRepository) {
            this.langRepository = langRepository;
        }

        @GetMapping
        public List<Lang> getLangs() {
            return langRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Lang getLang(@PathVariable Long id) {
            return langRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Lang> createLang(@RequestBody Lang lang) throws URISyntaxException {
            Lang savedLang = langRepository.save(lang);
            return ResponseEntity.created(new URI("/langs/" + Long.toString( savedLang.getId() ))).body(savedLang);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Lang> updateLang(@PathVariable Long id, @RequestBody Lang lang) {
            Lang currentLang = langRepository.findById(id).orElseThrow(RuntimeException::new);
            currentLang.builder()
                .name(lang.getName())
                .build();
            // currentLang.setName( lang.getName() );
            // currentLang.setUrlDbApi( lang.getUrlDbApi()  );
            currentLang = langRepository.save(lang);
    
            return ResponseEntity.ok(currentLang);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Lang> deleteLang(@PathVariable Long id) {
            langRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
