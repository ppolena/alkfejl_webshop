package alkfejl_webshop.repository;

import alkfejl_webshop.entity.Order;
import alkfejl_webshop.entity.OrderStatus;
import alkfejl_webshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByCustomer(User customer);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByOrderDateGreaterThan(Instant instant);

    List<Order> findByOrderDateLessThan(Instant instant);

    List<Order> findByOrderDateBetween(Instant earlierInstant, Instant laterInstant);
}
