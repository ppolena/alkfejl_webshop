package alkfejl_webshop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, of = {"firstName", "lastName", "email", "phoneNumber", "address", "accessRight"})
public class User extends BaseEntity {

    @Size(min = 2, max = 25)
    @Column(nullable = false, length = 25)
    private String firstName;

    @Size(min = 2, max = 25)
    @Column(nullable = false, length = 25)
    private String lastName;

    @Email
    @Size(max = 254)
    @Column(nullable = false, updatable = false, unique = true, length = 254)
    private String email;

    @Size(min = 8, max = 60)
    @Column(nullable = false, length = 60)
    private String password;

    @Size(max = 15)
    @Column(length = 15)
    private String phoneNumber;

    @Size(max = 50)
    @Column(length = 50)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private AccessRight accessRight;
}
