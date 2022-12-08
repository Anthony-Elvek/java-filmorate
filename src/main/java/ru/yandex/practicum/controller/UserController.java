package ru.yandex.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.validator.UserNameValidator;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private final UserNameValidator userNameValidator;
    private AtomicInteger id = new AtomicInteger();
    @Autowired
    public UserController(UserNameValidator userNameValidator) {
        this.userNameValidator = userNameValidator;
    }

    public Integer getId(){
        return id.incrementAndGet();
    }

    @GetMapping
    public Collection<User> allUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userNameValidator.isValid(user);
        user.setId(getId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        userNameValidator.isValid(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Unknown user");
        }
        return user;
    }

}
