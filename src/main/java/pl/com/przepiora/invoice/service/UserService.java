package pl.com.przepiora.invoice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.com.przepiora.invoice.model.Role;
import pl.com.przepiora.invoice.model.User;
import pl.com.przepiora.invoice.model.dto.NewUserDTO;
import pl.com.przepiora.invoice.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addNewUser(NewUserDTO newUserDTO) {
        User user = User.builder().email(newUserDTO.getEmail())
                .password(passwordEncoder.encode(newUserDTO.getPassword1()))
                .roles(new HashSet<>(Collections.singleton(Role.USER)))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    public Errors validateRetypingPassword(NewUserDTO newUserDTO) {
        Errors errors = new BeanPropertyBindingResult(newUserDTO,"newUserDTO");
        if (!(newUserDTO.getPassword1().equals(newUserDTO.getPassword2()))) {
            errors.reject("400", "Both passwords must be the same.");
        }
        return errors;
    }
}
