package alkfejl_webshop.controller;

import alkfejl_webshop.entity.*;
import alkfejl_webshop.repository.OrderRepository;
import alkfejl_webshop.repository.UserRepository;
import alkfejl_webshop.repository.WareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final WareRepository wareRepository;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findAll());
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-id/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id){
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(order.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-customer/{id}")
    public ResponseEntity<List<Order>> getAllOrdersByCustomer(@PathVariable UUID id){
        Optional<User> customer = userRepository.findById(id);
        if(customer.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByCustomer(customer.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<Order>> getAllOrdersByStatus(@PathVariable OrderStatus status){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByStatus(status));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-order-date-greater-than/{instant}")
    public ResponseEntity<List<Order>> getAllOrdersByOrderDateGreaterThan(@PathVariable Instant instant){
        //Instant instant = Instant.parse(dateString);
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByOrderDateGreaterThan(instant));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-order-date-less-than/{instant}")
    public ResponseEntity<List<Order>> getAllOrdersByORderDateLessThan(@PathVariable Instant instant){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByOrderDateLessThan(instant));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-order-date-between/{earlierInstant}&{laterInstant}")
    public ResponseEntity<List<Order>> getAllOrdersByOrderDateBetween(@PathVariable Instant earlierInstant, @PathVariable Instant laterInstant){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findByOrderDateBetween(earlierInstant, laterInstant));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @PostMapping("/by-customer/{id}/new-order")
    public ResponseEntity<Order> addOrder(@PathVariable UUID id){
        Optional<User> customer = userRepository.findById(id);
        if(customer.isPresent()){
            Order order = new Order();
            order.setCustomer(customer.get());
            order.setStatus(OrderStatus.PENDING);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(order));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @PutMapping("/by-id/{id}/new-items")
    public ResponseEntity<Order> addItemsToOrder(@PathVariable UUID id, @RequestBody Map<String, ArrayList<Map<String, String>>> items){
        Optional<Order> storedOrder = orderRepository.findById(id);
        if(storedOrder.isPresent()){
            if(storedOrder.get().getStatus().equals(OrderStatus.PENDING)){
                if(items.get("items") != null){
                    for(Map<String, String> item : items.get("items")){
                        if(item.get("ware") != null && item.get("amount") != null){
                            Optional<Ware> ware = wareRepository.findById(UUID.fromString(item.get("ware")));
                            if(ware.isPresent() && ware.get().getStock() >= Long.parseLong(item.get("amount"))){
                                ware.get().setStock(ware.get().getStock() - Long.parseLong(item.get("amount")));
                                wareRepository.save(ware.get());
                                Item newItem = new Item();
                                newItem.setWare(ware.get());
                                newItem.setAmount(Long.parseLong(item.get("amount")));
                                storedOrder.get().getItems().add(newItem);
                            }
                            else{
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                            }
                        }
                        else{
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                        }
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(storedOrder.get()));
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @PatchMapping("/by-id/{id}/new-item/{wareId}&{amount}")
    public ResponseEntity<Order> addItemToOrder(@PathVariable UUID id, @PathVariable UUID wareId, @PathVariable long amount){
        Optional<Order> storedOrder = orderRepository.findById(id);
        if(storedOrder.isPresent()){
            if(storedOrder.get().getStatus().equals(OrderStatus.PENDING)){
                Optional<Ware> ware = wareRepository.findById(wareId);
                if(ware.isPresent() && ware.get().getStock() >= amount){
                    ware.get().setStock(ware.get().getStock() - amount);
                    wareRepository.save(ware.get());
                    Item item = new Item();
                    item.setWare(ware.get());
                    item.setAmount(amount);
                    storedOrder.get().getItems().add(item);
                    return ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(storedOrder.get()));
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/by-id/{id}/change-status/{status}")
    public ResponseEntity<Order> changeOrderStatus(@PathVariable UUID id, @PathVariable OrderStatus status){
        Optional<Order> storedOrder = orderRepository.findById(id);
        if(storedOrder.isPresent()){
            if(!storedOrder.get().getItems().isEmpty()){
                storedOrder.get().setStatus(status);
                return ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(storedOrder.get()));
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @DeleteMapping("by-id/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID id){
        Optional<Order> storedOrder = orderRepository.findById(id);
        if(storedOrder.isPresent()){
            if(storedOrder.get().getStatus().equals(OrderStatus.PENDING)){
                for(Iterator<Item> it = storedOrder.get().getItems().iterator(); it.hasNext(); ){
                    Item item = it.next();
                    Ware ware = wareRepository.findById(item.getWare().getId()).get();
                    ware.setStock(ware.getStock() + item.getAmount());
                }
                orderRepository.delete(storedOrder.get());
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            else if(storedOrder.get().getStatus().equals(OrderStatus.FINISHED)){
                orderRepository.delete(storedOrder.get());
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @DeleteMapping("by-id/{id}/delete-items")
    public ResponseEntity<Order> deleteItemsFromOrder(@PathVariable UUID id){
        Optional<Order> storedOrder = orderRepository.findById(id);
        if(storedOrder.isPresent()){
            if(storedOrder.get().getStatus().equals(OrderStatus.PENDING)){
                for(Iterator<Item> it = storedOrder.get().getItems().iterator(); it.hasNext(); ){
                    Item item = it.next();
                    Ware ware = wareRepository.findById(item.getWare().getId()).get();
                    ware.setStock(ware.getStock() + item.getAmount());
                }
                storedOrder.get().getItems().clear();
                return ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(storedOrder.get()));
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @DeleteMapping("by-id/{id}/delete-item/{itemId}")
    public ResponseEntity<Order> deleteItemFromOrder(@PathVariable UUID id, @PathVariable UUID itemId){
        Optional<Order> storedOrder = orderRepository.findById(id);
        if(storedOrder.isPresent()){
            if(storedOrder.get().getStatus().equals(OrderStatus.PENDING)){
                for(Iterator<Item> it = storedOrder.get().getItems().iterator(); it.hasNext(); ){
                    Item item = it.next();
                    if(item.getId().compareTo(itemId) == 0){
                        Ware ware = wareRepository.findById(item.getWare().getId()).get();
                        ware.setStock(ware.getStock() + item.getAmount());
                        it.remove();
                    }
                }
                return ResponseEntity.status(HttpStatus.OK).body(orderRepository.save(storedOrder.get()));
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
