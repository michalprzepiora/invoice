package pl.com.przepiora.invoice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.com.przepiora.invoice.model.Role;
import pl.com.przepiora.invoice.model.User;
import pl.com.przepiora.invoice.model.dto.NewUserDTO;
import pl.com.przepiora.invoice.repository.UserRepository;
import pl.com.przepiora.invoice.mail.TokenGenerator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService {
    private static final String EMPTY_STRING = "";

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenGenerator tokenGenerator;
    private MailService mailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
        this.mailService = mailService;
    }

    public User addNewUser(NewUserDTO newUserDTO) {
        User user = User.builder().email(newUserDTO.getEmail())
                .password(passwordEncoder.encode(newUserDTO.getPassword1()))
                .roles(new HashSet<>(Collections.singleton(Role.USER)))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .enabled(false)
                .token(tokenGenerator.generate())
                .build();
        mailService.sendActivationLink(user.getEmail(), user.getToken());

        return userRepository.save(user);
    }

    public boolean confirmEmail(String token){
        Optional<User> optionalUser = userRepository.findByToken(token);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setToken(EMPTY_STRING);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Errors validateRetypingPassword(NewUserDTO newUserDTO) {
        Errors errors = new BeanPropertyBindingResult(newUserDTO,"newUserDTO");
        if (!(newUserDTO.getPassword1().equals(newUserDTO.getPassword2()))) {
            errors.reject("400", "Both passwords must be the same.");
        }
        return errors;
    }
}
