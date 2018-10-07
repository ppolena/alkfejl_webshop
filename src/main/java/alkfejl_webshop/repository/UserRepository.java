package alkfejl_webshop.repository;

import alkfejl_webshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findByEmail(String email);

    List<User> findByFirstNameIgnoreCase(String firstName);

    List<User> findByLastNameIgnoreCase(String lastName);

    List<User> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

    List<User> findByPhoneNumberNotNull();

    List<User> findByAddressNotNull();
}
