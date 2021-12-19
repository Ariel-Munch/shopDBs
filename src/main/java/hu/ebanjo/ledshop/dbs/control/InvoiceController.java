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

import hu.ebanjo.ledshop.dbs.model.Invoice;
import hu.ebanjo.ledshop.dbs.repo.InvoiceRepository;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    
        private final InvoiceRepository invoiceRepository;
    
        public InvoiceController(InvoiceRepository invoiceRepository) {
            this.invoiceRepository = invoiceRepository;
        }

        @GetMapping
        public List<Invoice> getInvoices() {
            return invoiceRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Invoice getInvoice(@PathVariable Long id) {
            return invoiceRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) throws URISyntaxException {
            Invoice savedInvoice = invoiceRepository.save(invoice);
            return ResponseEntity.created(new URI("/invoices/" + Long.toString( savedInvoice.getId() ))).body(savedInvoice);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
            Invoice currentInvoice = invoiceRepository.findById(id).orElseThrow(RuntimeException::new);
            currentInvoice.builder()
                .name(invoice.getName())
                .build();
            // currentInvoice.setName( invoice.getName() );
            // currentInvoice.setUrlDbApi( invoice.getUrlDbApi()  );
            currentInvoice = invoiceRepository.save(invoice);
    
            return ResponseEntity.ok(currentInvoice);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Invoice> deleteInvoice(@PathVariable Long id) {
            invoiceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
