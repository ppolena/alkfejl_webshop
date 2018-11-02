package alkfejl_webshop.security;

import alkfejl_webshop.entity.AccessRight;
import alkfejl_webshop.entity.User;
import alkfejl_webshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;
    private final WebSecurityConfig webSecurityConfig;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        switch(email){
            case "admin":{
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(AccessRight.ROLE_ADMIN.toString()));
                return new org.springframework.security.core.userdetails.User(email, webSecurityConfig.getAdminPassword(), grantedAuthorities);
            }
            case "guest":{
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(AccessRight.ROLE_GUEST.toString()));
                return new org.springframework.security.core.userdetails.User(email, webSecurityConfig.getGuestPassword(), grantedAuthorities);
            }
            default:{
                Optional<User> user = userRepository.findByEmail(email);
                if(!user.isPresent()){
                    throw new UsernameNotFoundException(email);
                }
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(user.get().getAccessRight().toString()));
                return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), grantedAuthorities);
            }
        }
    }
}
