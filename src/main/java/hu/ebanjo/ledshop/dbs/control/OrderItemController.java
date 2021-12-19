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

import hu.ebanjo.ledshop.dbs.model.OrderItem;
import hu.ebanjo.ledshop.dbs.repo.OrderItemRepository;

@RestController
@RequestMapping("/orderitems")
public class OrderItemController {
    
        private final OrderItemRepository orderitemRepository;
    
        public OrderItemController(OrderItemRepository orderitemRepository) {
            this.orderitemRepository = orderitemRepository;
        }

        @GetMapping
        public List<OrderItem> getOrderItems() {
            return orderitemRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public OrderItem getOrderItem(@PathVariable Long id) {
            return orderitemRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderitem) throws URISyntaxException {
            OrderItem savedOrderItem = orderitemRepository.save(orderitem);
            return ResponseEntity.created(new URI("/orderitems/" + Long.toString( savedOrderItem.getId() ))).body(savedOrderItem);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderitem) {
            OrderItem currentOrderItem = orderitemRepository.findById(id).orElseThrow(RuntimeException::new);
            currentOrderItem.builder()
                .name(orderitem.getName())
                .build();
            // currentOrderItem.setName( orderitem.getName() );
            // currentOrderItem.setUrlDbApi( orderitem.getUrlDbApi()  );
            currentOrderItem = orderitemRepository.save(orderitem);
    
            return ResponseEntity.ok(currentOrderItem);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<OrderItem> deleteOrderItem(@PathVariable Long id) {
            orderitemRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
