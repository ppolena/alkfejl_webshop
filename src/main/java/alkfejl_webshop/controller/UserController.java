package alkfejl_webshop.controller;

import alkfejl_webshop.entity.User;
import alkfejl_webshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-first-name/{firstName}")
    public ResponseEntity<List<User>> getAllUsersByFirstName(@PathVariable String firstName){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByFirstNameIgnoreCase(firstName));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-last-name/{lastName}")
    public ResponseEntity<List<User>> getAllUsersByLastName(@PathVariable String lastName){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByLastNameIgnoreCase(lastName));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/by-first-and-last-name/{firstName}&{lastName}")
    public ResponseEntity<List<User>> getAllUsersByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/phone-number-given")
    public ResponseEntity<List<User>> getAllUsersByPhoneNumberNotNull(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByPhoneNumberNotNull());
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/address-given")
    public ResponseEntity<List<User>> getAllUsersByAddressNotNull(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByAddressNotNull());
    }

    @Secured({"ROLE_ADMIN", "ROLE_GUEST"})
    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @PutMapping("/by-id/{id}")
    public ResponseEntity<String> changeUser(@PathVariable UUID id, @Valid @RequestBody User updatedUser){
        Optional<User> storedUser = userRepository.findById(id);
        if(storedUser.isPresent()){
            updatedUser.setId(storedUser.get().getId());
            userRepository.save(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @PatchMapping("/by-id/{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @Valid @RequestBody User updatedUser){
        Optional<User> storedUser = userRepository.findById(id);
        if(storedUser.isPresent()){
            if(updatedUser.getFirstName() != null){
                storedUser.get().setFirstName(updatedUser.getFirstName());
            }
            if(updatedUser.getLastName() != null){
                storedUser.get().setLastName(updatedUser.getLastName());
            }
            if(updatedUser.getPassword() != null){
                storedUser.get().setPassword(updatedUser.getPassword());
            }
            if(updatedUser.getPhoneNumber() != null){
                storedUser.get().setPhoneNumber(updatedUser.getPhoneNumber());
            }
            if(updatedUser.getAddress() != null){
                storedUser.get().setAddress(updatedUser.getAddress());
            }
            userRepository.save(storedUser.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id){
        Optional<User> storedUser = userRepository.findById(id);
        if(storedUser.isPresent()){
            userRepository.delete(storedUser.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
