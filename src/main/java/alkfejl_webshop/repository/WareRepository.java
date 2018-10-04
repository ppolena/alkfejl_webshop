package alkfejl_webshop.repository;

import alkfejl_webshop.entity.Ware;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WareRepository extends JpaRepository<Ware, Integer> {
    Optional<Ware> findByName(String name);

    List<Ware> findByType(String type);

    List<Ware> findByManufacturer(String manufacturer);

    List<Ware> findByPrice(double price);

    List<Ware> findByPriceGreaterThan(double price);

    List<Ware> findByPriceLessThan(double price);

    List<Ware> findByPriceBetween(double lowerLimit, double upperLimit);

    List<Ware> findByStock(long number);

    List<Ware> findByStockGreaterThan(long number);

    List<Ware> findByStockLessThan(long number);

    List<Ware> findByStockBetween(long lowerLimit, long upperLimit);
}
