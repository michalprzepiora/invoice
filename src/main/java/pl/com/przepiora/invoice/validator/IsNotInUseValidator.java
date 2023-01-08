package pl.com.przepiora.invoice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import pl.com.przepiora.invoice.repository.UserRepository;
import pl.com.przepiora.invoice.validator.interfaces.IsNotInUse;

@Component
public class IsNotInUseValidator implements ConstraintValidator<IsNotInUse,String> {
    private UserRepository userRepository;

    public IsNotInUseValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(IsNotInUse constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userRepository.findByEmailIgnoreCase(value).isEmpty();
    }
}
