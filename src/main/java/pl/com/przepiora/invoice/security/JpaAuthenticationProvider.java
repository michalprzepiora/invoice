package pl.com.przepiora.invoice.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.przepiora.invoice.model.User;
import pl.com.przepiora.invoice.repository.UserRepository;

import java.util.Optional;

@Component
public class JpaAuthenticationProvider implements AuthenticationProvider {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public JpaAuthenticationProvider(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userNameFromPage = authentication.getName();
        Object credentialFromPage = authentication.getCredentials();
        if (credentialFromPage instanceof String password) {
            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(userNameFromPage);
                        if (optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword())){
                User user = optionalUser.get();
                return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            }
        }
        throw new BadCredentialsException("Wrong username and/or password.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
