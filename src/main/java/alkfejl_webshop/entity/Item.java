package alkfejl_webshop.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table(name = "order_items")
@EqualsAndHashCode(callSuper = true, of = {"ware", "amount"})
@ToString(callSuper = true, of = {"ware", "amount"})
public class Item extends BaseEntity{

    @ManyToOne(optional = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Ware ware;

    @Positive
    @Column(nullable = false)
    private long amount;
}
