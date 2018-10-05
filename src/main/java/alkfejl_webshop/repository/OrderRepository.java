package alkfejl_webshop.repository;

import alkfejl_webshop.entity.Order;
import alkfejl_webshop.entity.User;
import alkfejl_webshop.entity.Ware;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);

    List<Order> findByWare(Ware ware);

    Optional<Order> findByUserAndWare(User user, Ware ware);

    List<Order> findByAmount(long amuount);

    List<Order> findByAmountGreaterThan(long amount);

    List<Order> findByAmountLessThan(long amount);

    List<Order> findByAmountBetween(long lowerLimit, long upperLimit);

    List<Order> findByOrderDateGreaterThan(Date date);

    List<Order> findByORderDateLessThan(Date date);

    List<Order> findByOrderDateBetween(Date earlierDate, Date laterDate);
}
