package alkfejl_webshop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "wares")
@EqualsAndHashCode(callSuper = true, of = {"name", "type", "manufacturer", "price", "stock", "description"})
@ToString(callSuper = true, of = {"name", "type", "manufacturer", "price", "stock", "description"})
public class Ware extends BaseEntity {

    @Size(max = 25)
    @Column(nullable = false, unique = true, length = 25)
    private String name;

    @Size(max = 25)
    @Column(nullable = false, length = 25)
    private String type;

    @Size(max = 25)
    @Column(nullable = false, length = 25)
    private String manufacturer;

    @Positive
    @Column(nullable = false)
    private double price;

    @PositiveOrZero
    @Column(nullable = false)
    private long stock;

    @Column(length = 100)
    private String description;
}
