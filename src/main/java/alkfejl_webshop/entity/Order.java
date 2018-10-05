package alkfejl_webshop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, of = {"user", "ware", "amount", "orderDate"})
public class Order extends BaseEntity {

    @ManyToOne
    @Column(nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @Column(nullable = false, updatable = false)
    private Ware ware;

    @Positive
    @Column(nullable = false)
    private long amount;

    @CreatedDate
    @Column(nullable = false)
    private Date orderDate;
}
