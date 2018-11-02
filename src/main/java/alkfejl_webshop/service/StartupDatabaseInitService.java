package alkfejl_webshop.service;

import alkfejl_webshop.entity.AccessRight;
import alkfejl_webshop.entity.User;
import alkfejl_webshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartupDatabaseInitService implements ApplicationListener<ContextRefreshedEvent>{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        User user1 = new User();
        user1.setFirstName("Jenő");
        user1.setLastName("Polgár");
        user1.setEmail("polgar@hivatal.hu");
        user1.setAccessRight(AccessRight.ROLE_CUSTOMER);
        user1.setPassword(passwordEncoder.encode("polgar"));
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Sándor");
        user2.setLastName("Szalacsi");
        user2.setEmail("szalacsi@atombunker.hu");
        user2.setAccessRight(AccessRight.ROLE_CUSTOMER);
        user2.setPassword(passwordEncoder.encode("szalacsi"));
        userRepository.save(user2);

        User user3 = new User();
        user3.setFirstName("Tamás");
        user3.setLastName("Tihanyi");
        user3.setEmail("gerincmutet@budapest.tv");
        user3.setAccessRight(AccessRight.ROLE_CUSTOMER);
        user3.setPassword(passwordEncoder.encode("tihanyi"));
        userRepository.save(user3);
    }
}
