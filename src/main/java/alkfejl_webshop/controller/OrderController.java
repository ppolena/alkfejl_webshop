package alkfejl_webshop.controller;

import alkfejl_webshop.entity.Order;
import alkfejl_webshop.entity.User;
import alkfejl_webshop.entity.Ware;
import alkfejl_webshop.repository.OrderRepository;
import alkfejl_webshop.repository.UserRepository;
import alkfejl_webshop.repository.WareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final WareRepository wareRepository;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findAll());
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(order.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<Order>> getByUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByUser(user.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/by-ware/{id}")
    public ResponseEntity<List<Order>> getByWare(@PathVariable Integer id) {
        Optional<Ware> ware = wareRepository.findById(id);
        if (ware.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByWare(ware.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/by-user-and-ware/{userId}&{wareId}")
    public ResponseEntity<Optional<Order>> getByUserAndWare(@PathVariable Integer userId, @PathVariable Integer wareId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Ware> ware = wareRepository.findById(wareId);
        if (user.isPresent() && ware.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByUserAndWare(user.get(), ware.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/by-amount/{amount}")
    public ResponseEntity<List<Order>> getByAmount(@PathVariable long amount) {
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByAmount(amount));
    }

    @GetMapping("/by-amount-greater-than/{amount}")
    public ResponseEntity<List<Order>> getByAmountGreaterThan(long amount) {
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByAmountGreaterThan(amount));
    }

    @GetMapping("/by-amount-less-than/{amount}")
    public ResponseEntity<List<Order>> getByAmountLessThan(long amount) {
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByAmountLessThan(amount));
    }

    @GetMapping("/by-amount-between/{lowerLimit}&{upperLimit}")
    public ResponseEntity<List<Order>> getByAmountBetween(long lowerLimit, long upperLimit) {
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByAmountBetween(lowerLimit, upperLimit));
    }

    /*public ResponseEntity<List<Order>> getByOrderDateGreaterThan(@PathVariable String dateString){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByOrderDateGreaterThan(date));
    }

    public ResponseEntity<List<Order>> getByORderDateLessThan(@PathVariable String dateString){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByORderDateLessThan(date));
    }

    public ResponseEntity<List<Order>> getByOrderDateBetween(@PathVariable String earlierDateString, @PathVariable String laterDateString){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByOrderDateBetween(earlierDate, laterDate));
    }*/

    @PostMapping
    public ResponseEntity<String> addOrder(@Valid @RequestBody Order order) {
        if (orderRepository.findByUserAndWare(order.getUser(), order.getWare()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/by-id/{id}")
    public ResponseEntity<String> changeOrder(@PathVariable Integer id, @Valid @RequestBody Order updatedOrder) {
        Optional<Order> storedOrder = orderRepository.findById(id);
        if (storedOrder.isPresent()) {
            updatedOrder.setId(storedOrder.get().getId());
            orderRepository.save(updatedOrder);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
