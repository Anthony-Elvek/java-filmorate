package ru.yandex.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.annotations.UserNameConstraint;
import ru.yandex.practicum.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Slf4j
public class UserNameValidator implements ConstraintValidator<UserNameConstraint, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        log.debug("Validation entered user : {}", user);
        if (Objects.isNull(user.getLogin())) {
            return false;
        }
        if (Objects.isNull(user.getName()) || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return true;
    }
}

