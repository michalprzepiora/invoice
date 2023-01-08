package pl.com.przepiora.invoice.validator.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.com.przepiora.invoice.validator.IsNotInUseValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsNotInUseValidator.class)
@Target( {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsNotInUse {
    String message() default "This email is already in use..";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
