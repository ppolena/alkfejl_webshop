package alkfejl_webshop.controller;

import alkfejl_webshop.entity.Order;
import alkfejl_webshop.entity.OrderStatus;
import alkfejl_webshop.entity.Ware;
import alkfejl_webshop.repository.OrderRepository;
import alkfejl_webshop.repository.WareRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wares")
public class WareController{

    private final WareRepository wareRepository;
    private final OrderRepository orderRepository;

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping
    public ResponseEntity<List<Ware>> getAllWares(){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findAll());
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-id/{id}")
    public ResponseEntity<Ware> getWareById(@PathVariable UUID id){
        Optional<Ware> ware = wareRepository.findById(id);
        if(ware.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(ware.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-name/{name}")
    public ResponseEntity<Ware> getWareByName(@PathVariable String name){
        Optional<Ware> ware = wareRepository.findByName(name);
        if(ware.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(ware.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<Ware>> getAllWaresByType(@PathVariable String type){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByType(type));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-manufacturer/{manufacturer}")
    public ResponseEntity<List<Ware>> getAllWaresByManufacturer(@PathVariable String manufacturer){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByManufacturer(manufacturer));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-price/{price}")
    public ResponseEntity<List<Ware>> getAllWaresByPrice(@PathVariable double price){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByPrice(price));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-price-greater-than/{price}")
    public ResponseEntity<List<Ware>> getAllWaresByPriceGreaterThan(@PathVariable double price){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByPriceGreaterThan(price));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-price-less-than/{price}")
    public ResponseEntity<List<Ware>> getAllWaresByPriceLessThan(@PathVariable double price){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByPriceLessThan(price));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-price-between/{lowerLimit}&{upperLimit}")
    public ResponseEntity<List<Ware>> getAllWaresByPriceBetween(@PathVariable double lowerLimit, @PathVariable double upperLimit){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByPriceBetween(lowerLimit, upperLimit));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-stock/{number}")
    public ResponseEntity<List<Ware>> getAllWaresByStock(@PathVariable long number){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByStock(number));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-stock-greater-than/{number}")
    public ResponseEntity<List<Ware>> getAllWaresByStockGreaterThan(@PathVariable long number){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByStockGreaterThan(number));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-stock-less-than/{number}")
    public ResponseEntity<List<Ware>> getAllWaresByStockLessThan(@PathVariable long number){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByStockLessThan(number));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_GUEST"})
    @GetMapping("/by-stock-between/{lowerLimit}&{upperLimit}")
    public ResponseEntity<List<Ware>> getAllWaresByStockBetween(@PathVariable long lowerLimit, @PathVariable long upperLimit){
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByStockBetween(lowerLimit, upperLimit));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<String> addWare(@Valid @RequestBody Ware ware){
        if(wareRepository.findByName(ware.getName()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        wareRepository.save(ware);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/add-wares")
    public ResponseEntity<String> addWares(@RequestBody Map<String, ArrayList<Map<String, String>>> wares){
        if(wares.get("wares") != null){
            ArrayList<Map<String, String>> waresList = wares.get("wares");
            for(Map<String, String> ware : waresList){
                if(ware.get("name") != null && ware.get("type") != null && ware.get("manufacturer") != null && ware.get("price") != null && ware.get("stock") != null){
                    if(!wareRepository.findByName(ware.get("name")).isPresent()){
                        Ware newWare = new Ware();
                        if(ware.get("name").length() <= 25 && ware.get("type").length() <= 25 && ware.get("manufacturer").length() <= 25 &&
                                NumberUtils.isParsable(ware.get("price")) && Double.parseDouble(ware.get("price")) > 0.0 &&
                                NumberUtils.isParsable(ware.get("stock")) && Double.parseDouble(ware.get("stock")) >= 0.0){
                            newWare.setName(ware.get("name"));
                            newWare.setType(ware.get("type"));
                            newWare.setManufacturer(ware.get("manufacturer"));
                            newWare.setPrice(Double.parseDouble(ware.get("price")));
                            newWare.setStock(Math.round(Double.parseDouble(ware.get("stock"))));
                            if(ware.get("description") != null && ware.get("description").length() <= 100){
                                newWare.setDescription(ware.get("description"));
                            }
                            wareRepository.save(newWare);
                        }
                    }
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/by-id/{id}")
    public ResponseEntity<String> changeWare(@PathVariable UUID id, @Valid @RequestBody Ware updatedWare){
        Optional<Ware> storedWare = wareRepository.findById(id);
        if(storedWare.isPresent()){
            updatedWare.setId(storedWare.get().getId());
            wareRepository.save(updatedWare);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/by-id/{id}")
    public ResponseEntity<String> updateWare(@PathVariable UUID id, @Valid @RequestBody Ware updatedWare){
        Optional<Ware> storedWare = wareRepository.findById(id);
        if(storedWare.isPresent()){
            if(updatedWare.getName() != null){
                storedWare.get().setName(updatedWare.getName());
            }
            if(updatedWare.getType() != null){
                storedWare.get().setType(updatedWare.getType());
            }
            if(updatedWare.getManufacturer() != null){
                storedWare.get().setManufacturer(updatedWare.getManufacturer());
            }
            if(updatedWare.getPrice() > 0){
                storedWare.get().setPrice(updatedWare.getPrice());
            }
            if(updatedWare.getStock() > storedWare.get().getStock()){
                storedWare.get().setStock(updatedWare.getStock());
            }
            if(updatedWare.getDescription() != null){
                storedWare.get().setDescription(updatedWare.getDescription());
            }
            wareRepository.save(storedWare.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<String> deleteWare(@PathVariable UUID id){
        Optional<Ware> storedWare = wareRepository.findById(id);
        if(storedWare.isPresent()){
            for(Order order : orderRepository.findByStatus(OrderStatus.PENDING)){
                if(order.getItems().contains(storedWare.get())){
                    order.getItems().remove(storedWare.get());
                }
            }
            wareRepository.delete(storedWare.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
