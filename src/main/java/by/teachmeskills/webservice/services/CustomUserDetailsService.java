package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.entities.CustomUserDetails;
import by.teachmeskills.webservice.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByLogin(username);
        return user.map(CustomUserDetails::fromUserEntityToCustomUserDetails).orElse(null);
    }
}
