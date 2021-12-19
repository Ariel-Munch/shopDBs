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

import hu.ebanjo.ledshop.dbs.model.Promotion;
import hu.ebanjo.ledshop.dbs.repo.PromotionRepository;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    
        private final PromotionRepository promotionRepository;
    
        public PromotionController(PromotionRepository promotionRepository) {
            this.promotionRepository = promotionRepository;
        }

        @GetMapping
        public List<Promotion> getPromotions() {
            return promotionRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Promotion getPromotion(@PathVariable Long id) {
            return promotionRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) throws URISyntaxException {
            Promotion savedPromotion = promotionRepository.save(promotion);
            return ResponseEntity.created(new URI("/promotions/" + Long.toString( savedPromotion.getId() ))).body(savedPromotion);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Promotion> updatePromotion(@PathVariable Long id, @RequestBody Promotion promotion) {
            Promotion currentPromotion = promotionRepository.findById(id).orElseThrow(RuntimeException::new);
            currentPromotion.builder()
                .name(promotion.getName())
                .build();
            // currentPromotion.setName( promotion.getName() );
            // currentPromotion.setUrlDbApi( promotion.getUrlDbApi()  );
            currentPromotion = promotionRepository.save(promotion);
    
            return ResponseEntity.ok(currentPromotion);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Promotion> deletePromotion(@PathVariable Long id) {
            promotionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
