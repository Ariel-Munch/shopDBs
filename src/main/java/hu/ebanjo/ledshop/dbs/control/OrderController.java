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

import hu.ebanjo.ledshop.dbs.model.Order;
import hu.ebanjo.ledshop.dbs.repo.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
        private final OrderRepository orderRepository;
    
        public OrderController(OrderRepository orderRepository) {
            this.orderRepository = orderRepository;
        }

        @GetMapping
        public List<Order> getOrders() {
            return orderRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Order getOrder(@PathVariable Long id) {
            return orderRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Order> createOrder(@RequestBody Order order) throws URISyntaxException {
            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.created(new URI("/orders/" + Long.toString( savedOrder.getId() ))).body(savedOrder);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
            Order currentOrder = orderRepository.findById(id).orElseThrow(RuntimeException::new);
            currentOrder.builder()
                .name(order.getName())
                .build();
            // currentOrder.setName( order.getName() );
            // currentOrder.setUrlDbApi( order.getUrlDbApi()  );
            currentOrder = orderRepository.save(order);
    
            return ResponseEntity.ok(currentOrder);
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }    
}
