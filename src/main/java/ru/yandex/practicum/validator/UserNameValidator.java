package ru.yandex.practicum.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.User;

import java.util.Objects;

@Slf4j
@Service
public class UserNameValidator {

    public void isValid(User user) {
        log.debug("Validation entered user : {}", user);
        if (Objects.isNull(user.getLogin())) {
            return;
        }
        if (Objects.isNull(user.getName()) || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}

