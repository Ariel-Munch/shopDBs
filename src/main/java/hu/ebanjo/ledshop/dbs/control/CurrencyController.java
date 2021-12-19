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

import hu.ebanjo.ledshop.dbs.model.Currency;
import hu.ebanjo.ledshop.dbs.repo.CurrencyRepository;

@RestController
@RequestMapping("/currencys")
public class CurrencyController {
    
        private final CurrencyRepository currencyRepository;
    
        public CurrencyController(CurrencyRepository currencyRepository) {
            this.currencyRepository = currencyRepository;
        }

        @GetMapping
        public List<Currency> getCurrencys() {
            return currencyRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Currency getCurrency(@PathVariable Long id) {
            return currencyRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) throws URISyntaxException {
            Currency savedCurrency = currencyRepository.save(currency);
            return ResponseEntity.created(new URI("/currencys/" + Long.toString( savedCurrency.getId() ))).body(savedCurrency);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Currency> updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
            Currency currentCurrency = currencyRepository.findById(id).orElseThrow(RuntimeException::new);
            currentCurrency.builder()
                .name(currency.getName())
                .build();
            // currentCurrency.setName( currency.getName() );
            // currentCurrency.setUrlDbApi( currency.getUrlDbApi()  );
            currentCurrency = currencyRepository.save(currency);
    
            return ResponseEntity.ok(currentCurrency);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Currency> deleteCurrency(@PathVariable Long id) {
            currencyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
