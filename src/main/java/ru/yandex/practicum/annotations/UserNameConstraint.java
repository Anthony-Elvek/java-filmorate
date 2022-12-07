package ru.yandex.practicum.annotations;

import ru.yandex.practicum.validator.UserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameValidator.class)
@Documented
public @interface UserNameConstraint {
    String message() default "Invalid username has been entered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
