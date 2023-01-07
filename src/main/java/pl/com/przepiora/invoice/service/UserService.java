package pl.com.przepiora.invoice.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.com.przepiora.invoice.model.Role;
import pl.com.przepiora.invoice.model.User;
import pl.com.przepiora.invoice.model.dto.NewUserDTO;
import pl.com.przepiora.invoice.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addNewUser(NewUserDTO newUserDTO) {
        User user = User.builder().email(newUserDTO.getEmail())
                .password(newUserDTO.getPassword1())
                .roles(new HashSet<>(Collections.singleton(Role.USER)))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    public Optional<User> getUser(String email){
        return userRepository.findByEmailIgnoreCase(email);
    }

    public Errors validate(NewUserDTO newUserDTO) {
        Errors errors = new BeanPropertyBindingResult(newUserDTO,"newUserDTO");
        if (!(newUserDTO.getPassword1().equals(newUserDTO.getPassword2()))) {
            errors.reject("400", "Both passwords must be the same.");
        }
        if(getUser(newUserDTO.getEmail()).isPresent()){
            errors.reject("400", "This email is already in use.");
        }
        return errors;
    }
}
