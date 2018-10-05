package alkfejl_webshop.controller;

import alkfejl_webshop.entity.Ware;
import alkfejl_webshop.repository.WareRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wares")
public class WareController {

    private final WareRepository wareRepository;

    @GetMapping("")
    public ResponseEntity<List<Ware>> getAllWares() {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findAll());
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<Ware> getWareById(@PathVariable Integer id) {
        Optional<Ware> ware = wareRepository.findById(id);
        if (ware.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ware.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<Ware> getWareById(@PathVariable String name) {
        Optional<Ware> ware = wareRepository.findByName(name);
        if (ware.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ware.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<Ware>> getWaresByType(@PathVariable String type) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByType(type));
    }

    @GetMapping("/by-manufacturer/{manufacturer}")
    public ResponseEntity<List<Ware>> getWaresByManufacturer(@PathVariable String manufacturer) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByManufacturer(manufacturer));
    }

    @GetMapping("/by-price/{price}")
    public ResponseEntity<List<Ware>> getWaresByPrice(@PathVariable double price) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByPrice(price));
    }

    @GetMapping("/by-price-greater-than/{price}")
    public ResponseEntity<List<Ware>> getWaresByPriceGreaterThan(@PathVariable double price) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByPriceGreaterThan(price));
    }

    @GetMapping("/by-price-less-than/{price}")
    public ResponseEntity<List<Ware>> getWaresByPriceLessThan(@PathVariable double price) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByPriceLessThan(price));
    }

    @GetMapping("/by-price-between/{lowerLimit}&{upperLimit}")
    public ResponseEntity<List<Ware>> getWaresByPriceBetween(@PathVariable double lowerLimit, @PathVariable double upperLimit) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByPriceBetween(lowerLimit, upperLimit));
    }

    @GetMapping("/by-stock/{number}")
    public ResponseEntity<List<Ware>> getWaresByStock(@PathVariable long number) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByStock(number));
    }

    @GetMapping("/by-stock-greater-than/{number}")
    public ResponseEntity<List<Ware>> getWaresByStockGreaterThan(@PathVariable long number) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByStockGreaterThan(number));
    }

    @GetMapping("/by-stock-less-than/{number}")
    public ResponseEntity<List<Ware>> getWaresByStockLessThan(@PathVariable long number) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByStockLessThan(number));
    }

    @GetMapping("/by-stock-between/{lowerLimit}&{upperLimit}")
    public ResponseEntity<List<Ware>> getWaresByStockBetween(@PathVariable long lowerLimit, @PathVariable long upperLimit) {
        return ResponseEntity.status(HttpStatus.OK).body(wareRepository.findByStockBetween(lowerLimit, upperLimit));
    }

    @PostMapping
    public ResponseEntity<String> addWare(@Valid @RequestBody Ware ware) {
        if (wareRepository.findByName(ware.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        wareRepository.save(ware);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/by-id/{id}")
    public ResponseEntity<String> changeWare(@PathVariable Integer id, @Valid @RequestBody Ware updatedWare) {
        Optional<Ware> storedWare = wareRepository.findById(id);
        if (storedWare.isPresent()) {
            updatedWare.setId(storedWare.get().getId());
            wareRepository.save(updatedWare);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/by-id/{id}")
    public ResponseEntity<String> updateWare(@PathVariable Integer id, @RequestBody Map<String, String> updatedWare) {
        Optional<Ware> storedWare = wareRepository.findById(id);
        if (storedWare.isPresent()) {
            if (updatedWare.get("name") != null) {
                storedWare.get().setName(updatedWare.get("name").toString());
            }
            if (updatedWare.get("type") != null) {
                storedWare.get().setType(updatedWare.get("type"));
            }
            if (updatedWare.get("manufacturer") != null) {
                storedWare.get().setManufacturer(updatedWare.get("manufacturer"));
            }
            if (NumberUtils.isParsable(updatedWare.get("price")) && Double.parseDouble(updatedWare.get("price")) > 0.0) {
                storedWare.get().setPrice(Double.parseDouble(updatedWare.get("price")));
            }
            if (NumberUtils.isParsable(updatedWare.get("stock")) && Double.parseDouble(updatedWare.get("stock")) > 0.0) {
                storedWare.get().setStock(Math.round(Double.parseDouble(updatedWare.get("stock"))));
            }
            if (updatedWare.get("description") != null) {
                storedWare.get().setDescription(updatedWare.get("description"));
            }
            wareRepository.save(storedWare.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
