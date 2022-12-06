package ru.yandex.practicum.annotations;

import ru.yandex.practicum.validator.ReleaseDateValidator;

import java.lang.annotation.*;
import javax.validation.Payload;
import javax.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidator.class)
@Documented
public @interface ReleaseDateConstraint {
    String message() default "Invalid film release date has been entered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
