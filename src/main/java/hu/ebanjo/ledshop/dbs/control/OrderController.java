package hu.ebanjo.ledshop.dbs.control;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.ebanjo.ledshop.dbs.model.Address;
import hu.ebanjo.ledshop.dbs.model.CartItem;
import hu.ebanjo.ledshop.dbs.model.Order;
import hu.ebanjo.ledshop.dbs.model.OrderDTO;
import hu.ebanjo.ledshop.dbs.model.Customer;
import hu.ebanjo.ledshop.dbs.model.Shopuser;

import hu.ebanjo.ledshop.dbs.repo.AddressRepository;
import hu.ebanjo.ledshop.dbs.repo.CartItemRepository;
import hu.ebanjo.ledshop.dbs.repo.CustomerRepository;
import hu.ebanjo.ledshop.dbs.repo.OrderRepository;
import hu.ebanjo.ledshop.dbs.repo.ShopRepository;
import hu.ebanjo.ledshop.dbs.repo.UserRepository;

@RestController
@CrossOrigin(origins= "*")
@RequestMapping("/orders")
public class OrderController {
    
    @Value("${app.GUEST_USER_COLLECTOR_ID:1}")
    Long GUEST_USER_COLLECTOR_ID;

    @Autowired OrderRepository orderRepository;
    @Autowired AddressRepository addressRepository;
    @Autowired CartItemRepository cartItemRepository;
    @Autowired CustomerRepository customerRepository;
    @Autowired UserRepository  userRepository ;    
    @Autowired ShopRepository  shopRepository ;    

        @GetMapping
        public List<Order> getOrders() {
            return orderRepository.findAll();
        }
    
        @GetMapping("/{id}")
        public Order getOrder(@PathVariable Long id) {
            return orderRepository.findById(id).orElseThrow(RuntimeException::new);
        }
    
        @PostMapping
        public ResponseEntity<Order> createOrder(@RequestBody OrderDTO order) throws URISyntaxException {
            // shop
            // customer
            Customer customer =
                (    ! order.getCheckoutCreateAcc() && order.getCustomer() != null 
                     && order.getShop_id() == order.getCustomer().getShop().getId()
                ) 
                ?  customerRepository.findById(order.getShop_id()).orElseThrow(RuntimeException::new)
                :  mkCustomer(order)
            ;
            //
            Order newOrder = Order.builder()
                .couponcode(order.getCouponcode())
                .notes(order.getNotes())
                .pay_mode(order.getPay_mode())
                .shippingPrice(order.getShippingPrice())
                .total(order.getTotal())
                .billingAddress( mkAddress(order.getBillingAddress() , order.getCustomer().getId()) )
                .shippingAddress(mkAddress(order.getShippingAddress(), order.getCustomer().getId()) )
                .customer(customer)
            .build();
            //
            order.getItems().forEach( oi -> newOrder.getItems().add( mkCartItem(oi)) );
            //
            Order savedOrder = orderRepository.save(newOrder);
            return ResponseEntity.created(new URI("/orders/" + Long.toString( savedOrder.getId() ))).body(savedOrder);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
            Order currentOrder = orderRepository.findById(id).orElseThrow(RuntimeException::new);
            currentOrder.builder()
                .notes(order.getNotes())
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
    //////////////////////
    private CartItem mkCartItem(CartItem oi) {
        CartItem ci = CartItem.builder()
        .molla_product_id(oi.getMolla_product_id())
        .molla_product_name(oi.getMolla_product_name())
        .picture_height(oi.getPicture_height())
        .picture_width(oi.getPicture_width())
        .picture_url(oi.getPicture_url())
        .price(oi.getPrice())
        .qty(oi.getQty())
        .sum(oi.getSum())
        .createdAt(LocalDateTime.now())
        .build();
        return cartItemRepository.save(ci);
    }

    private Address mkAddress(Address addr, Long customerId) {
        if (addr == null)  return null;
        if (addr.getId() == null)  {
            addr.setCustomerId(customerId);
            return addressRepository.save(addr);
        } else {
            return addressRepository.findById(addr.getId()).orElseThrow(RuntimeException::new);
        }
    }
    
    private Shopuser mkUser(String email, String password, String username, String firstname, String lastname, String token ) {
        return userRepository.save(Shopuser.builder()
                .email(email)
                .password(password)
                .createdAt(LocalDateTime.now())
                .active(false)
                .firstname(firstname)
                .lastname(lastname)
                // .blocked(false)
                .registrationToken(token)
                // .resetPasswordToken(password)
                .username(username)
            .build());
    }

    private Customer mkCustomer(OrderDTO o) {
        return customerRepository.save(
                Customer.builder()
                    .billingAddress( mkAddress(o.getBillingAddress(), o.getCustomer().getId()) )
                    .shippingAddress(mkAddress(o.getShippingAddress(),o.getCustomer().getId()) )
                    .createdAt(LocalDateTime.now())
                    // .firstname(o.getBillingAddress().getFirstName())
                    // .lastname(o.getBillingAddress().getLastName())
                    .phone(o.getBillingAddress().getPhone())
                    .shop(
                        shopRepository.findById(o.getShop_id()).orElseThrow(RuntimeException::new)
                    )
                    .user(
                        o.getCheckoutCreateAcc()
                        ? mkUser(o.getBillingAddress().getEmail(), "!*!", null
                            ,o.getBillingAddress().getFirstName(), o.getBillingAddress().getLastName()
                            , (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-","" ))
                        : userRepository.findById(GUEST_USER_COLLECTOR_ID).orElseThrow(RuntimeException::new)
                    )
                .build()
        );
    }

}
